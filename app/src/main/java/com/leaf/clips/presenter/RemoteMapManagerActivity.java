package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leaf.clips.R;

/**
 *È una classe che estende AppCompactActivity che recupera tutte le mappe in remoto dal model e gestisce RemoteMapManagerView
 */
public class RemoteMapManagerActivity extends AppCompatActivity {

    /**
     * View associata a tale Activity
     */
    //private RemoteMapManagerView view;

    /**
     * Metodo che inizializza la View associata
     * @param bundle Componente per salvare lo stato dell'applicazione
     * @return  void
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_remote_map_manager);
    }

    /**
     * Metodo che permette di eseguire il download di una mappa da un database remoto
     * @param mapPosition Posizione della mappa di cui fare il download
     * @return  void
     */
    public void downloadMap(int mapPosition){}

}
