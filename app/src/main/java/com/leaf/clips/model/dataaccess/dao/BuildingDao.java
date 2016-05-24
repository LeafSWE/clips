package com.leaf.clips.model.dataaccess.dao;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

import java.util.Collection;

/**
 *Interfaccia che espone i metodi per un DAO per accedere alla tabella "Building" del database locale
 */
public interface BuildingDao {

    /**
     * Metodo che permette la rimozione delle informazioni di un edificio dalla tabella "Building" del database locale
     * @param id Identificativo dell'edificio di cui rimuovere le informazioni dal database locale
     */
    void deleteBuilding(int id);

    /**
     * Metodo che viene utilizzato per recuperare le informazioni di tutti gli edifici presenti nella tabella "Building" del database locale
     * @return  Collection<BuildingTable>
     */
    Collection<BuildingTable> findAllBuildings();

    /**
     * Metodo che viene utilizzato per recuperare le informazioni di tutti gli edifici presenti nella tabella "Building" del database locale
     * @param id Identificativo dell'edificio di cui recuperare le informazioni
     * @return  BuildingTable
     */
    BuildingTable findBuildingById(int id);

    /**
     * Metodo per recuperare le informazioni di un edificio dal database locale tramite il suo major, sotto forma di oggetto BuildingTable
     * @param major Major identificante l'edificio che deve essere recuperato dal database
     * @return  BuildingTable
     */
    BuildingTable findBuildingByMajor(int major);

    /**
     * Metodo che permette l'inserimento delle informazioni di un edificio in una entry della tabella "Building" del database locale
     * @param toInsert Oggetto di tipo BuildingTable che contiene le informazioni dell'edificio
     * @return  int
     */
    int insertBuilding(BuildingTable toInsert);

    /**
     * Metodo per verificare la presenza nel database locale delle informazioni di un edificio
     * @param major major dell'edificio
     * @return  boolean
     */
    boolean isBuildingMapPresent(int major);

    /**
     * Metodo per aggiornare le informazioni di un edificio nella tabella "Building" del database locale
     * @param id Identificativo dell'edificio di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate dell'edificio
     * @return  boolean
     */
    boolean updateBuilding(int id, BuildingTable toUpdate);

}
