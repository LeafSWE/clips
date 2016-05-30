package com.leaf.clips.view;

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
     * Metodo utilizzato per visualizzare la lista delle mappe salvate nel database locale
     * @param buildingTables Collegamento tra la lista delle mappe salvate nel database locale e la view in cui esse devono essere mostrate
     * @param mapVersionStatus Array contenente lo stato di ogni mappa installata. Se vero allora la mappa è da aggiornare, se falso non lo è
     * @return  void
     */
    void setAdapter(Collection<BuildingTable> buildingTables, boolean [] mapVersionStatus);
}
