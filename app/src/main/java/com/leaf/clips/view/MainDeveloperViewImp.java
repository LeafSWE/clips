package com.leaf.clips.view;

import android.support.design.widget.FloatingActionButton;
import android.widget.Adapter;
import android.widget.ListView;

import com.leaf.clips.presenter.MainDeveloperActivity;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

// TODO: 5/6/16 Codify 

public class MainDeveloperViewImp implements MainDeveloperView {
    /**
     * View che mostra la lista dei log
     */
    private ListView logList;

    /**
     * Bottone che permette di attivare un log
     */
    private FloatingActionButton logStartBtn;

    /**
     * Presenter della View
     */
    private MainDeveloperActivity presenter;

    /**
     * Costruttore della classe MainDeveloperViewImp
     * @param presenter Presenter della View che viene creata
     */
    public MainDeveloperViewImp(MainDeveloperActivity presenter){
        this.presenter = presenter;
    }

    /**
     * Metodo utilizzato per visualizzare la lista di tutti i log salvati in locale
     * @param adp Collegamento tra la lista dei log e la view in cui essi devono essere mostrati
     * @return  void
     */
    @Override
    public void setLogsAdapter(Adapter adp){
        // TODO: 5/6/16
    }
}
