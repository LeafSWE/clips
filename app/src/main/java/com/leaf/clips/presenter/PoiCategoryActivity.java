package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leaf.clips.R;
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
        setContentView(R.layout.activity_poi_category);
    }
    
    /**
     * Metodo che permette di avviare la navigazione tramite l'oggetto navigator
     * @param selectedPoi POI da raggiungere selezionato tramite la View
     * @return  void
     */
    public void startNavigation(int selectedPoi){
        // TODO: 5/3/16  
    }

}
