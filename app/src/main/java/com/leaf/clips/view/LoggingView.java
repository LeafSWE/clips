package com.leaf.clips.view;

import android.widget.Adapter;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public interface LoggingView {
    /**
     * Metodo utilizzato per visualizzare la lista dei beacon rilevati
     * @param beacons Collegamento tra la lista dei beacon rilevati e la view in cui essi devono essere mostrati
     * @return  void
     */
    void setBeaconListAdapter(StringBuffer beacons);
}
