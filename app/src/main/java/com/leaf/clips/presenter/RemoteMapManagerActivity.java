package com.leaf.clips.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.view.RemoteMapManagerView;
import com.leaf.clips.view.RemoteMapManagerViewImp;

import java.io.IOException;

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
     * @return  void
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);
        this.view = new RemoteMapManagerViewImp(this);

        try {
            view.setRemoteMaps(databaseService.findAllRemoteBuildings());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo che permette di eseguire il download di una mappa da un database remoto
     * @param major Major della mappa di cui fare il download
     * @return  void
     */
    public void downloadMap(int major){

        if(databaseService.isBuildingMapPresent(Integer.valueOf(major))){
            view.showMapAlreadyPresent();
        }
        else{
            Intent intent = new Intent(this, MapDownloaderActivity.class);
            intent.putExtra("mapMajor", major);
            startActivity(intent);
        }

    }

}
