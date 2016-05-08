package com.leaf.clips.view;

import android.widget.Adapter;

import com.leaf.clips.presenter.NavigationActivity;
import com.leaf.clips.presenter.NavigationAdapter;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */
public class NavigationViewImp implements NavigationView {
    private NavigationActivity presenter;
    private Adapter instructionAdapter;

    public NavigationViewImp(NavigationActivity presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setInstructionAdapter(NavigationAdapter adapter) {

    }

    @Override
    public void refreshInstructions() {

    }
}
