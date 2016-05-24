package com.leaf.clips.view;

import android.widget.Adapter;

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

    /**
     * Metodo utilizzato per visualizzare la lista delle mappe salvate nel database locale
     * @param adp Collegamento tra la lista delle mappe salvate nel database locale e la view in cui esse devono essere mostrate
     * @return  void
     */
    void setAdapter(Adapter adp);
}
