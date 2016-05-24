package com.leaf.clips.view;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

/**
 *Interfaccia che espone i metodi per aggiornare la UI contenente la guida dell'applicazione
 */
public interface HelpView {

    /**
     * Metodo utilizzato per visualizzare la guida dell'applicazione
     * @param url Stringa rappresentante l'URL a cui recuperare la guida dell'applicazione
     */
    void setHelp(String url);

}
