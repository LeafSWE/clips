package com.leaf.clips.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.leaf.clips.R;

import java.util.ArrayList;

public class MainDeveloperActivity extends AppCompatActivity {
    //TODO: creare la View e spostare relativa logica

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_developer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ADAPTER
        final ListView listview = (ListView) findViewById(R.id.saved_log_list);
        String[] values = new String[] { "20160501_0830", "20160501_0835", "20160501_0930",
                "20160501_0940", "20160501_0950", "20160501_1010", "20160501_1030", "20160421_0830",
                "20160421_0837", "20160421_0839", "20160421_1830", "20160421_1840", "20160421_1850", "20160421_1859"};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        /**
         * Imposta il Listener sui click effettuati sugli item della ListView.
         */
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //TODO: dobbiamo passare i dati del log da mostrare nella Activity che stiamo aprendo
                Intent intent = new Intent(MainDeveloperActivity.this,LogInformationActivity.class);
                startActivity(intent);
            }

        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.start_log_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(MainDeveloperActivity.this,LoggingActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void showDetailedLog(int logPosition){
        //TODO
    }

    public void startNewLog(){
        //TODO
    }

}
