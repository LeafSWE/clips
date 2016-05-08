package com.leaf.clips.model;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

/**
 * Interfaccia che deve essere implementata da chi vuole usufruire delle informazioni contenute
 * nel model
 */
public interface InformationListener extends Listener {

    /**
     * Metodo che viene chiamato quando viene recuperata una mappa e non si siano verificate
     * eccezioni
     */
    void onDatabaseLoaded();

    /**
     * Metodo che viene richiamato nel caso in cui la mappa richiesta non sia stata trovata nel
     * database locale
     */
    boolean onLocalMapNotFound();

    /**
     * Metodo che viene richiamato nel caso in cui la mappa richiesta non sia stata trovata nel
     * database remoto
     */
    void onRemoteMapNotFound();

    /**
     * Metodo che viene invocato nel caso in cui non si riescano a reperire le informazioni sulle
     * mappe dal database remoto
     */
    void cannotRetrieveRemoteMapDetails();

    /**
     * Metodo che viene invocato nel caso in cui la versione della mappa locale differisca dalla
     * versione della mappa nel databaase remoto(l'ultima versione disponibile)
     */
    boolean noLastMapVersion();
}
