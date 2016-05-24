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
     * Metodo utilizzato per visualizzare le preferenze dell'utente riguardo la fruizione delle istruzioni di navigazione
     * @param adp Collegamento tra le preferenze riguardanti la fruizione delle istruzioni di navigazione e la view in cui esse devono essere mostrate
     */
    void setInstructionPreferences(Adapter adp);

    /**
     * Metodo utilizzato per visualizzare le preferenze dell'utente relative al percorso proposto
     * @param adp Collegamento tra le preferenze riguardanti le preferenze del percorso di navigazione e la view in cui esse devono essere mostrate
     */
    void setPathPreferences(Adapter adp);

    /**
     * Metodo utilizzato per visualizzare le preferenze dell'applicazione
     * @param xmlPreferenceResource Risorsa XML che contiene tutte le preferenze impostabili
     */
    void setPreferences(int xmlPreferenceResource);

}

