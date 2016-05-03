package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 *Classe di utility per la conversione da JSON a PointOfInterestTable
 */
public class RemotePointOfInterestDao {

    // TODO: 30/04/16 chiedere modifica alla classe
    private static final Gson gson = new Gson();
    /**
     * Costruttore di default per la classe RemotePointOfInterestDao
     */
    public RemotePointOfInterestDao() {}

    /**
     * Metodo utilizzato per la conversione di un oggetto JsonObject in un oggetto PointOfInterestTable, che viene ritornato
     * @param object Oggetto JSON che rappresenta un oggetto di tipo PointOfInterestTable
     * @return  PointOfInterestTable
     */
    public PointOfInterestTable fromJSONToTable(JsonObject object) {

        PointOfInterestTable pointOfInterestTable;
        pointOfInterestTable = gson.fromJson(object, PointOfInterestTable.class);
        return pointOfInterestTable;
    }

}