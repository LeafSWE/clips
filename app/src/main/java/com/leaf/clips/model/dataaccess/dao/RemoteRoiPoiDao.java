package com.leaf.clips.model.dataaccess.dao;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import junit.framework.Assert;
import java.lang.reflect.Type;

/**
 *Classe di utility per la conversione da JSON a RoiPoiTable
 */
public class RemoteRoiPoiDao {

    /**
     * Costruttore di default per la classe RemoteRoiPoiDao
     */
    public RemoteRoiPoiDao() {}



    /**
     * Metodo utilizzato per la conversione di un oggetto JsonObject in un oggetto RoiPoiTable, che viene ritornato
     * @param object Oggetto JSON che rappresenta un oggetto di tipo RoiPoiTable
     * @return  RoiPoiTable
     */
    public RoiPoiTable fromJSONToTable(JsonObject object) {
        // TODO: 30/04/16 chiedere perchè no nomi uguali
        Gson gson = new Gson();
        GsonBuilder gsonBldr = new GsonBuilder();
        JsonDeserializer<RoiPoiTable> deserializer = new JsonDeserializer<RoiPoiTable>() {
            @Override
            public RoiPoiTable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jObject = json.getAsJsonObject();
                int deserializatedRoiId = jObject.get("roiid").getAsInt();
                int deserializedPoiId = jObject.get("poiid").getAsInt();
                return new RoiPoiTable(deserializatedRoiId, deserializedPoiId);
            }
        };
        gsonBldr.registerTypeAdapter(RoiPoiTable.class, deserializer);
        RoiPoiTable roiPoiTable;
        roiPoiTable = gsonBldr.create().fromJson(object, RoiPoiTable.class);
        return roiPoiTable;
    }

}
