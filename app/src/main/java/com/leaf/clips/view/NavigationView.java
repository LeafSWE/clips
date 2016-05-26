package com.leaf.clips.view;
/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */
import com.leaf.clips.model.navigator.NavigationDirection;
import com.leaf.clips.model.navigator.ProcessedInformation;

import java.util.List;

// TODO: 25/05/16 aggiornare tracy/uml

/**
 * NavigationView espone i metodi utili al binding della View con la lista di istruzioni di
 * navigazione utili per raggiungere una certa destinazione.
 */
public interface NavigationView {
    /**
     * Collega l'Adapter appropriato alla View deputata a mostrare a lista di istruzioni di
     * navigazione utili per raggiungere una certa destinazione.
     * @param navigationInformation lista delle istruzioni di navigazione.
     */
    void setInstructionAdapter(List<ProcessedInformation> navigationInformation);

    /**
     * Ricarica le istruzioni.
     */
    void refreshInstructions();

    /**
     * Ricarica le istruzioni.
     */
    void refreshInstructions(int i);

    void noResult();

    void updateArrow(NavigationDirection direction);
}
