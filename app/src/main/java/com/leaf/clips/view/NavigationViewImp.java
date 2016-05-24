package com.leaf.clips.view;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.presenter.NavigationActivity;
import com.leaf.clips.presenter.NavigationAdapter;

import java.util.List;

/**
 * @author Andrea Tombolato
 * @version 0.04
 * @since 0.00
 */

/**
 * INavigationViewImp si occupa di gestire direttamente i widget della UI deputati a mostrare
 * la lista di istruzioni di navigazione utili per raggiungere una certa destinazione.
 */
public class NavigationViewImp implements NavigationView {

    /**
     * Riferimento al relativo Presenter.
     */
    private NavigationActivity presenter;

    /**
     * Riferimento al widget responsabile di mostrare la lista di istruzioni.
     */
    private NavigationAdapter instructionAdapter;

    /**
     * Costruttore della classe NavigationViewImp
     * @param presenter Presenter che ha il compito di controllare l'oggetto
     */
    public NavigationViewImp(final NavigationActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_navigation);

        ListView instruction = (ListView)presenter.findViewById(R.id.view_instruction_list);
        instruction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.showDetailedInformation(position);
            }
        });
    }

    /**
     * @inheritDoc
     * @param navigationInformation lista delle istruzioni di navigazione.
     */
    @Override
    public void setInstructionAdapter(List<ProcessedInformation> navigationInformation) {
        instructionAdapter = new NavigationAdapter(presenter, navigationInformation);
        instructionAdapter.notifyDataSetChanged();
        ListView listView = (ListView) presenter.findViewById(R.id.view_instruction_list);

        listView.setAdapter(instructionAdapter);

    }

    /**
     * @inheritDoc
     */
    @Override
    public void refreshInstructions() {
    }

    @Override
    public void refreshInstructions(int i) {
        Log.i("informationUpdate", "refresh:" + i);
        ListView listView = (ListView) presenter.findViewById(R.id.view_instruction_list);
        listView.getChildAt(i).setBackgroundColor(presenter.getResources().getColor(R.color.green));
        for(int j = 0; j < i; j++)
            listView.getChildAt(j).setBackgroundColor(presenter.getResources().getColor(R.color.backroundGrey));
        for(int j = i+1; j < listView.getCount(); j++)
            listView.getChildAt(j).setBackgroundColor(presenter.getResources().getColor(R.color.white));
    }

    @Override
    public void noResult(){
        presenter.setContentView(R.layout.activity_navigation_error);
    }
}
