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
public interface NavigationListener {

    void informationUpdate(ProcessedInformation info);

    void pathError();
}
