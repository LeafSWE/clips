package com.leaf.clips.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.leaf.clips.view.NearbyPoiView;
import com.leaf.clips.view.NearbyPoiViewImp;

import java.util.ArrayList;

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

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        view.setAdapter(adapter);
    }
}
