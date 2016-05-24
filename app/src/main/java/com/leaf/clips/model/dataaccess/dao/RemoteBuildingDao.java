package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 *Classe di utility per la conversione da JSON a BuildingTable
 */
public class RemoteBuildingDao {

    /**
     * Costruttore di default per la classe RemoteBuildingDao
     */
    public RemoteBuildingDao(){}

    /**
     * Metodo utilizzato per la conversione di un oggetto JsonObject in un oggetto BuildingTable, che viene ritornato
     * @param object Oggetto JSON che rappresenta un oggetto di tipo BuildingTable
     * @return  BuildingTable
     */
    public BuildingTable fromJSONToTable(JsonObject object){
        Gson gson = new Gson();
        BuildingTable buildingTable;
        buildingTable = gson.fromJson(object, BuildingTable.class);
        return buildingTable;
    }

}
