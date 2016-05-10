package com.leaf.clips.view;

import android.widget.Adapter;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public interface MainDeveloperView {
    /**
     * Metodo utilizzato per visualizzare la lista di tutti i log salvati in locale
     * @param stringLogs Collegamento tra la lista dei log e la view in cui essi devono essere mostrati
     * @return  void
     */
    void setLogsAdapter(String [] stringLogs);

}
