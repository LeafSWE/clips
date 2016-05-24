package com.leaf.clips.model.navigator.graph.navigationinformation;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

/**
 * Descrizione: Classe contenente le informazioni dettagliate utilizzate per la navigazione
 * Utilizzo: È utilizzata per restituire le informazioni dettagliate sotto forma di testo, utilizzate durante la navigazione
 */
public class DetailedInformation {
    /**
     * Descrizione dettagliata delle azioni da compiere per il superamento di un certo arco
     */
    private final String longDescription;

    /**
     * Costruttore
     * @param longDescription descrizione dettagliata delle informazioni per la navigazione di un arco
     */
    public DetailedInformation(String longDescription){
        this.longDescription = longDescription;
    }

    /**
     * Metodo che ritorna le informazioni contenute in un oggetto DetailedInformation per il superamento di un determinato arco
     * @return String infromazioni dettagliate
     */
    public String getDetailedInformation(){
        return this.longDescription;
    }
}
