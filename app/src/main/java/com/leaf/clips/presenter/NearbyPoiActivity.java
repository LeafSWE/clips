package com.leaf.clips.presenter;
/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NoBeaconSeenException;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.view.NearbyPoiView;
import com.leaf.clips.view.NearbyPoiViewImp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NearbyPoiActivity extends AppCompatActivity {

    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal model.
     */
    @Inject
    InformationManager informationManager;

    /**
     * Insieme di POI rilevati nelle vicinanze dell'utente.
     */
    private List<PointOfInterest> pois;

    /**
     * View associata a tale Activity
     */
    private NearbyPoiView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = new NearbyPoiViewImp(this);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        try {
            pois = (List<PointOfInterest>) informationManager.getNearbyPOIs();

            ArrayList<String> nearbyPoiNames = new ArrayList<>();
            for (PointOfInterest poi : pois) {
                String poiName = poi.getName();
                nearbyPoiNames.add(poiName);
            }
            view.setAdapter(nearbyPoiNames);
        } catch (NoBeaconSeenException e) {
            e.printStackTrace();
        }
    }
}
