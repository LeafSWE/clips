package com.leaf.clips.view;

import android.widget.Adapter;

import com.leaf.clips.model.dataaccess.dao.BuildingTable;

import java.util.Collection;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */


/**
 *Interfaccia che espone i metodi per aggiornare la UI contenente le mappe delle quali è possibile effettuare il download dal server
 */

public interface RemoteMapManagerView {

    // TODO: 5/27/16 Modificare attributo asta + tracy 
    /**
     * Metodo utilizzate per visualizzare le mappe che è possibile scaricare da un server remoto
     * @param adp Collegamento tra la lista delle mappe che è possibile scaricare e la view in cui esse devono essere mostrate
     * @return  void
     */
    void setRemoteMaps(Collection<BuildingTable> buildingTables);
}
