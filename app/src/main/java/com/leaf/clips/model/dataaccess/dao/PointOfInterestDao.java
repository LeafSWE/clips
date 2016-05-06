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
 *Interfaccia che espone i metodi per un DAO per accedere alla tabella "POI" del database locale
 */
public interface PointOfInterestDao {

    /**
     * Metodo che permette la rimozione delle informazioni di un edificio dalla tabella "POI" del database locale
     * @param id Identificativo del POI di cui rimuovere le informazioni dal database locale
     */
    void deletePointOfInterest(int id);

    /**
     * Metodo che viene utilizzato per recuperare le informazioni di tutti gli edifici presenti nella tabella "POI" del database locale
     * @param major Identificativo Major associato a tutti i beacon presenti in uno stesso edificio
     * @return  Collection<PointOfInterestTable>
     */
    Collection<PointOfInterestTable> findAllPointsWithMajor(int major);

    /**
     * Metodo per recuperare le informazioni di un edificio dal database locale tramite il suo identificativo, sotto forma di oggetto PointOfInterestTable
     * @param id Identificativo del POI di cui recuperare le informazioni
     * @return  PointOfInterestTable
     */
    PointOfInterestTable findPointOfInterest(int id);

    /**
     * Metodo che permette l'inserimento delle informazioni di un edificio in una entry della tabella "POI" del database locale
     * @param toInsert Oggetto di tipo PointOfInterestTable che contiene le informazioni dell'edificio
     * @return  int
     */
    int insertPointOfInterest(PointOfInterestTable toInsert);

    /**
     * Metodo per aggiornare le informazioni di un edificio nella tabella "POI" del database locale
     * @param id Identificativo del POI di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate del POI
     * @return  boolean
     */
    boolean updatePointOfInterest(int id, PointOfInterestTable toUpdate);

}
