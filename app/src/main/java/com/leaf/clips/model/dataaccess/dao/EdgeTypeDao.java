package com.leaf.clips.model.dataaccess.dao;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

/**
 *Interfaccia che espone i metodi per un DAO per accedere alla tabella "EdgeType" del database locale
 */
public interface EdgeTypeDao {

    /**
     * Metodo che permette la rimozione delle informazioni di un tipo di Edge dalla tabella "EdgeType" del database locale
     * @param id Identificativo del tipo di Edge di cui rimuovere le informazioni dal database locale
     * @return  void
     */
    void deleteEdgeType(int id);

    /**
     * Metodo per recuperare le informazioni di un tipo di Edge dal database locale tramite il suo identificativo, sotto forma di oggetto EdgeTypeTable
     * @param id Identificativo del tipo di Edge di cui recuperare le informazioni
     * @return  EdgeTypeTable
     */
    EdgeTypeTable findEdgeType(int id);

    /**
     * Metodo che permette l'inserimento delle informazioni del tipo di Edge in una entry della tabella "EdgeType" del database locale
     * @param toInsert Oggetto di tipo EdgeTypeTable che contiene le informazioni di un tipo di Edge
     * @return  int
     */
    int insertEdgeType(EdgeTypeTable toInsert);

    /**
     * Metodo per aggiornare le informazioni di un tipo di Edge nella tabella "EdgeType" del database locale
     * @param id Identificativo del tipo di Edge di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate del tipo di Edge
     * @return  boolean
     */
    boolean updateEdgeType(int id, EdgeTypeTable toUpdate);

}
