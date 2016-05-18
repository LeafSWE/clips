package com.leaf.clips.model.navigator.graph.navigationinformation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Oscar Elia Conti
 * @version 0.02
 */

/**
 * Descrizione: Classe che contiene le informazioni visive (sotto forma di URI a foto) utilizzate per la navigazione
 * Utilizzo:	È utilizzata per restituire le informazioni visive utilizzate durante la navigazione
 */
public class PhotoInformation {

    /**
     * Collezione di oggetti PhotoRef rappresentanti le fotografie dell'arco a cui l'oggetto è associato
     */
    private Collection<PhotoRef> photos;

    /**
     * Costruttore della classe PhotoInformation
     * @param photoURLs A list of all the photosUrls of one Navigation Instruction
     */
    public PhotoInformation(Collection<PhotoRef> photoURLs){
        this.photos = new ArrayList<>(photoURLs);
    }

    /**
     * Metodo che restituisce una collezione di oggetti PhotoRef rappresentanti le fotografie dell'arco a cui l'oggetto è associato
     * @return Collection<PhotoRef> associated to one NavigationInstrucion
     */
    public Collection<PhotoRef> getPhotoInformation() {
        return new ArrayList<>(this.photos);
    }
}
