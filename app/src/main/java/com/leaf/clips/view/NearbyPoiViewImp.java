package com.leaf.clips.view;

import android.widget.Adapter;
import android.widget.ListView;

import com.leaf.clips.presenter.NearbyPoiActivity;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

// TODO: 5/3/16 Codify 

public class NearbyPoiViewImp implements NearbyPoiView{
    /**
     * View che mostra la lista di POI nelle vicinanze dell'utente 
     */
    private ListView listPois;

    /**
     * Presenter della View 
     */
    private NearbyPoiActivity presenter;

    /**
     * Costruttore della classe NearbyPoiViewImp
     * @param presenter Presenter della View che viene creata
     */
    public NearbyPoiViewImp(NearbyPoiActivity presenter){
        this.presenter = presenter;
    }

    /**
     * Metodo utilizzato per visualizzare tutti i POI nelle circostanze dell'utente
     * @param adp Collegamento tra la lista dei POI circostanti l'utente e la view in cui essi devono essere mostrati
     * @return  void
     */
    @Override
    public void setAdapter(Adapter adp){
        // TODO: 5/3/16
    }

}
