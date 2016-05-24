package com.leaf.clips.model.navigator.graph.navigationinformation;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

/**
 * Descrizione: Classe utilizzata per recuperare le informazioni da fornire all'utente per la navigazione
 * Utilizzo: È utilizzata per recuperare le informazioni testuali, testuali estese e visive per permettere all'utente di navigare tramite dei campi dati di tipo BasicInformation, DetailedInformation e PhotoInformation
 */
public class NavigationInformationImp implements NavigationInformation {
    private final BasicInformation basicInformation;
    private final DetailedInformation detailedInformation;
    private final PhotoInformation photoInformation;

    /**
     * Costruttore della classe NavigationInformationImp
     * @param basicInformation informazioni di base
     * @param detailedInformation informazioni dettagliate
     * @param photoInformation foto associate ad un certo arco
     */
    public NavigationInformationImp(BasicInformation basicInformation, DetailedInformation detailedInformation, PhotoInformation photoInformation){
        this.basicInformation = basicInformation;
        this.detailedInformation = detailedInformation;
        this.photoInformation = photoInformation;
    }

    /**
     * Metodo che ritorna le informazioni di base per il superamento dell'arco al quale tale oggetto è associato
     * @return String informazioni testuali di base
     */
    @Override
    public String getBasicInformation() {
        return this.basicInformation.getBasicInstruction();
    }

    /**
     * Metodo che ritorna delle informazioni dettagliate per il superamento dell'arco al quale tale oggetto è associato
     * @return String informazioni dettagliate testuali
     */
    @Override
    public String getDetailedInformation() {
        return this.detailedInformation.getDetailedInformation();
    }

    /**
     * Metodo che ritorna un oggetto PhotoInformation contenente i riferimenti alle fotografie riguardanti l'arco al quale tale oggetto è associato
     * @return PhotoInformation foto associate ad un certo arco
     */
    @Override
    public PhotoInformation getPhotoInformation() {
        return this.photoInformation;
    }
}
