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
import com.leaf.clips.view.MainDeveloperView;
import com.leaf.clips.view.MainDeveloperViewImp;

import junit.framework.Assert;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainDeveloperView = new MainDeveloperViewImp(this);

        // TODO: 10/05/2016 Recuperare logs
        //Setto i log nell'app
        String [] stringLogs = new String [] {"230514", "230516", "230514", "230516", "230514", "230516"};
        mainDeveloperView.setLogsAdapter(stringLogs);

        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        //Controllo che l'iniziezione sia andata abuon fine
        Assert.assertNotNull(infoManager);
    }


    public void showDetailedLog(int logPosition){
        // TODO: 5/6/16 Passare la posizione del log nell'intent
        Intent intent = new Intent(this, LogInformationActivity.class);
        startActivity(intent);
    }

    public void startNewLog(){
        Intent intent = new Intent(this, LoggingActivity.class);
        startActivity(intent);
    }

}
