package com.leaf.clips.model.dataaccess.dao;
/**
 * @author
 * @version 0.00
 * @since 0.00
 *
 *
 */

import java.util.Collection;

/**
 *Interfaccia che espone i metodi per un DAO per accedere alla tabella "Photo" del database locale
 */
public interface PhotoDao {

    /**
     * Metodo che permette la rimozione delle informazioni di una foto dalla tabella "Photo" del database locale
     * @param id Identificativo della foto di cui rimuovere le informazioni dal database locale
     */
    void deletePhoto(int id);

    /**
     * Metodo che viene utilizzato per recuperare le informazioni di tutte foto associate ad un Edge presenti nella tabella "Photo" del database locale
     * @param id Identificativo dell'Edge
     * @return  Collection<PhotoTable>
     */
    Collection<PhotoTable> findAllPhotosOfEdge(int id);

    /**
     * Metodo per recuperare le informazioni di una foto dal database locale tramite il suo identificativo, sotto forma di oggetto PhotoTable
     * @param id Identificativo della foto
     * @return  PhotoTable
     */
    PhotoTable findPhoto(int id);

    /**
     * Metodo che permette l'inserimento delle informazioni di una foto in una entry della tabella "Photo" del database locale
     * @param toInser Oggetto di tipo Photo che contiene le informazioni della foto
     * @return  int
     */
    int insertPhoto(PhotoTable toInser);

    /**
     * Metodo per aggiornare le informazioni di una foto nella tabella "Photo" del database locale
     * @param id Identificativo della foto di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate della foto
     * @return  boolean
     */
    boolean updatePhoto(int id, PhotoTable toUpdate);

}
