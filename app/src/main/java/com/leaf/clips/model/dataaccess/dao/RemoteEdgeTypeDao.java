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
 *Classe di utility per la conversione da JSON a EdgeTypeTable
 */
public class RemoteEdgeTypeDao {

    /**
     * Costruttore di default per la classe RemoteEdgeTypeDao
     */
    public RemoteEdgeTypeDao(){}

    /**
     * Metodo utilizzato per la conversione di un oggetto JsonObject in un oggetto EdgeTypeTable, che viene ritornato
     * @param object Oggetto JSON che rappresenta un oggetto di tipo EdgeTypeTable
     * @return  EdgeTypeTable
     */
    public EdgeTypeTable fromJSONToTable(JsonObject object) {
        Gson gson = new Gson();
        // TODO: 29/04/16 chiedere perchè no nomi uguali
        GsonBuilder gsonBldr = new GsonBuilder();
        JsonDeserializer<EdgeTypeTable> deserializer = new JsonDeserializer<EdgeTypeTable>() {
            @Override
            public EdgeTypeTable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jObject = json.getAsJsonObject();
                int deserializatedId = jObject.get("id").getAsInt();
                String deserializedTypeName = jObject.get("description").getAsString();
                Assert.assertNotNull(deserializedTypeName);
                return new EdgeTypeTable(deserializatedId, deserializedTypeName);
            }
        };
        gsonBldr.registerTypeAdapter(EdgeTypeTable.class, deserializer);
        EdgeTypeTable edgeTypeTable;
        edgeTypeTable = gsonBldr.create().fromJson(object, EdgeTypeTable.class);
        return edgeTypeTable;
    }

}
