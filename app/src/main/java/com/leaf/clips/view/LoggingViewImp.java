package com.leaf.clips.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.LoggingActivity;

import java.util.ArrayList;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public class LoggingViewImp implements LoggingView {
    /**
     * Bottone per interrompere un log in corso
     */
    private FloatingActionButton btnStopLog;

    /**
     * Lista di log salvati sul dispositivo
     */
    private ListView listLog;

    /**
     * Presenter della View
     */
    private LoggingActivity presenter;

    /**
     * Costruttore della classe LoggingViewImp
     * @param presenter Presenter della View che viene creata
     */
    public LoggingViewImp(final LoggingActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_logging);
        Toolbar toolbar = (Toolbar)  presenter.findViewById(R.id.toolbar);
        presenter.setSupportActionBar(toolbar);

        listLog = (ListView) presenter.findViewById(R.id.visible_beacons_list);


        FloatingActionButton fab = (FloatingActionButton) presenter.findViewById(R.id.fab_stop_logging);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.stopLogging();
                }
            });
        }

        ActionBar actionBar = presenter.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Metodo utilizzato per visualizzare la lista dei beacon rilevati
     * @param beacons Collegamento tra la lista dei beacon rilevati e la view in cui essi devono essere mostrati
     */
    @Override
    public void setBeaconListAdapter(StringBuffer beacons){
        final ArrayList<String> list = new ArrayList<>();
        list.add(beacons.toString());

        final ArrayAdapter adapter = new ArrayAdapter(presenter, android.R.layout.simple_list_item_1, list);
        listLog.setAdapter(adapter);
    }
}
