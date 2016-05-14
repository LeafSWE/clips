package com.leaf.clips.view;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

/**
 * ImageDetailView espone i metodi utili al binding della View con la lista di immagini relative
 * ad una certa istruzione.
 */
public interface ImageDetailView {
    /**
     * Collega l'Adapter appropriato alla View deputata a mostrare le immagini relative ad una certa
     * istruzione di navigazione.
     * @param listLength numero delle immagini da mostrare.
     */
    void setAdapter(int listLength);
}
