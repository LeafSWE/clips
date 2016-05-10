package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.view.LoggingView;

import java.util.ArrayList;

public class LoggingActivity extends AppCompatActivity {

    /**
     * View associata a tale Activity 
     */
    private LoggingView view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Metodo che viene utilizzato per interrompere l'attività di log
     * @return  void
     */
    public void stopLogging(){
        // TODO: 10/05/2016 codify 
    }

}
