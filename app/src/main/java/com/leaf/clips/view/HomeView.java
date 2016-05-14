package com.leaf.clips.view;

import java.util.List;

/**
 * HomeView espone i metodi utili per aggiornare la UI con le informazioni dell'edificio.
 */
public interface HomeView {
    /**
     * Imposta il nome dell'edificio all'interno del widget progettato per mostrarlo.
     * @param name nome dell'edificio.
     */
    void setBuildingName(String name);

    /**
     * Imposta la descrizione dell'edificio all'interno del widget progettato per mostrarlo.
     * @param description descrizione dell'edificio.
     */
    void setBuildingDescription(String description);

    /**
     * Imposta gli orari di apertura dell'edificio all'interno del widget progettato per mostrarli.
     * @param hours orario di apertura al pubblico.
     */
    void setBuildingOpeningHours(String hours);

    /**
     * Imposta l'indirizzo dell'edificio all'interno del widget progettato per mostrarlo.
     * @param address indirizzo dell'edificio.
     */
    void setBuildingAddress(String address);

    /**
     * Imposta la lista delle categorie di POI dell'edificio all'interno del widget progettato per mostrarlo.
     * @param poiCategoryList lista di categorie di POI dell'edificio.
     */
    void setPoiCategoryListAdapter(List<String> poiCategoryList);
}
