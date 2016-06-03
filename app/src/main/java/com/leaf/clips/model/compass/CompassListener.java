package com.leaf.clips.model.compass;


/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import com.leaf.clips.model.Listener;

/**
 * Interfaccia base che deve essere implementata dalle classi che vogliono leggere i cambiamenti della bussola
 */
public interface CompassListener extends Listener{

    /**
     * Metodo che viene invocato ad ogni cambiamento della bussola
     * @param orientation Gradazione rilevata dalla bussola
     */
    void changed(float orientation);
}
