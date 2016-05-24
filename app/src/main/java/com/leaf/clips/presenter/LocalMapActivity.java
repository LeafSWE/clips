package com.leaf.clips.presenter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.leaf.clips.R;

/**
 *Classe che estende AppCompactActivity e per la gestione dell'interazione tra LocalMapView ed il model
 */

public class LocalMapActivity extends AppCompatActivity {

    /**
     * View associata a tale Activity
     */

    // TODO: 5/24/16 Aggiungere view
    //private LocalMapManagerView view;

    /**
     * Metodo che permettere di rimuovere una mappa del database locale
     * @param mapPosition Posizione occupata dalla mappa da rimuovere
     * @return  void
     */
    public void deleteMap(int mapPosition){}

    /**
     * Metodo che inizializza la View associata a tale Activity
     * @param bundle Componente per salvare lo stato dell'applicazione
     * @return  void
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_local_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     * Metodo che permette di aggiornare una mappa del database locale
     * @param mapPosition Posizione della mappa da aggiornare
     * @return  void
     */
    public void updateMap(int mapPosition){}

}
