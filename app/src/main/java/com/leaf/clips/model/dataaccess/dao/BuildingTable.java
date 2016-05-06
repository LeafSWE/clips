package com.leaf.clips.model.dataaccess.dao;
/**
 * @author
 * @version 0.00
 * @since 0.00
 *
 *
 */

/**
 *Classe che rappresenta una ennupla della tabella Building del database locale
 */
public class BuildingTable {

    /**
     * Indirizzo dell'edificio
     */
    private final String address;

    /**
     * Descrizione dell'edificio
     */
    private final String description;

    /**
     * Identificativo dell'edificio
     */
    private final int id;

    /**
     * Major dell'edificio
     */
    private final int major;

    /**
     * Dimensione della mappa (in MB)
     */
    private final String mapSize;

    /**
     * Versione corrente della mappa
     */
    private final int mapVersion;

    /**
     * Nome dell'edificio
     */
    private final String name;

    /**
     * Orario dell'apertura dell'edificio
     */
    private final String openingHours;

    /**
     * Identificativo dell'applicazione
     */
    private final String uuid;

    /**
     * Costruttore della classe BuildingTable
     * @param id Identificativo numerico dell'oggetto BuildingTable
     * @param uuid Identificativo univoco
     * @param major Major dell'edificio
     * @param name Nome dell'edificio mappato
     * @param description Descrizione dell'edificio mappato
     * @param openingHours Orari di apertura dell'edificio mappato
     * @param address Indirizzo dell'edificio mappato
     * @param mapVersion Versione della mappa
     * @param mapSize Dimensione della mappa (espressa in MB)
     */
    public BuildingTable(int id, String uuid, int major, String name, String description, String openingHours, String address, int mapVersion, String mapSize) {
        this.id = id;
        this.uuid = uuid;
        this.major = major;
        this.name = name;
        this.description = description;
        this.openingHours = openingHours;
        this.address = address;
        this.mapVersion = mapVersion;
        this.mapSize = mapSize;
    }

    /**
     * Metodo che ritorna l'indirizzo dell'edificio
     * @return  String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Metodo che ritorna la descrizione dell'edificio
     * @return  String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Metodo che ritorna l'identificativo dell'edificio
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
     * Metodo che ritorna il nome dell'edificio
     * @return  String
     */
    public String getName() {
        return name;
    }

    /**
     * Metodo che ritorna l'orario di apertura dell'edificio
     * @return  String
     */
    public String getOpeningHours() {
        return openingHours;
    }

    /**
     * Metodo che ritorna la dimensione della mappa (in MB)
     * @return  String
     */
    public String getSize() {
        return mapSize;
    }

    /**
     * Metodo che ritorna l'identificativo dell'applicazione
     * @return  String
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * Metodo che ritorna la versione della mappa dell'edificio
     * @return  int
     */
    public int getVersion() {
        return mapVersion;
    }

}
