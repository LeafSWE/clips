package com.leaf.clips.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.beacon.LoggerImp;
import com.leaf.clips.view.LogInformationView;
import com.leaf.clips.view.LogInformationViewImp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.inject.Inject;


public class LogInformationActivity extends AppCompatActivity {

    /**
     * Oggetto del Model per la gestione dei log
     */
    @Inject
    InformationManager infoManager;

    /**
     * View associata a tale Activity
     */
    private LogInformationView view;


    /**
     *Chiamato quando si sta avviando l'activity. Questo metodo si occupa di inizializzare
     *i campi dati.
     *@param savedInstanceState se l'Actvity viene re-inizializzata dopo essere stata chiusa, allora
     *                           questo Bundle contiene i dati più recenti forniti al metodo
     *                           <a href="http://tinyurl.com/acaw22p">onSavedInstanceState(Bundle)</a>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.view = new LogInformationViewImp(this);
        MyApplication.getInfoComponent().inject(this);

        int logPosition = getIntent().getExtras().getInt("logNumber");

        // get all the files from the log directory
        String path = LoggerImp.getPath();
        File directory = new File(path);

        File[] fList = directory.listFiles();
        String stringLogName = new String();
        int i = 0;
        for (File file : fList) {
            if (file.isFile() && i == logPosition)
                stringLogName = file.getName();
            i++;
        }

        //Get the text file
        File fileLog = new File(path,stringLogName);

        //Read text from file
        StringBuilder stringLog = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileLog));
            String line;

            while ((line = br.readLine()) != null) {
                stringLog.append(line);
                stringLog.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File read failed: " + e.toString());
        }

        view.setBeaconAdapter(stringLog.toString());

    }

    /**
     * Metodo che viene utilizzato per rimuovere un log salvato
     * @param name Nome del log da eliminare
     */
    public void deleteLog(String name){
        int logPosition = getIntent().getExtras().getInt("logNumber");

        // get all the files from the log directory
        String path = LoggerImp.getPath();
        File directory = new File(path);

        File[] fList = directory.listFiles();
        String stringLogName = new String();
        int i = 0;
        for (File file : fList) {
            if (file.isFile() && i == logPosition)
                stringLogName = file.getName();
            i++;
        }

        infoManager.removeBeaconInformationFile(stringLogName);

        Intent intent = new Intent(this, MainDeveloperActivity.class);
        startActivity(intent);
        finish();

    }

}
