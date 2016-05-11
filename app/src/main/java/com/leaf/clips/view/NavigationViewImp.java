package com.leaf.clips.view;

import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.presenter.NavigationActivity;
import com.leaf.clips.presenter.NavigationAdapter;

import java.util.List;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */
public class NavigationViewImp implements NavigationView {
    private NavigationActivity presenter;
    private NavigationAdapter instructionAdapter;

    public NavigationViewImp(NavigationActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_navigation);


    }

    @Override
    public void setInstructionAdapter(List<ProcessedInformation> navigationInformation) {
        instructionAdapter = new NavigationAdapter(presenter, navigationInformation);
        instructionAdapter.notifyDataSetChanged();
        ListView listView = (ListView) presenter.findViewById(R.id.view_instruction_list);
        listView.setAdapter(instructionAdapter);
    }

    @Override
    public void refreshInstructions() {
        //TODO
    }
}
