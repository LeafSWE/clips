package com.leaf.clips.presenter;
/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.view.NearbyPoiView;
import com.leaf.clips.view.NearbyPoiViewImp;

public class NearbyPoiActivity extends AppCompatActivity {

    /**
     * Insieme di POI rilevati nelle circostanze
     */
    //private Collection<PointOfInterest> pois;

    /**
     * View associata a tale Activity
     */
    private NearbyPoiView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: creare la View e spostare relativa logica

        super.onCreate(savedInstanceState);
        view = new NearbyPoiViewImp(this);

        //ADAPTER just for debug TODO: remove on integration
        String[] values = new String[] {"Aula 1C150", "Aula 1BC45", "Toilette donne 1CB"};

        view.setAdapter(values);
    }
}
