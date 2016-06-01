package com.leaf.clips.presenter;
/**
 * @author Andrea Tombolato
 * @version 0.06
 * @since 0.00
 */

import android.content.Intent;
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

/**
 * Questa Activity si occupa di gestire i POI rilevati nelle vicinanze dell'utente.
 */
//TODO:astah
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

    /**
     *Chiamato quando si sta avviando l'activity. Questo metodo si occupa di inizializzare
     *i campi dati.
     *@param savedInstanceState se l'Actvity viene re-inizializzata dopo essere stata chiusa, allora
     *                           questo Bundle contiene i dati più recenti forniti al metodo
     *                           <a href="http://tinyurl.com/acaw22p">onSavedInstanceState(Bundle)</a>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = new NearbyPoiViewImp(this);
        MyApplication.getInfoComponent().inject(this);

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

    /**
     * Matodo che recupera l'id del POI scelto e lo passa a DescriptionPoiActivity, in modo che essa
     * possa visualizzare la descrizione del Poi scelto.
     * @param selectedPoi POI di cui visualizzare la descrizione
     */
    public void showDescription(int selectedPoi){
        Intent intent = new Intent(this, PoiDescriptionActivity.class);

        if(pois != null){
            int chosenPoiId = pois.get(selectedPoi).getId();
            intent.putExtra("poi_id",chosenPoiId);

            startActivity(intent);
        }
    }
}
