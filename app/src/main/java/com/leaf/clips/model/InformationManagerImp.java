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
import android.util.Log;

import com.leaf.clips.model.beacon.Logger;
import com.leaf.clips.model.beacon.LoggerImp;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.usersetting.SettingImp;

import java.io.IOException;
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
        //TODO: remove (debug purpose)
        //map = this.dbService.findBuildingByMajor(666);
        Log.i("INFORMATION_MANAGER", "START SERVICE");

        super.startService();
    }

    /**
    * Metodo che ritorna tutte le categorie di POI presenti all'interno dell'edificio
    * @return  Collection<String>
    */
    @Override
    public Collection<String> getAllCategories(){

        LinkedList<String> list = new LinkedList<>();
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

        LinkedList<PointOfInterest> list = new LinkedList<>();
        list.addAll(map.getNearbyPOIs(lastBeaconsSeen.peek()));
        return list;

    }

    /**
    * Metodo che permette di recuperare una mappa dal database in base al major dei beacon rilevati
    */

    private void loadMap(){
        Log.i("INFORMATION_MANAGER","CARICO LA MAPPA");
        int major = lastBeaconsSeen.peek().getMajor();

        if(dbService.isBuildingMapPresent(major)){
            try {
                if(!dbService.isBuildingMapUpdated(major)){
                    boolean shouldUpdate = true;
                    for(Listener listener : listeners)
                        shouldUpdate = shouldUpdate && ((InformationListener)listener).noLastMapVersion();
                    if (shouldUpdate)
                        dbService.updateBuildingMap(major);
                }
                else
                    map = dbService.findBuildingByMajor(major);

            } catch (IOException e) {
                e.printStackTrace();
                for(Listener listener : listeners)
                    ((InformationListener)listener).cannotRetrieveRemoteMapDetails(); //errore connessione
            }
        }
        else{
            boolean mapExists = false;
            try {
                mapExists = dbService.isRemoteMapPresent(major);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(mapExists){
                boolean shouldDownload = true;
                for(Listener listener : listeners)
                    shouldDownload = shouldDownload && ((InformationListener)listener).onLocalMapNotFound();
                try {
                    if(shouldDownload) {
                        map = dbService.findRemoteBuildingByMajor(major);
                        Log.i("INFORMATION_MANAGER","MAP LOADING FROM REMOTE DB");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    for(Listener listener : listeners)
                        ((InformationListener)listener).cannotRetrieveRemoteMapDetails(); //errore connessione
                }
            }
            else
                for(Listener listener : listeners)
                    ((InformationListener)listener).onRemoteMapNotFound();
        }
        //map=dbService.findBuildingByMajor(666);

    }

    /**
     * Metodo che si occupa di settare il campo dati lastBeaconsSeen con la PriorityQueue<MyBeacon>
     * contenente gli ultimi beacon rilevati. Nel caso in cui non sia stata ancora caricata una
     * mappa dal database locale si occupa di caricare la mappa dell'edificio che contiene
     * i beacon rilevati
     */
    @Override
    public void onReceive(Context context, Intent intent){

        Log.i("INFORMATION_MANAGER", "RECEIVE");
        PriorityQueue<MyBeacon> p;
        p = ((PriorityQueue<MyBeacon>)intent.getSerializableExtra("queueOfBeacons"));
        for(MyBeacon beacon : p)
            Log.i("P", beacon.toString());
        if(!p.containsAll(lastBeaconsSeen) || lastBeaconsSeen.containsAll(p))
            setVisibleBeacon(p);
        lastBeaconsSeen = p;
        if(map == null) {

            loadMap();

            if (map != null)
                for(Listener listener : listeners)
                    ((InformationListener)listener).onDatabaseLoaded();
        }

        if(shouldLog) {
            activeLog.add(lastBeaconsSeen);
        }

        for (Listener listener:listeners
                ) {
            ((InformationListener)listener).onDatabaseLoaded();
        }

        Log.d("beaconz", "onReceive");
    }

    /**
    * Metodo che permette di rimuovere un log delle informazioni dei beacon visibili
    * @param filename Nome del file da rimuovere
    */
    @Override
    public void removeBeaconInformationFile(String filename){
        activeLog.remove(filename);
    }

    /**
    * Metodo che permette di salvare il log delle informazioni dei beacon visibili su file
    * @param filename Nome del file in cui salvare le informazioni dei beacon
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

    /**
     * Metodo che permette di registrare un listener
     * @param listener Listener che deve essere aggiunto alla lista di InformationListener
     */
    @Override
    public void addListener(InformationListener listener) {
        super.addListener(listener);
    }

    /**
     * Metodo che permette di rimuovere un listener
     * @param listener Listener che deve essere rimosso dalla lista di InformationListener
     */
    @Override
    public void removeListener(InformationListener listener) {
        super.removeListener(listener);
    }

    // TODO aggiungere asta/travis/test
    /**
     * Metodo che ritorna tutti i PointOfInterest appartenenti ad una certa categoria
     * @param category Nome della categoria di cui si vogliono recupoerare tutti i PointOfInterest
     * @return Collection<PointOfInterest>
     */
    @Override
    public Collection<PointOfInterest> getPOIsByCategory(String category) {
        Collection<PointOfInterest> pois = map.getAllPOIs();
        Collection<PointOfInterest> poisWithCategory = new LinkedList<>();
        for (PointOfInterest poi:pois) {
            if (poi.getCategory().equals(category))
                poisWithCategory.add(poi);
        }
        return poisWithCategory;
    }

    private boolean isDeveloper(){
        return new SettingImp(getContext()).isDeveloper();
    }
}

