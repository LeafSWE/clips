package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leaf.clips.R;
import com.leaf.clips.view.MapDownloaderView;

public class MapDownloaderActivity extends AppCompatActivity {


    /**
     * View associata a tale Activity
     */
    private MapDownloaderView view;

    /**
     * Metodo che gestisce la View per visualizzare il completamento del download di una mappa
     * @return  void
     */
    public void downloadFinished(){}

    /**
     * Metodo che inizializza la View associata
     * @param bundle Componente per salvare lo stato dell'applicazione
     * @return  void
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map_dowloader);
    }

}
