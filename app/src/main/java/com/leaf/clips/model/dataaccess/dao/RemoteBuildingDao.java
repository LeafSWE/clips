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
        // TODO: 30/04/16 chiedere perchè no nomi uguali
        GsonBuilder gsonBldr = new GsonBuilder();
        JsonDeserializer<BuildingTable> deserializer = new JsonDeserializer<BuildingTable>() {
            @Override
            public BuildingTable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                JsonObject jObject = json.getAsJsonObject();
                int deserializatedId = jObject.get("id").getAsInt();
                String deserializatedUuid = jObject.get("uuid").getAsString();
                int deserializatedMajor = jObject.get("major").getAsInt();
                String deserializatedName = jObject.get("name").getAsString();
                String deserializedDescription = jObject.get("description").getAsString();
                String deserializedOpeningHours = jObject.get("openingHours").getAsString();
                String deserializedAddress = jObject.get("address").getAsString();
                int deserializedMapVersion = jObject.get("mapVersion").getAsInt();
                String deserializedMapSize = jObject.get("dimension").getAsString();

                return new BuildingTable(deserializatedId, deserializatedUuid, deserializatedMajor,
                        deserializatedName, deserializedDescription, deserializedOpeningHours,
                        deserializedAddress, deserializedMapVersion, deserializedMapSize);
            }
        };
        gsonBldr.registerTypeAdapter(BuildingTable.class, deserializer);
        BuildingTable buildingTable;
        buildingTable = gsonBldr.create().fromJson(object, BuildingTable.class);
        return buildingTable;
    }

}
