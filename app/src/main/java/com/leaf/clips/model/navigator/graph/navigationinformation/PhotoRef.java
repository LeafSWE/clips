package com.leaf.clips.model.navigator.graph.navigationinformation;

import java.net.URI;

/**
 * @author Cristian Andrighetto
 * @version 0.03
 * @since 0.00
 */
public class PhotoRef {
    /**
     * Identificativo della fotografia
     */
    private final int id;
    /**
     * URL di una fotografia
     */
    private final URI photoUrl;
    
    /**
     * Costruttore della classe PhotoRef
     * @param id Identificativo della fotografia
     * @param source URL di una fotografia
     */
    public PhotoRef(int id, URI source) {
        this.id=id;
        this.photoUrl = source;
    }

    /**
     * Metodo per recuperare l'identificativo della foto
     * @return int identificativo della foto
     */
    public int getId(){
        return id;
    }
    /**
     * Metodo che restituisce l'URL per accedere alla fotografia che l'oggetto rappresenta
     * @return URI di una foto
     */
    public URI getPhotoUri() {
        return this.photoUrl;
    }

}
