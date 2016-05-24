package com.leaf.clips.model.beacon;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import java.util.PriorityQueue;

/**
 * Interfaccia che espone i metodi utili per accedere
 * alle funzionalità di un logger
 */
public interface Logger {
    /**
     * Metodo che salva le informazioni contenute nell'oggetto su di un
     * file
     */
    void log();

    /**
     * Metodo che aggiunge all’insieme di informazioni di beacon già
     * eventualmente presenti le informazioni riguardanti i beacon passati
     * in ingresso
     * @param beacons Insieme dei beacon di cui salvare le informazioni
     */
    void add(PriorityQueue<MyBeacon> beacons);

    /**
     * Metodo che, dato il nome di un file di log, ritorna l’informazione
     * in esso contenuta sotto forma di stringa
     * @param name Nome del file di log da cui reperire le informazioni
     * @return
     */
    String open(String name);

    /**
     * Metodo per la rimozione di un log precedentemente salvato
     * @param name Nome del log da rimuovere
     */
    boolean remove(String name);

    /**
     * Metodo che salva le informazioni contenute nell’oggetto su di un
     * file con nome uguale alla stringa passata come parametro
     * @param name Nome da dare al file
     */
    void save(String name);

    /**
     * Metodo per l'accesso alle informazioni nel log temporaneo
     * @return StringBuffer Informazioni nel log temporaneo
     */
    StringBuffer getData();
}
