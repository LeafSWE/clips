package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leaf.clips.R;
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
        super.onCreate(savedInstanceState);
        view = new NearbyPoiViewImp(this);
    }
}
