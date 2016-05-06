package com.leaf.clips.model.dataaccess.dao;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

/**
 *Classe che rappresenta una ennupla della tabella RoiPoi del database locale
 */
public class RoiPoiTable {

    /**
     * Identificativo del POI 
     */
    private final int poiID;

    /**
     * Identificativo del ROI 
     */
    private final int roiID;

    /**
     * Costruttore della classe RoiPoiTable
     * @param roiID Identificativo del ROI
     * @param poiID Identificativo del POI
     */
    public RoiPoiTable(int roiID, int poiID) {
        this.roiID = roiID;
        this.poiID = poiID;
    }

    /**
     * Metodo che restituisce l'identificativo del POI
     * @return  int
     */
    public int getPoiID() {
        return poiID;
    }

    /**
     * Metodo che restituisce l'identificativo del ROI
     * @return  int
     */
    public int getRoiID() {
        return roiID;
    }

}
