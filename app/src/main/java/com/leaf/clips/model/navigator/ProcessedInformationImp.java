package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 *
 *
 */

import com.leaf.clips.model.navigator.graph.edge.DefaultEdge;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;

/**
 *Classe che rappresenta le informazioni di navigazione pronte per essere restituite ad un eventuale utilizzatore
 */
public class ProcessedInformationImp implements ProcessedInformation {

    /**
     * La distanza da percorrere in un certo Edge
     */
    private String distance;

    /**
     * Informazioni di base di un Edge
     */
    private String basic;

    /**
     * La prossima direzione verso cui dirigersi
     */
    private int direction;

    /**
     * Indicazioni dettagliate di un Edge
     */
    private String detailed;

    /**
     * Lista di foto di un Edge
     */
    private PhotoInformation photos;

    ProcessedInformationImp(){
        this.basic = "Destinazione Raggiunta";
        this.detailed = "Hai raggiunto la tua destinazione. Questa dovrebbe trovarsi intorna a te.";
        this.photos = null;
        this.direction = 9;
        this.distance = "La destinazione dovrebbe essere vicino a te";
    }

    /**
     * Costruttore della classe ProcessedInformationImp
     * @param edge Edge da cui devono essere estratte le informazioni
     */
    public ProcessedInformationImp(EnrichedEdge edge) {
        this.basic = edge.getBasicInformation();
        this.detailed = edge.getDetailedInformation();
        this.photos = edge.getPhotoInformation();
        this.direction = edge.getCoordinate();
        int dist = (int)edge.getDistance();
        if(edge instanceof DefaultEdge)
            this.distance = dist+" m";
        else
            this.distance = dist+" piano";
    }

    /**
     * Costruttore della classe ProcessedInformationImp
     * @param edge Edge da cui devono essere estratte le informazioni
     * @param starterInformation Informazioni aggiuntive per costruire le informazioni associate ad
     *                           un arco del percorso per superarlo
     */
    public ProcessedInformationImp(EnrichedEdge edge, String starterInformation) {
        //TODO: fare get da edge e includere le starterInformation come ???
        this.basic = starterInformation + " " + edge.getBasicInformation();
        this.detailed = edge.getDetailedInformation();
        this.photos = edge.getPhotoInformation();
        this.direction = edge.getCoordinate();
        int dist = (int)edge.getDistance();
        if(edge instanceof DefaultEdge)
            this.distance = dist+" m";
        else
            this.distance = dist+" piano";
    }

    /**
     * Metodo che ritorna le istruzioni dettagliate per superare un certo arco nel percorso calcolato
     * @return  String
     */
    @Override
    public String getDetailedInstruction() {
        return this.detailed;
    }

    /**
     * Metodo che ritorna un oggetto PhotoInformation con il quale è possibile accedere alle
     * fotografie che ritraggono l'arco da superare nel percorso calcolato
     * @return  PhotoInformation
     */
    @Override
    public PhotoInformation getPhotoInstruction() {
        return photos;
    }

    /**
     * Metodo che ritorna le istruzioni basilari per superare un certo arco nel percorso calcolato
     * @return  String
     */
    @Override
    public String getProcessedBasicInstruction() {
        return this.basic;
    }

    /**
     * Metodo che ritorna la direzione verso cui dirigersi
     * @return  int
     */
    @Override
    public int getDirection() {return this.direction;}

    /**
     * Metodo che ritorna la distanza da percorrere nell'arco in cui ci si trova
     * @return String
     */
    @Override
    public String getDistance() {
        return this.distance;
    }

    @Override
    public int compareTo(ProcessedInformation another) {
        if (basic.equals(another.getProcessedBasicInstruction()) && distance == another.getDistance() &&
                detailed.equals(another.getDetailedInstruction()) && direction == another.getDirection())
            return 0;
        else
            return 1;
    }
}

