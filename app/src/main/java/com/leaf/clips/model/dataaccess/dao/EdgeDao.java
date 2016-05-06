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
 *Interfaccia che espone i metodi per un DAO per accedere alla tabella "Edge" del database locale
 */
public interface EdgeDao {

    /**
     * Metodo che permette la rimozione delle informazioni di un edificio dalla tabella "Edge" del database locale
     * @param id Identificativo dell'arco di cui rimuovere le informazioni dal database locale
     */
    void deleteEdge(int id);

    /**
     * Metodo che viene utilizzato per recuperare le informazioni di tutti gli archi presenti nella tabella "Edge" del database locale
     * @param major Identificativo major dell'edificio di cui si vogliono recuperare tutti gli archi
     * @return  Collection<EdgeTable>
     */
    Collection<EdgeTable> findAllEdgesOfBuilding(int major);

    /**
     * Metodo per recuperare le informazioni di un arco dal database locale tramite il suo identificativo, sotto forma di oggetto EdgeTable
     * @param id Identificativo dell'arco di cui recuperare le informazioni
     * @return  EdgeTable
     */
    EdgeTable findEdge(int id);

    /**
     * Metodo che permette l'inserimento delle informazioni di un edificio in una entry della tabella "Edge" del database locale
     * @param toInsert Oggetto di tipo EdgeTable che contiene le informazioni dell'arco
     * @return  int
     */
    int insertEdge(EdgeTable toInsert);

    /**
     * Metodo per aggiornare le informazioni di un edificio nella tabella "Edge" del database locale
     * @param id Identificativo dell'arco di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate dell'arco
     * @return  boolean
     */
    boolean updateEdge(int id, EdgeTable toUpdate);

}
