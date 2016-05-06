package com.leaf.clips.view;

import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

import com.leaf.clips.presenter.LogInformationActivity;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public class LogInformationViewImp implements LogInformationView {
    /**
     * Bottone per rimuovere un log salvato 
     */
    private FloatingActionButton btnDeleteLog;

    /**
     * Presenter della View 
     */
    private LogInformationActivity presenter;

    /**
     * TextView all'interno della quale viene visualizzato il contenuto del log 
     */
    private TextView txtLog;

    /**
     * Costruttore della classe LogInformationViewImp
     * @param presenter Presenter della View che viene creata
     */
    public LogInformationViewImp(LogInformationActivity presenter){
        this.presenter = presenter;
    }

    /**
     * Metodo utilizzato per visualizzare la lista delle informazioni di un certo beacon
     * @param logInfo Collegamento tra la lista delle informazione dei log e la view in cui esse devono essere mostrate
     * @return  void
     */
    @Override
    public void setBeaconAdapter(String logInfo){
        // TODO: 5/6/16 Codify 
    }
}
