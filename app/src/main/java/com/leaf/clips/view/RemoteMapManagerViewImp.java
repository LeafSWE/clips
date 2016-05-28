package com.leaf.clips.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Adapter;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.presenter.RemoteMapManagerActivity;
import com.leaf.clips.presenter.RemoteMapManagerAdapter;

import java.util.Collection;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

/**
 *Classe che si occupa di mostrare le mappe degli edifici disponibili al download. La UI legata a questa classe permette all'utente di accedere alle funzionalità di download di una certa mappa
 */

public class RemoteMapManagerViewImp implements RemoteMapManagerView{
    /**
     * Presenter della View
     */
    private RemoteMapManagerActivity presenter;

    /**
     * Costruttore della classe RemoteMapManagerViewImp
     * @param presenter Presenter della View che viene creata
     */
    public RemoteMapManagerViewImp(RemoteMapManagerActivity presenter){
        this.presenter = presenter;
        this.presenter.setContentView(R.layout.activity_remote_map_manager);
    }

    // TODO: 5/27/16 Asta + Tracy, attributo modificato
    /**
     * Metodo utilizzate per visualizzare le mappe che è possibile scaricare da un server remoto
     * @param adp Collegamento tra la lista delle mappe che è possibile scaricare e la view in cui esse devono essere mostrate
     * @return  void
     */
    @Override
    public void setRemoteMaps(Collection<BuildingTable> buildingTables){
        ListView listView = (ListView) presenter.findViewById(R.id.listViewRemoteMaps);
        listView.setAdapter(new RemoteMapManagerAdapter(presenter,buildingTables));
    }

    // TODO: 5/28/16 Aggiungere Asta + Tracy 
    public void showMapAlreadyPresent () {
        AlertDialog.Builder builder = new AlertDialog.Builder(presenter);
        builder.setMessage(R.string.remote_map_already_downloaded)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
