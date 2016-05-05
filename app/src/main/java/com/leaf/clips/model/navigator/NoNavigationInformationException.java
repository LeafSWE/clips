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
public class NoNavigationInformationException extends NavigationExceptions {

    /**
     * Messaggio di default associato all'eccezione nel caso in cui non si sia ancora calcolato
     * un percorso
     */
    private final static String DEFAULT_MESSAGE = "No calculate path";

    /**
     * Stringa che rappresenta l'eccezione
     */
    private final String message;

    /**
     * Costruttore di default della classe NoNavigationInformationException
     */
    public NoNavigationInformationException() {
        this.message = DEFAULT_MESSAGE;
    }

    /**
     * Costruttore della classe NoNavigationInformationException
     * @param message Messaggio che rappresenta l'eccezione
     */
    public NoNavigationInformationException(String message) {
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

