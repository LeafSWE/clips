package com.leaf.clips.view;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
        Toolbar toolbar = (Toolbar) this.presenter.findViewById(R.id.toolbar);
        this.presenter.setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) this.presenter.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        this.presenter.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
