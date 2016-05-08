package com.leaf.clips.presenter;

/**
 * @author Marco Zanella
 * @version 0.02
 * @since 0.01
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestInformation;
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

        //for debugging TODO inizializzazione della lista di POI
        //******************************************************************************************
        poiList = new LinkedList<>();
        for(int i = 1; i <= 10 ; i++) {
            if (i%2 == 0){
                poiList.add(new PointOfInterestImp(i,new PointOfInterestInformation("poi"+i,
                        "description", "category%2")));
            } else if (i%3 == 0){
                poiList.add(new PointOfInterestImp(i,new PointOfInterestInformation("poi"+i,
                        "description", "category%3")));
            } else {
                poiList.add(new PointOfInterestImp(i,new PointOfInterestInformation("poi"+i,
                        "description", "category%anothe")));
            }
        }
        //******************************************************************************************

        // viene creata una matrice che contiene nomi dei POI e categoria di appartenenza
        String [][] s = new String [poiList.size()][2];
        for(int i = 0; i < poiList.size(); i++){
            s[i][0]=poiList.get(i).getName();
            s[i][1]=poiList.get(i).getCategory();
        }

        view.setPoiListAdapter(s);
    }
    
    /**
     * Metodo che permette di avviare la navigazione tramite l'oggetto navigator
     * @param selectedPoi POI da raggiungere selezionato tramite la View
     */
    public void startNavigation(int selectedPoi){
        // for debugging TODO far partire la navigazione
        //******************************************************************************************
        System.out.println("%%%%" + poiList.get(selectedPoi).getId()+
                poiList.get(selectedPoi).getCategory() + poiList.get(selectedPoi).getName());
        //******************************************************************************************
    }

}
