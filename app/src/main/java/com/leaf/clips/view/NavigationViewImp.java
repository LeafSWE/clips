package com.leaf.clips.view;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.model.navigator.NavigationDirection;
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
        if (instruction != null) {
            instruction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    presenter.showDetailedInformation(position);
                }
            });
        }
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

        if (listView != null) {
            listView.setAdapter(instructionAdapter);
        }

    }

    /**
     * @inheritDoc
     */
    @Override
    public void refreshInstructions() {
        lastRefresh = 0;
    }

    int lastRefresh = 0;

    @Override
    public void refreshInstructions(int i) {
        Log.i("informationUpdate", "refresh:" + i);
        ListView listView = (ListView) presenter.findViewById(R.id.view_instruction_list);
        if (listView != null) {
            listView.getChildAt(i).setBackgroundColor(presenter.getResources().getColor(R.color.currentInstructionColor));
        }
        for(int j = 0; j < i; j++){
            if (listView != null) {
                listView.getChildAt(j).setBackgroundColor(presenter.getResources().getColor(R.color.white));
            }
            if (listView != null) {
                listView.getChildAt(j).findViewById(R.id.imageView_check).setVisibility(View.VISIBLE);
            }
        }
        if (listView != null) {
            for(int j = i+1; j < listView.getCount(); j++){
                View child = listView.getChildAt(j);
                if(child != null)
                    child.setBackgroundColor(presenter.getResources().getColor(R.color.white));
            }


        }
        lastRefresh = i;
    }

    /**
     * Metodo che viene invocato nel caso in cui non siano presenti risultati
     */
    @Override
    public void noResult(){
        presenter.setContentView(R.layout.activity_navigation_error);
    }

    /**
     * Metodo che permette di aggiornare la direzione della freccia per la navigazione
     * @param direction Direzione in cui la freccia deve puntare
     */
    @Override
    public void updateArrow(NavigationDirection direction) {

        ListView listView = (ListView) presenter.findViewById(R.id.view_instruction_list);
        if(listView != null && listView.getChildAt(lastRefresh) != null) {
            ImageView image = (ImageView) listView.getChildAt(lastRefresh).findViewById(R.id.imageView_direction);
            if (image != null) {
                NavigationDirection direction1 = ((ProcessedInformation)instructionAdapter.getItem(lastRefresh)).getDirection();
                if (!(direction1 == NavigationDirection.DESTINATION || direction1 == NavigationDirection.ELEVATOR_DOWN ||
                        direction1 == NavigationDirection.ELEVATOR_UP || direction1 == NavigationDirection.STAIR_DOWN ||
                        direction1 == NavigationDirection.STAIR_UP)) {
                    switch (direction) {
                        case STRAIGHT:
                            image.setBackgroundResource(R.drawable.arrow_go_straight);
                            break;
                        case LEFT:
                            image.setBackgroundResource(R.drawable.arrow_turn_left);
                            break;
                        case RIGHT:
                            image.setBackgroundResource(R.drawable.arrow_turn_right);
                            break;
                        case TURN:
                            image.setBackgroundResource(R.drawable.arrow_turn_around);
                            break;
                    }
                }
            }
        }
    }

    @Override
    public ProcessedInformation getActualInformation() {
        return (ProcessedInformation)instructionAdapter.getItem(lastRefresh);
    }
}
