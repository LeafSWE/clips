package com.leaf.clips.view;
/**
* @author
* @version 0.00 
* @since 0.00
* 
* 
*/

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.PoiActivity;

import java.util.Collections;
import java.util.List;

/**
    *Classe che si occupa di mostrare la lista dei POI relativi ad una certa categoria. La UI legata a questa classe permette all'utente di accedere alle informazioni di un certo POI appartenente alla categoria.
    */
    public class PoiViewImp implements PoiView {

    /**
    * View che permette di visualizzare la lista delle categorie di POI
    */
    private ListView pois;

    /**
    * Presenter della View
    */
    private PoiActivity presenter;

    /**
    * Costruttore della classe PoiViewImp
    * @param presenter Presenter della View che viene creata
    */
    public PoiViewImp(final PoiActivity presenter){
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_all_poi);

        pois = (ListView)presenter.findViewById(R.id.poi_list);

        //Imposta il Listener sulla lista di categorie
        pois.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.showDescription(position);
            }
        });

        presenter.setTitle(R.string.all_pois);
    }

    /**
     * Metodo utilizzato per visualizzare tutti i POI
     * @param poiList lista di stringhe che rappresentano tutti i POI
     */
    public void setPoiListAdapter(List<String> poiList){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(presenter,android.R.layout.simple_list_item_1,poiList);

        pois.setAdapter(adapter);
    }

}

