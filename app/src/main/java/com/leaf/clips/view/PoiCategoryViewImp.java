package com.leaf.clips.view;

/**
 * @author Marco Zanella
 * @version 0.02
 * @since 0.01
 */

import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.PoiCategoryActivity;

/**
 *Classe che si occupa di mostrare la lista dei POI relativi ad una certa categoria. La UI legata a questa classe permette all'utente di accedere alle informazioni di un certo POI appartenente alla categoria.
 */
public class PoiCategoryViewImp implements PoiCategoryView {

    /**
     * View che permette di visualizzare la lista delle categorie di POI
     */
    private ListView pois;

    /**
     * Costruttore della classe PoiCategoryViewImp
     * @param presenter Presenter della View che viene creata
     */
    public PoiCategoryViewImp(PoiCategoryActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_poi_category);

        //pois.findViewById(R.id.category_list);
        pois = (ListView)presenter.findViewById(R.id.category_list);
    }

    /**
     * Presenter della View
     */
    private PoiCategoryActivity presenter;

    /**
     * Metodo utilizzato per visualizzare tutti i POI appartenenti ad una certa categoria
     * @param toDisplay Array di stringhe che rappresentano le categorie che devono essere mostrate
     */
    @Override
    public void setPoiListAdapter(final String[][] toDisplay) {

    }

}

