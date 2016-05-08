package com.leaf.clips.presenter;

/**
 * @author Marco Zanella
 * @version 0.02
 * @since 0.01
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestImp;
import com.leaf.clips.view.PoiCategoryView;
import com.leaf.clips.view.PoiCategoryViewImp;

import java.util.LinkedList;
import java.util.List;

public class PoiCategoryActivity extends AppCompatActivity {

    /**
     * Lista di POI associati ad una certa categoria 
     */
    private List<PointOfInterest> poiList;

    /**
     * View associata a tale Activity 
     */
    private PoiCategoryView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new PoiCategoryViewImp(this);

        String chosenCategoryName = getIntent().getStringExtra("category_name");

        setTitle(chosenCategoryName);

        /**
         * TODO: recupera lista poi appartenenti alla categoria scelta
         * poiList = informationManager.getPoiInCategory(chosenCategoryName);
         *
         * List<String> poiNamesList = new LinkedList()
         *
         * foreach(PointOfInterest poi : poiList){
         *  String poiName = poi.getName();
         *  poiNameList.add(poiName);
         * }
         *
         *view.setPoiListAdapter(poiNameList);
         */

        //TODO: remove (only for debug purpose) --> use code upon instead
        List<String> list = new LinkedList<>();
        list.add("Aula 1A150");
        list.add("Aula 1AD100");
        list.add("Aula 1C150");
        list.add("Aula 1BC45");
        list.add("Aula 1BC50");
        view.setPoiListAdapter(list);
    }
    
    /**
     * Metodo che permette di avviare la navigazione tramite l'oggetto navigator
     * @param selectedPoi POI da raggiungere selezionato tramite la View
     */
    public void startNavigation(int selectedPoi){
        Intent intent = new Intent(this, NavigationActivity.class);
        /**
         * Recupera l'id del POI scelto e lo passa a NavigationActivity, in modo che essa possa
         * autonomamente recuperare il POI scelto e calcolare il percorso
         */
        //TODO: remove (for debug purpose only)
        poiList = new LinkedList<>();
        PointOfInterest poi = new PointOfInterestImp(666,null);
        poiList.add(poi);
        /****************************/

        if(poiList != null){
            int chosenPoiId = poiList.get(selectedPoi).getId();
            intent.putExtra("poi_id",chosenPoiId);
            startActivity(intent);
        }
    }

}
