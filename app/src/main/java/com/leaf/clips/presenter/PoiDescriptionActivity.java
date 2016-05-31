package com.leaf.clips.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NoBeaconSeenException;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.view.DescriptionView;
import com.leaf.clips.view.DescriptionViewImp;

import java.util.Collection;
import java.util.Iterator;

import javax.inject.Inject;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
//TODO: aggiungere intera classe ad astah
public class PoiDescriptionActivity extends AppCompatActivity {
    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal Model.
     */
    @Inject
    InformationManager informationManager;

    /**
     * Riferimento alla relativa View.
     */
    private DescriptionView view;

    /**
     * Chiamato quando si sta avviando l'activity. Questo metodo si occupa di inizializzare i
     * campi dati.
     * @param savedInstanceState se l'Actvity viene re-inizializzata dopo essere stata chiusa, allora
     *                           questo Bundle contiene i dati più recenti forniti al metodo
     *                           <a href="http://tinyurl.com/acaw22p">onSavedInstanceState(Bundle)</a>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInfoComponent().inject(this);
        int poiId = getIntent().getIntExtra("poi_id", -1);
        if (poiId!=-1){
            boolean found = false;
            try {
                Collection<PointOfInterest> pois = informationManager.getBuildingMap().getAllPOIs();
                Iterator<PointOfInterest> i = pois.iterator();
                PointOfInterest poi = null;
                while (!found && i.hasNext()){
                    poi = i.next();
                    if (poi.getId() == poiId)
                        found = true;
                }
                if (found) {
                    view = new DescriptionViewImp(this);
                    setTitle(poi.getName());

                    String floorString;
                    int floor = poi.getAllBelongingROIs().iterator().next().getFloor();
                    if (floor == 0)
                        floorString = "Piano terra";
                    else {
                        floorString = floor + "° piano";
                    }
                    view.setGround(floorString);

                    view.setCategory(poi.getCategory());

                    view.setDescription(poi.getDescription());
                }
            } catch (NoBeaconSeenException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
}
