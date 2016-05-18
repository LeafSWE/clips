package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 *
 *
 */

/**
 *Classe che rappresenta una eccezione che può essere lanciata durante l'utilizzo della classe
 * Navigator
 */
public class NoGraphSetException extends NavigationExceptions {

    /**
     * Messaggio di default associato all'eccezione nel caso in cui si avvii la navigazione senza
     * aver settato il grafo
     */
    private final static String DEFAULT_MESSAGE = "Start navigation without graph set";

    /**
     * Stringa che rappresenta l'eccezione
     */
    private final String message;

    /**
     * Costruttore di default della classe NoGraphSetException
     */
    public NoGraphSetException() {
        this.message = DEFAULT_MESSAGE;
    }

    /**
     * Costruttore della classe NoGraphSetException
     * @param message Messaggio che rappresenta l'eccezione
     */
    public NoGraphSetException(String message) {
        this.message = message;
    }

    /**
     * Metodo che ritorna il messaggio che rappresenta l'eccezione
     * @return  String
     */
    @Override
    public String getException() {
        return this.message;
    }

}

