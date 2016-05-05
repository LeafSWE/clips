package com.leaf.clips.model.dataaccess.dao;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

/**
 *Classe che rappresenta una ennupla della tabella Category del database locale
 */
public class CategoryTable {

    /**
     * Nome della categoria
     */
    private final String description;

    /**
     * Identificativo numerico dell'oggetto CategoryTable
     */
    private final int id;

    /**
     * Costruttore della classe CategoryTable
     * @param id Identificativo numerico dell'oggetto CategoryTable
     * @param description Nome della categoria
     */
    public CategoryTable(int id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * Metodo che restituisce il nome della categoria
     * @return  String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Metodo che restituisce l'identificativo numerico dell'oggetto CategoryTable
     * @return  int
     */
    public int getId() {
        return id;
    }

}
