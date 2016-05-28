package com.leaf.clips.presenter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.model.InformationListener;
import com.leaf.clips.model.Listener;
import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.view.MapDownloaderView;
import com.leaf.clips.view.MapDownloaderViewImp;

import junit.framework.Assert;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

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

        final int major = getIntent().getExtras().getInt("mapMajor");


        AsyncDownload asyncDownload = new AsyncDownload();
        asyncDownload.execute(major);
        try {
            asyncDownload.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    /**
     * Metodo che gestisce la View per visualizzare il completamento del download di una mappa
     * @return  void
     */
    public void downloadFinished(){}



    /**
     * Classe che estende AsyncTask per il download di una mappa
     */
    private class AsyncDownload extends AsyncTask<Integer, Void, Boolean> {

        /**
         * Variabile che indica se si sono verificati errori di connessione
         */
        private Boolean remoteError = false;

        /**
         * Metodo svolto in background per il download della mappa
         * @param params identificativo della mappa
         * @return Boolean
         */
        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                Integer major = params[0];
                databaseService.findRemoteBuildingByMajor(params[0]);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                remoteError = true;
            }
            return false;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            TextView txt = (TextView) findViewById(R.id.textViewMapDownloadProgress);
            txt.setText("Mappa Scaricata");
        }

        @Override
        protected void onPreExecute() {
            TextView txt = (TextView) findViewById(R.id.textViewMapDownloadProgress);
            txt.setText("Inizio il dowload");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            TextView txt = (TextView) findViewById(R.id.textViewMapDownloadProgress);
            txt.setText("Scaricando");
        }
    }
    
}
