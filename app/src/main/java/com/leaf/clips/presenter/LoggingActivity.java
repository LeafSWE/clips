package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.R;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.view.LoggingView;
import com.leaf.clips.view.LoggingViewImp;

import java.util.ArrayList;

import javax.inject.Inject;

public class LoggingActivity extends AppCompatActivity {

    /**
     * View associata a tale Activity 
     */
    private LoggingView view;

    @Inject
    InformationManager informationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new LoggingViewImp(this);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        informationManager.startRecordingBeacons();
    }

    /**
     * Metodo che viene utilizzato per interrompere l'attività di log
     * @return  void
     */
    public void stopLogging(){
        // TODO: 10/05/2016 codify 
    }

}
