package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.05
 * @since 0.01
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NoBeaconSeenException;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.view.PoiView;
import com.leaf.clips.view.PoiViewImp;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * PoiCategoryActivity si occupa di gestire le categorie di POI presenti nell'edificio, in modo che
 * l'utente possa effettuare la ricerca della destinazione per categoria.
 */
public class PoiActivity extends AppCompatActivity {
    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal model
     */
    @Inject
    InformationManager informationManager;

    /**
     * Lista di POI associati ad una certa categoria 
     */
    private List<PointOfInterest> poiList;

    /**
     * View associata a tale Activity 
     */
    private PoiView view;

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
        view = new PoiViewImp(this);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        try {
            poiList = (List<PointOfInterest>) informationManager.getBuildingMap().getAllPOIs();
            List<String> poiNames = new LinkedList<>();
            for (PointOfInterest poi: poiList) {
                String poiName = poi.getName();
                poiNames.add(poiName);
            }
            Collections.sort(poiList, new Comparator<PointOfInterest>() {
                @Override
                public int compare(PointOfInterest lhs, PointOfInterest rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });
            Collections.sort(poiNames);
            view.setPoiListAdapter(poiNames);
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

        if(poiList != null){
            int chosenPoiId = poiList.get(selectedPoi).getId();
            intent.putExtra("poi_id",chosenPoiId);

            startActivity(intent);
        }
    }
}
