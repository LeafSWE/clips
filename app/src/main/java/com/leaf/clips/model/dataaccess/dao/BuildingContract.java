package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

import android.provider.BaseColumns;

/**
 *Classe che contiene le informazioni corrette della tabella Building del database locale
 */
public class BuildingContract implements BaseColumns{

    /**
     * Valore della colonna address. Valore di default "address"
     */
    public final static String COLUMN_ADDRESS="address";

    /**
     * Valore della colonna description. Valore di default "description"
     */
    public final static String COLUMN_DESCRIPTION="description";

    /**
     * Valore della colonna id. Valore di default "id"
     */
    public final static String COLUMN_ID="id";

    /**
     * Valore della colonna mapVersion. Valore di default "mapVersion"
     */
    public final static String COLUMN_MAPVERSION="mapVersion";

    /**
     * Valore della colonna name. Valore di default "name"
     */
    public final static String COLUMN_NAME="name";

    /**
     * Valore della colonna openingHours. Valore di default "openingHours"
     */
    public final static String COLUMN_OPENINGHOURS="openingHours";

    /**
     * Valore della colonna uuid. Valore di default "uuid"
     */
    public final static String COLUMN_UUID="uuid";

    /**
     * Valore della colonna mapSize. Valore di default "size"
     */
    public final static String COLUMN_MAPSIZE="size";

    /**
     * Valore della colonna major. Valore di default "major"
     */
    public final static String COLUMN_MAJOR="major";

    /**
     * Nome della tabella.Valore di default "Building"
     */
    public final static String TABLE_NAME="Building";

}
