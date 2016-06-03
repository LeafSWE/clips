package com.leaf.clips.presenter;

import android.app.NotificationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.leaf.clips.R;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.view.RemoteMapManagerView;
import com.leaf.clips.view.RemoteMapManagerViewImp;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

/**
 *È una classe che estende AppCompactActivity che recupera tutte le mappe in remoto dal model e gestisce RemoteMapManagerView
 */
public class RemoteMapManagerActivity extends AppCompatActivity {

    /**
     * View associata a tale Activity
     */
    private RemoteMapManagerView view;

    /**
     * Riferimento al model per poter accedere alla gestione delle mappe
     */
    @Inject
    DatabaseService databaseService;

    /**
     * Metodo che inizializza la View associata
     * @param bundle Componente per salvare lo stato dell'applicazione
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MyApplication.getInfoComponent().inject(this);
        this.view = new RemoteMapManagerViewImp(this);

        AsyncFindRemoteBuilding aSyncFindRemoteBuilding = new AsyncFindRemoteBuilding();
        aSyncFindRemoteBuilding.execute();
        try {
            aSyncFindRemoteBuilding.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che permette di eseguire il download di una mappa da un database remoto
     * @param major Major della mappa di cui fare il download
     */
    public void downloadMap(final int major){

        if(databaseService.isBuildingMapPresent(major)){
            view.showMapAlreadyPresent();
        }
        else{
            final int id = 1;

            final NotificationManager mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setContentTitle(getString(R.string.map_download_title)).setContentText(getString(R.string.map_download)).setSmallIcon(R.drawable.ic_sync_white_24dp);
            // Start a lengthy operation in a background thread
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {

                            // Do the "lengthy" operation 20 times

                                // Sets the progress indicator to a max value, the
                                // current completion percentage, and "determinate"
                                // state
                                mBuilder.setProgress(0, 0, true);
                                // Displays the progress bar for the first time.
                                mNotifyManager.notify(id, mBuilder.build());
                                // Sleeps the thread, simulating an operation
                                // that takes time
                            try {
                                databaseService.findRemoteBuildingByMajor(Integer.valueOf(major));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // When the loop is finished, updates the notification
                            mBuilder.setContentText(getString(R.string.map_downloaded)).setProgress(0,0,false).setSmallIcon(R.drawable.ic_vertical_align_bottom_white_24dp);
                            mNotifyManager.notify(id, mBuilder.build());
                        }
                    }
            // Starts the thread by calling the run() method in its Runnable
            ).start();
        }

    }


    private class AsyncFindRemoteBuilding extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                view.setRemoteMaps(databaseService.findAllRemoteBuildings());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private class AsyncDownloadRemoteBuilding extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                databaseService.findRemoteBuildingByMajor((Integer)params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
