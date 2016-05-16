package com.leaf.clips.model;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import com.leaf.clips.model.navigator.ProcessedInformation;

/**
 * Interfaccia che deve essere implementata da chi vuole usufruire della navigazione
 */
public interface NavigationListener extends Listener {

    /**
     * Metodo che viene invocato quando c'è un aggiornamento nelle informazioni di navigazione
     * @param info informazioni aggiornate
     */
    void informationUpdate(ProcessedInformation info);

    /**
     * Metodo che viene invocato in caso di errori di navigazione
     */
    void pathError();
}
