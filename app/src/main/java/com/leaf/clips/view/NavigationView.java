package com.leaf.clips.view;

import com.leaf.clips.model.navigator.ProcessedInformation;

import java.util.List;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */
public interface NavigationView {
    void setInstructionAdapter(List<ProcessedInformation> navigationInformation);
    void refreshInstructions();
}
