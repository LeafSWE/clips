package com.leaf.clips.view;

import android.support.design.widget.FloatingActionButton;
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

        FloatingActionButton btnAddNewMap = (FloatingActionButton) presenter.findViewById(R.id.fab_add_new_map);
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
    public void refreshMaps(){}
    
    /**
     * Metodo utilizzato per visualizzare la lista delle mappe salvate nel database locale
     * @param buildingTables Collegamento tra la lista delle mappe salvate nel database locale e la view in cui esse devono essere mostrate
     * @param mapVersionStatus Array contenente lo stato di ogni mappa installata. Se vero allora la mappa è da aggiornare, se falso non lo è
     * @return  void
     */
    @Override
    public void setAdapter(Collection<BuildingTable> buildingTables, boolean [] mapVersionStatus){
        ListView listView = (ListView) presenter.findViewById(R.id.listViewLocalMaps);
        listView.setAdapter(new LocalMapAdapter(presenter,buildingTables, mapVersionStatus));
    }
}
