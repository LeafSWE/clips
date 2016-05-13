package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.beacon.LoggerImp;
import com.leaf.clips.view.MainDeveloperView;
import com.leaf.clips.view.MainDeveloperViewImp;

import junit.framework.Assert;

import java.io.File;

import javax.inject.Inject;

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


    @Override
    public void onBackPressed() {
        Intent intent = getParentActivityIntent();
        startActivity(intent);
    }

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

        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        //Controllo che l'iniziezione sia andata abuon fine
        Assert.assertNotNull(infoManager);
    }


    public void showDetailedLog(int logPosition){
        Intent intent = new Intent(this, LogInformationActivity.class);
        intent.putExtra("logNumber", logPosition);
        startActivity(intent);
    }

    public void startNewLog(){
        Intent intent = new Intent(this, LoggingActivity.class);
        startActivity(intent);
    }

}
