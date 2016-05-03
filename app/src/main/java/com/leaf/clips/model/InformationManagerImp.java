package com.leaf.clips.model;
/**
* @author
* @version 0.00 
* @since 0.00
* 
* 
*/

import android.content.Context;
import android.content.Intent;

import com.leaf.clips.model.beacon.Logger;
import com.leaf.clips.model.beacon.MyBeacon;

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
    * Costruttore della classe InformationManagerImp
    * @param dbService Oggetto per la gestione delle mappe nel database locale e per il recupero delle mappe nel database remoto
    * @param context Contesto dell'applicazione
    */
    @Override
    public InformationManagerImp(DatabaseService dbService, Context context);

    /**
    * Metodo che ritorna tutte le categorie di POI presenti all'interno dell'edificio
    * @return  Collection<String>
    */
    @Override
    public Collection<String> getAllCategories(){
        return new LinkedList<>(map.getAllPOIsCategories());
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
    * Metodo che ritorna la mappa dell'edificio se questa è già stata caricata dal database locale. Viene lanciata una eccezione di tipo NoBeaconSeenException nel caso in cui non sia stata caricata la mappa poiché non è stato ancora ricevuto alcun beacon
    * @return  BuildingMap
    */
    @Override
    public BuildingMap getBuildingMap(){
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

        map.getNearbyPOIs(lastBeaconsSeen.peek());

    }

    /**
    * Metodo che permette di recuperare una mappa dal database in base al major dei beacon rilevati
    * @return  BuildingMap Mappa dell'edificio
    */
    @Override
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
     *     contenente gli ultimi beacon rilevati. Nel caso in cui non sia stata ancora caricata una mappa dal database locale si occupa di caricare la mappa dell'edificio che contiene I beacon rilevati
     */
    @Override
    public void onReceive(Context context, Intent intent){

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
     * @return  void
     */
    @Override
    public void startRecordingBeacons(){

    }

}
