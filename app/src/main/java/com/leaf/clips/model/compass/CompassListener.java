package com.leaf.clips.model.compass;


// TODO: 25/05/16 aggiongere tracy/uml
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

/**
 * Interfaccia base che deve essere implementata dalle classi che vogliono leggere i cambiamenti della bussola
 */
public interface CompassListener {

    /**
     * Metodo che viene invocato ad ogni cambiamento della bussola
     * @param orientation Gradazione rilevata dalla bussola
     */
    void changed(float orientation);
}
