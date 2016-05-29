package com.leaf.clips.view;

import android.widget.Adapter;
import com.leaf.clips.R;
import com.leaf.clips.presenter.MapDownloaderActivity;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

/**
 *Interfaccia che espone i metodi per aggiornare la UI che mostra il progresso durante il download della mappa di un edificio dal server
 */

public class MapDownloaderViewImp implements MapDownloaderView {

    /**
     * Riferimento al presenter che gestisce la view
     */
    private MapDownloaderActivity presenter;
    
    public MapDownloaderViewImp(MapDownloaderActivity presenter){
        this.presenter = presenter;

        presenter.setContentView(R.layout.activity_map_dowloader);
    }
    
    /**
     * Metodo utilizzato per visualizzare la mappa che si sta scaricando
     * @param adp Collegamento tra la mappa che si sta scaricando e la view in cui essa deve essere mostrata
     * @return  void
     */
    @Override
    public void setDowloadingMap(Adapter adp){}

    /**
     * Metodo utilizzato per visualizzare il progresso nel download di una mappa
     * @param prg Attuale progresso del download
     * @return  void
     */
    @Override
    public void setProgressDownload(int prg){}
}
