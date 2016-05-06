package com.leaf.clips.presenter;

/**
 * @author Marco Zanella
 * @version 0.02
 * @since 0.01
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.view.PoiCategoryView;

public class PoiCategoryActivity extends AppCompatActivity {

    /**
     * Lista di POI associati ad una certa categoria 
     */
    //private List<PointOfInterest> poiList;

    /**
     * View associata a tale Activity 
     */
    private PoiCategoryView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    /**
     * Metodo che permette di avviare la navigazione tramite l'oggetto navigator
     * @param selectedPoi POI da raggiungere selezionato tramite la View
     */
    public void startNavigation(int selectedPoi){
        // TODO: 5/3/16  
    }

}
