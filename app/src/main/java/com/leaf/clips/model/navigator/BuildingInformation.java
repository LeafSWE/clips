package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 *
 *
 */

import android.support.v4.content.LocalBroadcastManager;

/**
 *Classe che rappresenta le informazioni di un edificio
 */
public class BuildingInformation {

    /**
     * Indirizzo dell'edificio
     */
    private final String address;

    /**
     * Descrizione dell'edificio
     */
    private final String description;

    /**
     * Nome dell'edificio
     */
    private final String name;

    /**
     * Orari di apertura dell'edificio
     */
    private final String  openingHours;

    /**
     * Costruttore della classe BuildingInformation
     * @param name Nome dell'edificio
     * @param description Descrizione dell'edificio
     * @param openingHours Orari di apertura dell'edificio
     * @param address Indirizzo dell'edificio
     */
    public BuildingInformation(String name, String description, String openingHours, String address) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.openingHours = openingHours;
    }

    /**
     * Metodo che ritorna l'indirizzo dell'edificio al quale tale oggetto è associato
     * @return  String
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Metodo che ritorna la descrizione dell'edificio al quale tale oggetto è associato
     * @return  String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Metodo che ritorna il nome dell'edificio al quale tale oggetto è associato
     * @return  String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Metodo che ritorna gli orari dell'edificio al quale tale oggetto è associato
     * @return  String
     */
    public String getOpeningHours() {
        return this.openingHours;
    }

    /**
     * Metodo che ritorna tutte le informazioni dell'edificio al quale tale oggetto è associato sottoforma di stringa
     * @return  String
     */
    public String toString() {
        return "Nome: " + this.name + "\n" +
                "Indirizzo: " + this.address + "\n" +
                "Descrizione: " + this.description + "\n" +
                "Orario apertura: " + this.openingHours;
    }

}

