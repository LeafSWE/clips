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
    public MainDeveloperViewImp(final MainDeveloperActivity presenter){
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_main_developer);
        Toolbar toolbar = (Toolbar) presenter.findViewById(R.id.toolbar);
        presenter.setSupportActionBar(toolbar);

        //ADAPTER
        final ListView listview = (ListView) presenter.findViewById(R.id.saved_log_list);
        String[] values = new String[] { "20160501_0830", "20160501_0835", "20160501_0930",
                "20160501_0940", "20160501_0950", "20160501_1010", "20160501_1030", "20160421_0830",
                "20160421_0837", "20160421_0839", "20160421_1830", "20160421_1840", "20160421_1850", "20160421_1859"};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(presenter, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        /**
         * Imposta il Listener sui click effettuati sugli item della ListView.
         */
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                //TODO: dobbiamo passare i dati del log da mostrare nella Activity che stiamo aprendo
                Intent intent = new Intent(presenter,LogInformationActivity.class);
                // TODO: 5/6/16 indirizzare la chiamata al presenter
            }

        });

        FloatingActionButton fab = (FloatingActionButton) presenter.findViewById(R.id.start_log_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(presenter,LoggingActivity.class);
                //Indirizzare la chiamata al presenter
            }
        });
        presenter.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
