package com.leaf.clips.view;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

import android.widget.Adapter;

/**
 *Interfaccia che espone i metodi per aggiornare la UI che mostra il progresso durante il download della mappa di un edificio dal server
 */

public interface MapDownloaderView {
    /**
     * Metodo utilizzato per visualizzare la mappa che si sta scaricando
     * @param adp Collegamento tra la mappa che si sta scaricando e la view in cui essa deve essere mostrata
     * @return  void
     */
    void setDowloadingMap(Adapter adp);

    // TODO: 5/28/16 Correggere nome metodo errato Asta + Tracy 
    /**
     * Metodo utilizzato per visualizzare il progresso nel download di una mappa
     * @param prg Attuale progresso del download
     * @return  void
     */
    void setProgressDownload(int prg);
}
