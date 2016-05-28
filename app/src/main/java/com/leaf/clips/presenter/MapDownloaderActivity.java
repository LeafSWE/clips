package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leaf.clips.R;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.view.MapDownloaderView;
import com.leaf.clips.view.MapDownloaderViewImp;

import javax.inject.Inject;

/**
 *È una classe che estende AppCompactActivity che consente di gestire il download o l'aggiornamento delle mappe
 */

public class MapDownloaderActivity extends AppCompatActivity {

    // TODO: 5/28/16 Aggiungere attributo Asta + Tracy
    @Inject
    DatabaseService databaseService;
    /**
     * View associata a tale Activity
     */
    private MapDownloaderView view;

    /**
     * Metodo che inizializza la View associata
     * @param bundle Componente per salvare lo stato dell'applicazione
     * @return  void
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        view = new MapDownloaderViewImp(this);
    }


    /**
     * Metodo che gestisce la View per visualizzare il completamento del download di una mappa
     * @return  void
     */
    public void downloadFinished(){}
    
}
