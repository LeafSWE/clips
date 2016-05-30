package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.usersetting.Setting;
import javax.inject.Inject;

/**
 * Classe che estende AppCompactActivity e controlla utilizzando il model, se l'utente è sviluppatore o meno.
 * È utilizzata per discriminare la visualizzazione delle funzionalità sviluppatore tra un utente sviluppatore ed un utente che non lo è.
 */
public class MainDeveloperPresenter extends AppCompatActivity {

    /**
     * Riferimento alle preferenze utente
     */
    @Inject
    Setting userSetting;

    /**
     * Metodo che viene invocato alla creazione dell'oggetto
     * @param savedInstanceState satto dell'oggetto memorizzato
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInfoComponent().inject(this);

        if(isDeveloper()){
            startDeveloperOptions();
        }
        else
            startDeveloperUnlocker();
    }

    /**
     * Metodo determina se l'utente è sviluppatore o meno. L'utente è riconosciuto come sviluppatore
     * solo se ha inserito almeno una volta un codice sviluppatore valido nell'apposita sezione.
     * @return true se e solo se l'utente è riconosciuto come sviluppatore.
     */
    public boolean isDeveloper(){
        return userSetting.isDeveloper();
    }

    /**
     * Metodo che avvia l'Activity utile a inserire il codice per accedere alla sezione sviluppatore.
     */
    public void startDeveloperUnlocker(){
        Intent intent = new Intent(this, DeveloperUnlockerActivity.class);
        startActivity(intent);
    }

    /**
     * Metodo che avvia l'Activity principale della sezione sviluppatore.
     */
    public void startDeveloperOptions(){
        Intent intent = new Intent(this, MainDeveloperActivity.class);
        startActivity(intent);
    }
}
