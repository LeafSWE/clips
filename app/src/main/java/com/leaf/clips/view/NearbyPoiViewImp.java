package com.leaf.clips.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.NearbyPoiActivity;

import java.util.ArrayList;

/**
 * @author Andrea Tombolato
 * @version 0.05
 * @since 0.00
 */

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
    public NearbyPoiViewImp(final NearbyPoiActivity presenter){
        presenter.setContentView(R.layout.activity_nearby_poi);
        this.presenter = presenter;

        listPois = (ListView)presenter.findViewById(R.id.nearby_poi_list);
        //Listener
        listPois.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.showDescription(position);
            }
        });
    }

    /**
     * Metodo utilizzato per visualizzare tutti i POI nelle circostanze dell'utente
     * @param toDisplay Array contenente i nomi dei PointOfInterest circostanti all'utente
     */
    @Override
    public void setAdapter(ArrayList<String> toDisplay){
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(presenter,
        android.R.layout.simple_list_item_1, toDisplay);
        listPois.setAdapter(adapter);
    }
}
