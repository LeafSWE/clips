package com.leaf.clips.presenter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.leaf.clips.R;
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

    // TODO: 5/24/16 Aggiungere Asta + Tracy
    @Inject
    DatabaseService databaseService;
    /**
     * View associata a tale Activity
     */
    private LocalMapManagerView view;

    // TODO: 5/26/16 Modificare nome attributo, Asta + Tracy
    /**
     * Metodo che permettere di rimuovere una mappa del database locale
     * @param mapPosition Posizione occupata dalla mappa da rimuovere
     * @return  void
     */
    public void deleteMap(int major){
        databaseService.deleteBuilding(databaseService.findBuildingByMajor(major));

        LoadMaps();
    }

    /**
     * Metodo che inizializza la View associata a tale Activity
     * @param bundle Componente per salvare lo stato dell'applicazione
     * @return  void
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);
        view = new LocalMapManagerViewImp(this);

        LoadMaps();
    }

    // TODO: 5/26/16 Modificare nome attributo, Asta + Tracy
    /**
     * Metodo che permette di aggiornare una mappa del database locale
     * @param mapPosition Posizione della mappa da aggiornare
     * @return  void
     */
    public void updateMap(int major){
        try {
            databaseService.updateBuildingMap(major);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoadMaps();
    }

    // TODO: 5/27/16 Aggiungere asta + tracy
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

}
