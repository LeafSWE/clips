package com.leaf.clips.view;

/**
 * @author Marco Zanella
 * @version 0.02
 * @since 0.01
 */

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.PoiCategoryActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *Classe che si occupa di mostrare la lista dei POI relativi ad una certa categoria. La UI legata a questa classe permette all'utente di accedere alle informazioni di un certo POI appartenente alla categoria.
 */
public class PoiCategoryViewImp implements PoiCategoryView {

    /**
     * View che permette di visualizzare la lista delle categorie di POI
     */
    private ListView pois;

    /**
     * Presenter della View
     */
    private PoiCategoryActivity presenter;

    /**
     * Costruttore della classe PoiCategoryViewImp
     * @param presenter Presenter della View che viene creata
     */
    public PoiCategoryViewImp(PoiCategoryActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_poi_category);
    }

    /**
     * Metodo utilizzato per visualizzare tutti i POI appartenenti ad una certa categoria
     * @param toDisplay Array di stringhe che rappresentano le categorie che devono essere mostrate
     */
    @Override
    public void setPoiListAdapter(String[] toDisplay) {
        final ArrayList<String> list = new ArrayList<>(Arrays.asList(toDisplay));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(presenter,
                android.R.layout.simple_list_item_1, list);
        pois.setAdapter(adapter);
    }

}

