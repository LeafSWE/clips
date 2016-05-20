package com.leaf.clips.presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.R;
import com.leaf.clips.model.usersetting.InstructionPreference;
import com.leaf.clips.model.usersetting.PathPreference;
import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.view.PreferencesViewImp;

import javax.inject.Inject;

/**
 *È una classe che estende AppCompactActivity che consente di gestire le preferenze utente recuperandole dal model
 */
public class PreferencesActivity extends AppCompatActivity {

    /**
     * View associata a tale Activity
     */
    private PreferencesViewImp view;

    // TODO: 17/05/16 aggiungere a tracy/uml se va bene
    /**
     * Riferimento alle preferenze utente
     */
    @Inject
    Setting setting;

    /**
     * Metodo che inizializza la View associata
     * @param savedInstanceState Componente per salvare lo stato dell'applicazione
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);
        view = new PreferencesViewImp(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        view.setPreferences(R.xml.preferences);
    }

    // TODO: 17/05/16 se va bene aggiungere tracy/uml
    /**
     * Metodo che viene invocato ogni volta che si accede all'activity. Ha il compito di impostare le
     * preferenze sulla view
     */
    @Override
    protected void onResume(){
        super.onResume();
    }

    /**
     * Metodo che permette di salvare le preferenze utente
     */
    public void savePreferences(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String preferenceToSave = prefs.getString("path_pref", "error");
        if (preferenceToSave.equals(
                getResources().getString(R.string.default_path_preferences)))
            setting.setPathPreference(PathPreference.NO_PREFERENCE);
        else if (preferenceToSave.equals(
                getResources().getString(R.string.elevator_path_preferences)))
            setting.setPathPreference(PathPreference.ELEVATOR_PREFERENCE);
        else if (preferenceToSave.equals(
                getResources().getString(R.string.stair_path_preferences)))
            setting.setPathPreference(PathPreference.STAIR_PREFERENCE);
        preferenceToSave = prefs.getString("instr_pref", "error");
        if (preferenceToSave.equals(
                getResources().getString(R.string.default_instruction_preferences)))
            setting.setInstructionPreference(InstructionPreference.NO_PREFERENCE);
        else if (preferenceToSave.equals(
                getResources().getString(R.string.tts_instruction_preferences)))
            setting.setInstructionPreference(InstructionPreference.TEXT_TO_SPEECH);
        else if (preferenceToSave.equals(
                getResources().getString(R.string.sonar_instruction_prefereces)))
            setting.setInstructionPreference(InstructionPreference.SONAR);
    }

    /**
     * Metodo che ridefinisce il comportamento del pulsante della toolbar e lo mette uguale a premere
     * il tasto fisico back
     * @return true
     */
    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
}
