package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.leaf.clips.R;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.beacon.LoggerImp;
import com.leaf.clips.view.MainDeveloperView;
import com.leaf.clips.view.MainDeveloperViewImp;

import junit.framework.Assert;

import java.io.File;

import javax.inject.Inject;

/**
 * È una classe che estende AppCompactActivity e consente di gestire l'interazione tra MainDeveloperView ed il model.
 * È usata per recuperare i log dal model e avviare la registrazione di un nuovo log. Gestisce tutte le possibile richieste effettuate da MainDeveloperView.
 */
public class MainDeveloperActivity extends AppCompatActivity {

    /**
     * View associata a tale Activity
     */
    private MainDeveloperView mainDeveloperView;

    /**
     * Oggetto del Model per la gestione delle informazioni
     */
   @Inject
    InformationManager infoManager;

    /**
     * Metodo che viene invocato alla creazione dell'oggetto
     * @param savedInstanceState stato memorizzato dell'oggetto
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainDeveloperView = new MainDeveloperViewImp(this);

        String path = LoggerImp.getPath();

        File directory = new File(path);

        // get all the files from the log directory
        File[] fList = directory.listFiles();

        if (fList != null) {
            String [] stringLogs = new String[fList.length];
            int i = 0;
            for (File file : fList) {
                if (file.isFile())
                    stringLogs[i] = file.toString();
                i++;
            }

            mainDeveloperView.setLogsAdapter(stringLogs);
        }

        else
            mainDeveloperView.setLogsAdapter(null);

        MyApplication.getInfoComponent().inject(this);

        //Controllo che l'iniziezione sia andata abuon fine
        Assert.assertNotNull(infoManager);
    }

    /**
     * Metodo che permette di visualizzare il contenuto di un log
     * @param logPosition  Intero rappresentante la posizione del log selezionato all'interno della lista
     */
    public void showDetailedLog(int logPosition){
        Intent intent = new Intent(this, LogInformationActivity.class);
        intent.putExtra("logNumber", logPosition);
        startActivity(intent);
    }

    /**
     * Metodo che avvia un nuovo log
     */
    public void startNewLog(){
        Intent intent = new Intent(this, LoggingActivity.class);
        startActivity(intent);
    }

    /**
     * Metodo utilizzato per modificare il comportamento di default del tasto back
     */
    @Override
    public void onBackPressed() {
        Intent intent = getParentActivityIntent();
        startActivity(intent);
    }

    /**
     * Metodo che viene invocato alla creazione delle opzioni del menu
     * @param menu Il menu che viene creato
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_developer, menu);
        return true;
    }

    /**
     * Metodo che viene invocato alla selezione di un item del menu
     * @param item Item selezione
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_beacon_power_area) {
            Intent intent = new Intent(this, BeaconPowerAreaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
