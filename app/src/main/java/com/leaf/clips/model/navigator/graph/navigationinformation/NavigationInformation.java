package com.leaf.clips.model.navigator.graph.navigationinformation;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 */

/**
 * Descrizione: Interfaccia che espone i metodi per accedere alle informazioni di navigazione
 * Utilizzo: È utilizzata per rendere indipendente l'implementazione delle informazioni di navigazione dall'utilizzo di quest'ultime
 */
public interface NavigationInformation {
    /**
     * Metodo che ritorna le informazioni di base per il superamento dell'arco al quale tale oggetto è associato
     * @return String infromazioni teastuali di base
     */
    String getBasicInformation();

    /**
     * Metodo che ritorna delle informazioni dettagliate per il superamento dell'arco al quale tale oggetto è associato
     * @return String informazioni testuali dettagliate
     */
    String getDetailedInformation();

    /**
     * Metodo che ritorna un oggetto PhotoInformation contenente i riferimenti alle fotografie riguardanti l'arco al quale tale oggetto è associato
     * @return PhotoInformation
     */
    PhotoInformation getPhotoInformation();
}
