package com.leaf.clips.model.dataaccess.dao;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 *Classe di utility per la conversione da JSON a EdgeTable
 */
public class RemoteEdgeDao {

    /**
     * Costruttore di default per la classe RemoteEdgeDao
     */
    public RemoteEdgeDao(){}

    /**
     * Metodo utilizzato per la conversione di un oggetto JsonObject in un oggetto EdgeTable, che viene ritornato
     * @param object Oggetto JSON che rappresenta un oggetto di tipo EdgeTable
     * @return  EdgeTable
     */
    public EdgeTable fromJSONToTable(JsonObject object) {
        Gson gson = new Gson();
        EdgeTable edgeTable;
        edgeTable = gson.fromJson(object, EdgeTable.class);
        return edgeTable;
    }

}
