package com.leaf.clips.model;
/**
* @author
* @version 0.00 
* @since 0.00
* 
* 
*/

import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;

import java.util.Collection;
import java.util.PriorityQueue;

/**
*Interfaccia che si occupa di esporre tutti i metodi utili per accedere ad informazioni trattate dai vari pacchetti del Model
*/ 
public interface InformationManager { 

    /**
    * Metodo che ritorna tutte le categorie di POI all'interno dell'edificio
    * @return  Collection<String>
    */
     Collection<String> getAllCategories();

    /**
    * Metodo che ritorna la PriorityQueue<MyBeacon>, eventualmente vuota, dei beacon visibili
    * @return  PriorityQueue<MyBeacon>
    */
     PriorityQueue<MyBeacon> getAllVisibleBeacons();

    /**
    * Metodo che ritorna la mappa dell'edificio se questa è già stata caricata dal database locale. Viene lanciata una eccezione di tipo NoBeaconSeenException nel caso in cui non sia stata caricata la mappa poiché non è stato ancora ricevuto alcun beacon
    * @return  BuildingMap
    */
     BuildingMap getBuildingMap() throws NoBeaconSeenException;

    /**
    * Metodo che ritorna un oggetto DatabaseService che permette di interrogare il database
    * @return  DatabaseService
    */
     DatabaseService getDatabaseService();

    /**
    * Metodo che, dato il nome di un log, ritorna l'informazione in esso contenuta sotto forma di stringa
    * @param name Nome del file di log da cui reperire l'informazione
    * @return  String
    */
     String getLogInfo(String name);

    /**
    * Metodo che ritorna l'insieme di POI associati al beacon rilevato con il segnale più potente. Viene lanciata una eccezione di tipo NoBeaconSeenException nel caso in cui venga invocato il metodo ma non è stato rilevato ancora alcun beacon
    * @return  Collection<PointOfInterest>
    */
    Collection<PointOfInterest> getNearbyPOIs() throws NoBeaconSeenException;

    /**
     * Metodo che permette di rimuovere un log delle informazioni dei beacon visibili
     * @param filename Nome del file da rimuovere
     */
    void removeBeaconInformationFile(String filename);

    /**
     * Metodo che permette di salvare il log delle informazioni dei beacon visibili su file
     * @param filename Nome da dare al file da salvare
     */
    void saveRecordedBeaconInformation(String filename);

    /**
    * Metodo che permette di avviare il log delle informazioni dei beacon visibili
    */
    void startRecordingBeacons();


}

