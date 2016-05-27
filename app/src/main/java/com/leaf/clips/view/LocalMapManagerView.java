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
 *Interfaccia che espone i metodi per aggiornare la UI contenente le mappe salvate nel database locale.
 */
public interface LocalMapManagerView {
    /**
     * Metodo che aggiorna la lista delle mappe salvate nel database locale
     * @return  void
     */
    void refreshMaps();

    // TODO: 5/26/16 Tracy + Asta 
    /**
     * Metodo utilizzato per visualizzare la lista delle mappe salvate nel database locale
     * @param adp Collegamento tra la lista delle mappe salvate nel database locale e la view in cui esse devono essere mostrate
     * @return  void
     */
    void setAdapter(Collection<BuildingTable> buildingTables, boolean [] mapVersionStatus);
}
