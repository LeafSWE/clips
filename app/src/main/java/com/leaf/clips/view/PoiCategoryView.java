package com.leaf.clips.view;

/**
 * @author Marco Zanella
 * @version 0.02
 * @since 0.01
 */

/**
 *Interfaccia che espone i metodi per aggiornare la UI contenente la lista dei POI appartenenti ad una data categoria
 */
public interface PoiCategoryView {

    /**
     * Metodo utilizzato per visualizzare tutti i POI appartenenti ad una certa categoria
     * @param toDisplay Array di stringhe che rappresentano le categorie che devono essere mostrate
     */
    void setPoiListAdapter(String[][] toDisplay);

}

