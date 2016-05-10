package com.leaf.clips.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.LogInformationActivity;
import com.leaf.clips.presenter.LoggingActivity;
import com.leaf.clips.presenter.MainDeveloperActivity;

import java.util.ArrayList;

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
    private final ListView logList;

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
    public MainDeveloperViewImp(final MainDeveloperActivity presenter){

        //Assegno il presenter alla view
        this.presenter = presenter;

        //Carico il layout XML
        presenter.setContentView(R.layout.activity_main_developer);

        //Setto la toolbar
        Toolbar toolbar = (Toolbar) presenter.findViewById(R.id.toolbar);
        presenter.setSupportActionBar(toolbar);

        //ADAPTER
        logList = (ListView) presenter.findViewById(R.id.saved_log_list);


        /**
         * Imposta il Listener sui click effettuati sugli item della ListView. (I log)
         */
        logList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                presenter.showDetailedLog(position);
            }

        });

        /**
         * Controlla le azioni sul fab, e carica l'activity di un nuovo log
         */

        logStartBtn = (FloatingActionButton) presenter.findViewById(R.id.start_log_button);
        logStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startNewLog();
            }
        });


        presenter.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    /**
     * Metodo utilizzato per visualizzare la lista di tutti i log salvati in locale
     * @param stringLogs Collegamento tra la lista dei log e la view in cui essi devono essere mostrati
     * @return  void
     */
    @Override
    public void setLogsAdapter(String [] stringLogs){

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < stringLogs.length; ++i) {
            list.add(stringLogs[i]);
        }

        final ArrayAdapter adapter = new ArrayAdapter(presenter, android.R.layout.simple_list_item_1, list);
        logList.setAdapter(adapter);
    }
}
