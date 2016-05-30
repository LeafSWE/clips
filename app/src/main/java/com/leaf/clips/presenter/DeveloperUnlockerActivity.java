package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.03
 * @since 0.00
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.view.DeveloperUnlockerView;
import com.leaf.clips.view.DeveloperUnlockerViewImp;

import javax.inject.Inject;

/**
 *  classe che estende AppCompactActivity che consente di gestire
 *  l'interazione tra DeveloperUnlockerView ed il model
 */

public class DeveloperUnlockerActivity extends AppCompatActivity {

    /**
     * View associata a tale Activity
     */
    DeveloperUnlockerView developerUnlockerView;

    /**
     * Impostazioni dell'utente
     */
    @Inject
    Setting userSetting;

    /**
     * Metodo che inizializza la View associata e recupera un riferimento alle impostazioni dell'utente
     * @param savedInstanceState Componente per salvare lo stato dell'applicazione
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        developerUnlockerView = new DeveloperUnlockerViewImp(this);

        MyApplication.getInfoComponent().inject(this);
    }

    /**
     * Metodo che si occupa di far accedere l'utente, in possesso di un codice sviluppatore valido,
     * alla sezione sviluppatore dell'applicazione.
     * @param code codice immesso dall'utente.
     * @return true se il codice immesso è corretto.
     */
    public boolean unlockDeveloper(String code){
        if(userSetting.unlockDeveloper(code)){
            Intent intent = new Intent(this, MainDeveloperActivity.class);
            startActivity(intent);
            return true;
        }
        else{
            developerUnlockerView.showWrongCode();
            return false;
        }
    }
}
