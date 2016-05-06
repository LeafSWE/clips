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
 *Interfaccia che espone i metodi per un DAO per accedere alla tabella "ROI" del database locale
 */
public interface RegionOfInterestDao {

    /**
     * Metodo che permette la rimozione delle informazioni di una RegionOfInterest dalla tabella "ROI" del database locale
     * @param id Identificativo della RegionOfInterest di cui rimuovere le informazioni dal database locale
     */
    void deleteRegionOfInterest(int id);

    /**
     * Metodo che viene utilizzato per recuperare le informazioni di tutte le RegionOfInterest associato ad certo edificio, dato il major dell'edificio
     * @param major Major dell'edificio
     * @return  Collection<RegionOfInterestTable>
     */
    Collection<RegionOfInterestTable> findAllRegionsWithMajor(int major);

    /**
     * Metodo per recuperare le informazioni di una RegionOfInterest dal database locale tramite il suo identificativo, sotto forma di oggetto RegionOfInterestTable
     * @param id Identificativo della RegionOfInterest di cui recuperare le informazioni
     * @return  RegionOfInterestTable
     */
    RegionOfInterestTable findRegionOfInterest(int id);

    /**
     * Metodo che permette l'inserimento delle informazioni di una RegionOfInterest in una entry della tabella "ROI" del database locale
     * @param toInsert Oggetto di tipo RegionOfInterestTable che contiene le informazioni della RegionOfInterest
     * @return  int
     */
    int insertRegionOfInterest(RegionOfInterestTable toInsert);

    /**
     * Metodo per aggiornare le informazioni di una RegionOfInterest nella tabella "ROI" del database locale
     * @param id Identificativo della RegionOfInterest di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate della RegionOfInterest
     * @return  boolean
     */
    boolean updateRegionOfInterest(int id, RegionOfInterestTable toUpdate);

}
