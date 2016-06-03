package com.leaf.clips.view;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import android.widget.Adapter;

/**
 *Interfaccia che espone i metodi per aggiornare la UI contenente le preferenze dell'utente rispetto al percorso consigliato e alla modalità di fruizione delle istruzioni di navigazione
 */
public interface PreferencesView {

    /**
     * Metodo utilizzato per visualizzare le preferenze dell'applicazione
     * @param xmlPreferenceResource Risorsa XML che contiene tutte le preferenze impostabili
     */
    void setPreferences(int xmlPreferenceResource);

}

