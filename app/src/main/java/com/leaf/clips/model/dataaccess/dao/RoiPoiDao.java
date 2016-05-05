package com.leaf.clips.model.dataaccess.dao;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

/**
 *Interfaccia che espone i metodi per un DAO per accedere alla tabella "ROIPOI" del database locale
 */
public interface RoiPoiDao {

    /**
     * Metodo che permette la rimozione delle associazioni tra un ROI e i POI ad esso associato dalla tabella "ROIPOI" del database locale
     * @param poi Identificativo del POI di cui rimuovere le associazioni con i ROI dal database locale
     */
    void deleteRoiPoisWherePoi(int poi);

    /**
     * Metodo che permette la rimozione delle associazioni tra un POI e i ROI ad esso associato dalla tabella "ROIPOI" del database locale
     * @param roi Identificativo del ROI di cui rimuovere le associazioni con i POI dal database locale
     */
    void deleteRoiPoisWhereRoi(int roi);

    /**
     * Metodo per recuperare tutti gli identificativi dei POI associati ad un ROI
     * @param roi Identificativo del ROI di cui recuperare gli identificativi di tutti i POI associati
     * @return  int[]
     */
    int[] findAllPointsWithRoi(int roi);

    /**
     * Metodo per recuperare tutti gli identificativi dei ROI associati ad un POI
     * @param poi Identificativo del POI di cui recuperare gli identificativi di tutti i ROI associati
     * @return  int[]
     */
    int[] findAllRegionsWithPoi(int poi);

    /**
     * Metodo che permette l'inserimento tra ROI ed POI nel database locale utilizzando un oggetto RoiPoiTable
     * @param toInsert Oggetto di tipo RoiPoiTable che contiene le associazioni tra ROI e POI
     * @return  int
     */
    int insertRoiPoi(RoiPoiTable toInsert);

    /**
     * Metodo per aggiornare le associazioni tra POI e ROI
     * @param poi Identificativo del POI di cui aggiungere una associazione con un ROI
     * @param roi Identificativo del ROI di cui aggiungere una associazione con un POI
     * @param toUpdate Oggetto che contiene le associazioni tra ROI e POI
     * @return  boolean
     */
    boolean updateRoiPoi(int poi, int roi, RoiPoiTable toUpdate);

}
