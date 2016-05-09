package com.leaf.clips.view;

import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */
public interface DetailedInformationView {
    void setPhoto(PhotoInformation photo);

    /**
     * Metodo che imposta una stringa formattata in HTML come testo della TextView dedicata a
     * mostrare l'istruzione dettagliata.
     * @param detailedInstr: Stringa contenente l'istruzione dettagliata formattata come HTML.
     */
    void setDetailedDescription(String detailedInstr);
}
