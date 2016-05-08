package com.leaf.clips.model.dataaccess.dao;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

/**
 *Classe che rappresenta una ennupla della tabella RegionOfInterest del database locale
 */
public class RegionOfInterestTable {

    /**
     * Identificativo della RegionOfInterest
     */
    private final int id;

    /**
     * Major dell'edificio
     */
    private final int major;

    /**
     * Identificativo del beacon associato alla ROI
     */
    private final int minor;

    /**
     * UUID dell'applicazione
     */
    private final String uuid;

    /**
     * Costruttore della classe RegionOfInterestTable
     * @param id Identificativo della RegionOfInterest
     * @param uuid Identificativo dell'applicazione
     * @param major Major dell'edificio
     * @param minor Identificativo del beacon associato alla ROI
     */
    public RegionOfInterestTable(int id, String uuid, int major, int minor) {  
        this.uuid = uuid;
        this.id = id;
        this.major = major;
        this.minor = minor;
    }

    /**
     * Metodo che ritorna l'identificativo della ROI
     * @return  int
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo che ritorna il major dell'edificio
     * @return  int
     */
    public int getMajor() {
        return major;
    }

    /**
     * Metodo che ritorna l'identificativo del beacon associato alla ROI
     * @return  int
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Metodo che ritorna l'UUID dell'applicazione
     * @return  String
     */
    public String getUUID() {
        return uuid;
    }

}
