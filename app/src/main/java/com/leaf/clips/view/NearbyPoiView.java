package com.leaf.clips.view;

import java.util.ArrayList;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

public interface NearbyPoiView {
    /**
     * Metodo utilizzato per visualizzare tutti i POI nelle circostanze dell'utente
     * @param toDisplay Array contenente i nomi dei PointOfInterest circostanti l'utente
     */
    void setAdapter(ArrayList<String> toDisplay);
}
