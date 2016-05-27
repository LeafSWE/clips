package com.leaf.clips.view;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.presenter.LocalMapActivity;
import com.leaf.clips.presenter.LocalMapAdapter;

import java.util.Collection;

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
    public LocalMapManagerViewImp(final LocalMapActivity presenter){
        this.presenter = presenter;
        this.presenter.setContentView(R.layout.activity_local_map);

        Button btnAddNewMap = (Button) presenter.findViewById(R.id.fab_add_new_map);
        btnAddNewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.DownloadNewMap();
            }
        });
    }

    // TODO: 5/27/16 Valutare se rimuovere questo metodo + Asta + Tracy

    /**
     * Metodo che aggiorna la lista delle mappe salvate nel database locale
     * @return  void
     */
    @Override
    public void refreshMaps(){

    }

    // TODO: 5/26/16 Modificare asta + tracy
    /**
     * Metodo utilizzato per visualizzare la lista delle mappe salvate nel database locale
     * @param adp Collegamento tra la lista delle mappe salvate nel database locale e la view in cui esse devono essere mostrate
     * @return  void
     */
    @Override
    public void setAdapter(Collection<BuildingTable> collectionBuildingTable, boolean [] mapsVersionStatus){
        ListView listView = (ListView) presenter.findViewById(R.id.listViewLocalMaps);
        listView.setAdapter(new LocalMapAdapter(presenter,collectionBuildingTable, mapsVersionStatus));
    }
}
