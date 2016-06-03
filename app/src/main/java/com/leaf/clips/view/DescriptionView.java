package com.leaf.clips.view;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

/**
 * DescriptionView espone i metodi utili per aggiornare la UI con le informazioni riguardanti un
 * singolo POI.
 */
public interface DescriptionView {
    /**
     * Imposta l'identificativo del piano nel widget deputato a mostrarlo.
     * @param ground identificativo del piano
     */
    void setGround(String ground);

    /**
     * Imposta la categoria del POI nel widget deputato a mostrarlo.
     * @param category categoria del POI
     */
    void setCategory(String category);

    /**
     * Imposta la descrizione del POI nel widget deputato a mostrarla.
     * @param description descrizione del POI
     */
    void setDescription(String description);
}
