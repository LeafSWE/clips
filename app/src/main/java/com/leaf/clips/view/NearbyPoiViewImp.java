package com.leaf.clips.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.leaf.clips.R;
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
    public NearbyPoiViewImp(final NearbyPoiActivity presenter){
        presenter.setContentView(R.layout.activity_nearby_poi);
        this.presenter = presenter;

        listPois = (ListView)presenter.findViewById(R.id.nearby_poi_list);
        //Listener
        listPois.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(presenter, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metodo utilizzato per visualizzare tutti i POI nelle circostanze dell'utente
     * @param adp Collegamento tra la lista dei POI circostanti l'utente e la view in cui essi devono essere mostrati
     * @return  void
     */
    @Override
    public void setAdapter(ListAdapter adp){
        listPois.setAdapter(adp);
    }

}
