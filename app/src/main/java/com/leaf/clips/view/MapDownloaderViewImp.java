package com.leaf.clips.view;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

import android.widget.Adapter;

import com.leaf.clips.R;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.presenter.MapDownloaderActivity;

import java.util.Map;

import javax.inject.Inject;

/**
 *Interfaccia che espone i metodi per aggiornare la UI che mostra il progresso durante il download della mappa di un edificio dal server
 */

public class MapDownloaderViewImp implements MapDownloaderView {

    // TODO: 5/28/16 Aggiungere campi + Costruttore su Asta + Tracy
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

    // TODO: 5/28/16 Cambiato nome metodo, correggere Asta + Tracy
    /**
     * Metodo utilizzato per visualizzare il progresso nel download di una mappa
     * @param prg Attuale progresso del download
     * @return  void
     */
    @Override
    public void setProgressDownload(int prg){}
}
