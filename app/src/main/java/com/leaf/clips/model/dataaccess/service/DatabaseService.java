package com.leaf.clips.model.dataaccess.service;
/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.00
 *
 *
 */

import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.navigator.BuildingMap;

import java.io.IOException;
import java.util.Collection;

/**
 *Interfaccia che espone tutti i metodi per l'accesso alle mappe contenute nel database locale
 * o remoto
 */
public interface DatabaseService {

    /**
     * Metodo per cancellare una mappa a partire dall'identificativo di un edificio
     * @param buildingMap Mappa dell'edificio da rimuovere
     */
    void deleteBuilding(BuildingMap buildingMap);

    /**
     * Metodo che ritorna la lista di tutti gli oggetti BuildingTable presenti nel database locale
     * @return  Collection<BuildingTable> Le informazioni sulle mappe degli edifici già scaricate
     */
    Collection<BuildingTable> findAllBuildings();

    /**
     * Metodo che ritorna la lista di tutti gli oggetti BuildingTable presenti nel database remoto
     * @return  Collection<BuildingTable> Le informazioni sulle mappe degli edifici supportati
     * dall'applicazione
     */
    Collection<BuildingTable> findAllRemoteBuildings() throws IOException;

    /**
     * Metodo per il recupero di un oggetto BuildingMap da un database locale tramite
     * l'identificativo Major uguale in tutti i beacon presenti in uno stesso edificio
     * @param major Identificativo major uguale per tutti i beacon presenti in uno stesso edificio
     * @return  BuildingMap La mappa dell'edificio richiesto
     */
    BuildingMap findBuildingByMajor(int major);

    /**
     * Metodo per effettuare il download di una mappa dal database remoto a partire
     * dall'identificativo major uguale per tutti i beacon presenti in un certo edificio
     * @param major Identificativo major uguale per tutti i beacon presenti in uno stesso edificio
     * @return  BuildingMap La mappa dell'edificio richiesto
     */
    BuildingMap findRemoteBuildingByMajor(int major) throws IOException;

    /**
     * Metodo per verificare la presenza di una mappa di un edificio nel database locale
     * @param major Major dell'edificio
     * @return  boolean True se la mappa è presente nel database locale, false in caso contrario
     */
    boolean isBuildingMapPresent(int major);

    /**
     * Metodo per verificare la presenza di una mappa di un edificio nel database remoto
     * @param major Major dell'edificio
     * @return boolean True se la mappa è presente nel database remoto, false in caso contrario
     */
    boolean isRemoteMapPresent(int major) throws IOException;

    /**
     * Metodo per verificare se la mappa di un edificio è aggiornata all'ultima versione disponibile
     * @param major Major dell'edificio
     * @return  boolean True se la mappa è aggiornata all'ultima versione disponibile, false in
     * caso contrario
     */
    boolean isBuildingMapUpdated(int major) throws IOException;

    /**
     * Metodo per aggiornare la mappa di un edificio all'ultima versione disponibile
     * @param major Major dell'edificio
     */
    void updateBuildingMap(int major) throws IOException;

}
