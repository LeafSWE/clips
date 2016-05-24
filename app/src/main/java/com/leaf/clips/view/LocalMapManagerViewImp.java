package com.leaf.clips.view;

import android.widget.Adapter;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.LocalMapActivity;
import com.leaf.clips.presenter.LocalMapAdapter;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

/**
 *Classe che si occupa di mostrare le mappe degli edifici salvate nel database locale. La UI legata a questa classe permette all'utente di accedere alle funzionalità di aggiornamento e rimozione di una certa mappa
 */
public class LocalMapManagerViewImp implements LocalMapManagerView {

    /**
     * Presenter della View
     */
    private LocalMapActivity presenter;

    /**
     * Costruttore della classe LocalMapManagerViewImp
     * @param presenter Presenter della View che viene creata
     */
    public LocalMapManagerViewImp(LocalMapActivity presenter){
        this.presenter = presenter;
        this.presenter.setContentView(R.layout.activity_local_map);
    }

    /**
     * Metodo che aggiorna la lista delle mappe salvate nel database locale
     * @return  void
     */
    @Override
    public void refreshMaps(){}

    /**
     * Metodo utilizzato per visualizzare la lista delle mappe salvate nel database locale
     * @param adp Collegamento tra la lista delle mappe salvate nel database locale e la view in cui esse devono essere mostrate
     * @return  void
     */
    @Override
    public void setAdapter(Adapter adp){
        ListView listView = (ListView) presenter.findViewById(R.id.listViewLocalMaps);
        listView.setAdapter((LocalMapAdapter)adp);
    }
}
