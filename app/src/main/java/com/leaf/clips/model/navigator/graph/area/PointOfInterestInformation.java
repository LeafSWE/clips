package com.leaf.clips.model.navigator.graph.area;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
/**
 *Classe che rappresenta le informazioni associate ad un POI
 */
public class PointOfInterestInformation {
    
    /**
     * Categoria di appartenenza del POI
     */
    private final String  category;

    /**
     * Descrizione del POI
     */
    private final String  description;

    /**
     * Nome del POI
     */
    private final String  name;

    /**
     * Costruttore della classe PointOfInterestInformation
     * @param name Nome del POI
     * @param description Descrizione del POI
     * @param category Categoria di appartenenza del POI
     */
    public PointOfInterestInformation(String name, String description, String category){
        this.name = name;
        this.description = description;
        this.category = category;
    }

    /**
     * Metodo che ritorna la categoria di appartenenza del PointOfInterest a cui l'oggetto è associato
     * @return  String
     */
    public String getCategory(){
        return this.category;
    }

    /**
     * Metodo che ritorna la descrizione del PointOfInterest a cui l'oggetto è associato
     * @return  String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Metodo che ritorna il nome del PointOfInterest a cui l'oggetto è associato
     * @return  String
     */
    public String getName() {
        return this.name;
    }

}
