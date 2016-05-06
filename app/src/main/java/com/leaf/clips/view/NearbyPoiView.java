package com.leaf.clips.view;

import android.widget.Adapter;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

public interface NearbyPoiView {
    /**
     * Metodo utilizzato per visualizzare tutti i POI nelle circostanze dell'utente
     * @param adp Collegamento tra la lista dei POI circostanti l'utente e la view in cui essi devono essere mostrati
     * @return  void
     */
    void setAdapter(Adapter adp);
}
