package com.leaf.clips.model;
/**
* @author Federico Tavella
* @version 0.03
* @since 0.00
* 
* 
*/

import android.content.Context;
import android.content.Intent;

import com.leaf.clips.model.beacon.Logger;
import com.leaf.clips.model.beacon.LoggerImp;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.model.usersetting.SettingImp;

import java.util.Collection;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
*Classe che permette l'accesso alle informazioni trattate nel package Model
*/ 
public class InformationManagerImp extends AbsBeaconReceiverManager implements InformationManager { 

    /**
    * Logger per la registrazione delle informazioni dei beacon rilevati
    */
    private Logger activeLog;

    /**
    * Oggetto per la gestione delle mappe nel database locale e per il recupero delle mappe nel database remoto
    */
    private final DatabaseService dbService;

    /**
    * PriorityQueue, eventualmente vuota, contenente gli ultimi beacon rilevati
    */
    private PriorityQueue<MyBeacon> lastBeaconsSeen;

    /**
    * Mappa dell'edificio di cui sono stati rilevati I beacon
    */
    private BuildingMap map;

    /**
     * Variabile booleana che indica se debba essere effettuato o meno il log dei beacon rilevati
     */
    boolean shouldLog = false;

    /**
    * Costruttore della classe InformationManagerImp
    * @param dbService Oggetto per la gestione delle mappe nel database locale e per il recupero
     *                 delle mappe nel database remoto
    * @param context Contesto dell'applicazione
    */

    public InformationManagerImp(DatabaseService dbService, Context context){
        super(context);
        this.dbService = dbService;
        lastBeaconsSeen = new PriorityQueue<>();
        activeLog = new LoggerImp();
    }

    /**
    * Metodo che ritorna tutte le categorie di POI presenti all'interno dell'edificio
    * @return  Collection<String>
    */
    @Override
    public Collection<String> getAllCategories(){

        LinkedList<String> list = new LinkedList<String>();
        list.addAll(map.getAllPOIsCategories());
        return list;
    }

    /**
    * Metodo che ritorna la PriorityQueue<MyBeacon>, eventualmente vuota, dei beacon visibili
    * @return  PriorityQueue<MyBeacon>
    */
    @Override
    public PriorityQueue<MyBeacon> getAllVisibleBeacons(){
        return lastBeaconsSeen;
    }

    /**
    * Metodo che ritorna la mappa dell'edificio se questa è già stata caricata dal database locale.
     * Viene lanciata una eccezione di tipo NoBeaconSeenException nel caso in cui non sia stata
     * caricata la mappa poiché non è stato ancora ricevuto alcun beacon
    * @return  BuildingMap
    */
    @Override
    public BuildingMap getBuildingMap() throws NoBeaconSeenException {
        if(map == null)
            throw new NoBeaconSeenException("No beacons seen yet");
        return map;
    }

    /**
    * Metodo che ritorna un oggetto DatabaseService che permette di interrogare il database
    * @return  DatabaseService
    */
    @Override
    public DatabaseService getDatabaseService(){
        return dbService;
    }

    /**
    * Metodo che, dato il nome di un log, ritorna l'informazione in esso contenuta sotto forma di stringa
    * @param name Nome del log da cui reperire l'informazione
    * @return  String
    */
    @Override
    public String getLogInfo(String name){
        return activeLog.open(name);
    }

    /**
     * Metodo che ritorna l'insieme di POI associati al beacon rilevato con il segnale più potente.
     * Viene lanciata una eccezione di tipo NoBeaconSeenException nel caso in cui venga invocato
     * il metodo ma non è stato rilevato ancora alcun beacon
     * @return  Collection<PointOfInterest>
     */
    @Override
    public Collection<PointOfInterest> getNearbyPOIs() throws NoBeaconSeenException{
        if(lastBeaconsSeen.isEmpty())
            throw new NoBeaconSeenException();

        LinkedList<PointOfInterest> list = new LinkedList<PointOfInterest>();
        list.addAll(map.getNearbyPOIs(lastBeaconsSeen.peek()));
        return list;

    }

    /**
    * Metodo che permette di recuperare una mappa dal database in base al major dei beacon rilevati
    * @return  BuildingMap Mappa dell'edificio
    */

    private BuildingMap loadMap(){
        int major = lastBeaconsSeen.peek().getMajor();

        if(dbService.isBuildingMapPresent(major)){
            if(!dbService.isBuildingMapUpdated(major))
                dbService.updateBuildingMap(major);

            return dbService.findBuildingByMajor(major);
        }
        else
            return dbService.findRemoteBuildingByMajor(major);

    }

    /**
     * Metodo che si occupa di settare il campo dati lastBeaconsSeen con la PriorityQueue<MyBeacon>
     *     contenente gli ultimi beacon rilevati. Nel caso in cui non sia stata ancora caricata una
     *     mappa dal database locale si occupa di caricare la mappa dell'edificio che contiene
     *     i beacon rilevati
     */
    @Override
    public void onReceive(Context context, Intent intent){
        PriorityQueue<MyBeacon> p;
        p = ((PriorityQueue<MyBeacon>)intent.getSerializableExtra("queueOfBeacons"));

        for (MyBeacon oneBeacon : p)
            lastBeaconsSeen.add(oneBeacon);

        if(map == null)
            loadMap();

        if(shouldLog){
            activeLog.add(lastBeaconsSeen);
        }
    }

    /**
    * Metodo che permette di rimuovere un log delle informazioni dei beacon visibili
    * @param filename Nome del file da rimuovere
    * @return  void
    */
    @Override
    public void removeBeaconInformationFile(String filename){
        activeLog.remove(filename);
    }

    /**
    * Metodo che permette di salvare il log delle informazioni dei beacon visibili su file
    * @param filename Nome del file in cui salvare le informazioni dei beacon
    * @return  void
    */
    @Override
    public void saveRecordedBeaconInformation(String filename){
        activeLog.save(filename);
    }

    /**
    * Metodo che setta il campo dati lastBeaconsSeen
    * @param beacons Lista dei beacon visibili
    */
    private void setVisibleBeacon(PriorityQueue<MyBeacon> beacons){
        lastBeaconsSeen.clear();
        for(MyBeacon beacon : beacons){
            lastBeaconsSeen.add(beacon);
        }
    }

    /**
     * Metodo che permette di avviare il log delle informazioni dei beacon visibili
     */
    @Override
    public void startRecordingBeacons(){
        if(isDeveloper())
            shouldLog = true;
    }

    private boolean isDeveloper(){
        return new SettingImp(getContext()).isDeveloper();
    }
}

