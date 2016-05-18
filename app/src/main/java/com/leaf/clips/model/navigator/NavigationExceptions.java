package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 *
 *
 */

/**
 * Classe base per le eccezioni che possono essere lanciate durante la navigazione.
 * Estende java.lang.Exception
 */
public abstract class NavigationExceptions extends Exception {

    /**
     * Metodo che ritorna una stringa che rappresenta il motivo per cui è stata lanciata l'eccezione
     * @return  String
     */
    public abstract String getException();

}

