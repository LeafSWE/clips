package com.leaf.clips.model.navigator.graph.navigationinformation;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

/**
 * Descrizione: Classe contenente le informazioni di base per la navigazione
 * Utilizzo: È utilizzata per restituire le informazioni di base necessarie per la navigazione sotto forma di testo
 */
public class BasicInformation {
    /**
     * Azione da compiere per superare un determinato arco
     */
    private final String action;

    /**
     * Costruttore della classe BasicInformation
     * @param basicInformation informazioni di base per la navigazione di un arco
     */
    public BasicInformation(String basicInformation){
        this.action = basicInformation;
    }

    /**
     * Metodo che ritorna le informazioni contenute in un oggetto BasicInformation per il superamento di un determinato arco
     * @return String informazioni di base per la navigazione di un arco
     */
    public String getBasicInstruction(){
        return this.action;
    }


}
