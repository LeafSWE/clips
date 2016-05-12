package com.leaf.clips.view;

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
public class NavigationViewImp implements NavigationView {
    private NavigationActivity presenter;
    private NavigationAdapter instructionAdapter;

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
