package com.leaf.clips.view;

import com.leaf.clips.presenter.NavigationAdapter;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */
public interface NavigationView {
    void setInstructionAdapter(NavigationAdapter adapter);
    void refreshInstructions();
}
