package com.leaf.clips.view;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Adapter;

import com.leaf.clips.R;
import com.leaf.clips.presenter.PreferencesActivity;

/**
 *Classe che si occupa di mostrare la UI utile alla modifica delle preferenze dell'utente
 */
public class PreferencesViewImp implements PreferencesView, SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * Presenter della View
     */
    private PreferencesActivity presenter;

    // TODO: 17/05/16 aggiungere tracy/uml se va bene
    /**
     * Fragment per mostrare le preferenze
     */
    private PreferenceFragment preferenceFragment;

    /**
     * Costruttore della classe PreferencesViewImp
     * @param presenter Presenter della View che viene creata
     */
    public PreferencesViewImp(PreferencesActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.preferences_layout);

        preferenceFragment = new PreferenceFragment() {};

        presenter.getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, preferenceFragment)
                .commit();
    }

    // TODO: 17/05/16 aggiungere tracy/uml se va bene
    /**
     * Metodo utilizzato per visualizzare le preferenze dell'applicazione
     * @param xmlPreferenceResource Risorsa XML che contiene tutte le preferenze impostabili
     */
    @Override
    public void setPreferences(int xmlPreferenceResource) {
        preferenceFragment.addPreferencesFromResource(xmlPreferenceResource);
        preferenceFragment.getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    // TODO: 17/05/16 aggiungere tracy/uml se va bene
    /**
     * Metodo che viene invocato ad ogni cambio di preferenza. Tale cambio viene propagato al presenter
     * che ha il compito di salvarlo.
     * @param sharedPreferences Mappa chiave-valore dove vengono salvate le preferenze
     * @param key Preferenza che viene cambiata
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i("setting", "OnSharedPreferenceChanged" + key);
        if (key.equals("path_pref") || (key.equals("instr_pref"))) {
            Log.i("setting", "Entra if->OnSharedPreferenceChanged");
            presenter.savePreferences();
        }
    }

    /**
     * Metodo utilizzato per visualizzare le preferenze dell'utente riguardo la fruizione delle istruzioni di navigazione
     * @param adp Collegamento tra le preferenze riguardanti la fruizione delle istruzioni di navigazione e la view in cui esse devono essere mostrate
     */
    @Override
    public void setInstructionPreferences(Adapter adp) {
        // TODO: 17/05/16 togliere se quanto fatto va bene
    }

    /**
     * Metodo utilizzato per visualizzare le preferenze dell'utente relative al percorso proposto
     * @param adp Collegamento tra le preferenze riguardanti le preferenze del percorso di navigazione e la view in cui esse devono essere mostrate
     */
    @Override
    public void setPathPreferences(Adapter adp) {
        // TODO: 17/05/16 togliere se quanto fatto va bene
    }
}
