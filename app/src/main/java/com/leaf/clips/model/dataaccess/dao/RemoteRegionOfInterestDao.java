package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 *Classe di utility per la conversione da JSON a RegionOfInterestTable
 */
public class RemoteRegionOfInterestDao {



    /**
     * Costruttore di default per la classe RemoteRegionOfInterestDao
     */
    public RemoteRegionOfInterestDao() {
    }

    /**
     * Metodo utilizzato per la conversione di un oggetto JsonObject in un oggetto RegionOfInterestTable, che viene ritornato
     *
     * @param object Oggetto JSON che rappresenta un oggetto di tipo RegionOfInterestTable
     * @return RegionOfInterestTable
     */
    public RegionOfInterestTable fromJSONToTable(JsonObject object) {
        Gson gson = new Gson();
        RegionOfInterestTable regionOfInterestTable;
        regionOfInterestTable = gson.fromJson(object, RegionOfInterestTable.class);
        return regionOfInterestTable;
    }
}
