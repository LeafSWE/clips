package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 *
 *
 */

/**
 * Classe che rappresenta una eccezione che può essere lanciata durante l'utilizzo della classe
 * Navigator
 */
public class PathException extends NavigationExceptions {

    /**
     * Messaggio di default associato all'eccezione nel caso in cui il beacon più potente
     * nella lista di beacon in ingresso sia associato ad una RegionOfInterest non appartenente ad
     * una di quelle presenti nel percorso calcolato
     */
    private final static String DEFAULT_MESSAGE = "Nearby beacon not in ROI of calculate path";

    /**
     * Stringa che rappresenta l'eccezione
     */
    private final String message;

    /**
     * Costruttore di default della classe PathException
     */
    public PathException() {
        this.message = DEFAULT_MESSAGE;
    }

    /**
     * Costruttore della classe PathException
     * @param message Messaggio che rappresenta l'eccezione
     */
    public PathException(String message) {
        this.message = message;
    }

    /**
     * Metodo che ritorna il messaggio che rappresenta l'eccezione
     * @return  String
     */
    @Override
    public String getException() {
        return message;
    }

}

