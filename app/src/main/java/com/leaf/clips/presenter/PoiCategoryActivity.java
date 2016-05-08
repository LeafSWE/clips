package com.leaf.clips.presenter;

/**
 * @author Marco Zanella
 * @version 0.02
 * @since 0.01
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.view.PoiCategoryView;
import com.leaf.clips.view.PoiCategoryViewImp;

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
         */
    }
    
    /**
     * Metodo che permette di avviare la navigazione tramite l'oggetto navigator
     * @param selectedPoi POI da raggiungere selezionato tramite la View
     */
    public void startNavigation(int selectedPoi){

    }

}
