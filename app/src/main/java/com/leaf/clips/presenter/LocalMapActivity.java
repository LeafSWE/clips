package com.leaf.clips.presenter;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.view.LocalMapManagerView;
import com.leaf.clips.view.LocalMapManagerViewImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

/**
 *Classe che estende AppCompactActivity e per la gestione dell'interazione tra LocalMapView ed il model
 */

public class LocalMapActivity extends AppCompatActivity {

    /**
     * Riferimento al model per poter accedere alla gestione delle mappe
     */
    @Inject
    DatabaseService databaseService;
    /**
     * View associata a tale Activity
     */
    private LocalMapManagerView view;

    /**
     * Metodo che inizializza la View associata a tale Activity
     * @param bundle Componente per salvare lo stato dell'applicazione
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MyApplication.getInfoComponent().inject(this);
        view = new LocalMapManagerViewImp(this);

        LoadMaps();
    }

    /**
     * Metodo che permettere di rimuovere una mappa del database locale
     * @param major Major dalla mappa da rimuovere
     */
    public void deleteMap(int major){
        databaseService.deleteBuilding(databaseService.findBuildingByMajor(major));
        LoadMaps();
    }

    /**
     * Metodo che permette di aggiornare una mappa del database locale
     * @param major Major della mappa da aggiornare
     */
    public void updateMap(int major){
        try {
            databaseService.updateBuildingMap(major);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoadMaps();
    }

    /**
     * Meotodo utilizzato per leggere le mappe dal database e aggiornare la view
     */
    private void LoadMaps() {
        Collection<BuildingTable> buildingTable = databaseService.findAllBuildings();

        //Se non trovo nessuna mappa passo l'adapter vuoto
        if(buildingTable.size() == 0)
            view.setAdapter(new ArrayList<BuildingTable>(), new boolean [0]);
        else {
            boolean [] mapVersionStatus = new boolean[buildingTable.size()];

            int i = 0;
            for(BuildingTable building : buildingTable) {
                try {
                    mapVersionStatus[i] = databaseService.isBuildingMapUpdated(building.getMajor());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i++;
            }

            view.setAdapter(buildingTable, mapVersionStatus);
        }
    }

    /**
     * Metodo utilizzato per poter avviare l'activity che si occupa della gestione delle mappe remote
     */
    public void DownloadNewMap() {
        Intent intent = new Intent(this,RemoteMapManagerActivity.class);
        startActivity(intent);
    }

    /**
     * Override del metodo onResume per ricaricare le mappe dopo che l'activy è passata in background
     */
    @Override
    protected void onResume(){
        super.onResume();
        LoadMaps();
    }
}