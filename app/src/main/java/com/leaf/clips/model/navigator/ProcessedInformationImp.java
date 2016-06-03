package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 *
 *
 */

import android.util.Log;

import com.leaf.clips.model.navigator.graph.edge.DefaultEdge;
import com.leaf.clips.model.navigator.graph.edge.ElevatorEdge;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.navigator.graph.edge.StairEdge;
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
    private NavigationDirection direction;

    /**
     * Indicazioni dettagliate di un Edge
     */
    private String detailed;

    /**
     * Lista di foto di un Edge
     */
    private PhotoInformation photos;

    /**
     * Costruttore della classe ProcessedInformationImp
     */
    public ProcessedInformationImp(){
        this.basic = "Destinazione Raggiunta";
        this.detailed = "Hai raggiunto la tua destinazione. Questa dovrebbe trovarsi intorna a te.";
        this.photos = null;
        this.direction = NavigationDirection.DESTINATION;
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
        if(edge instanceof ElevatorEdge)
            if((edge.getEndPoint().getFloor()-edge.getStarterPoint().getFloor())>0)
                this.direction = NavigationDirection.ELEVATOR_UP;
            else
                this.direction = NavigationDirection.ELEVATOR_DOWN;
        else if (edge instanceof StairEdge)
            if((edge.getEndPoint().getFloor()-edge.getStarterPoint().getFloor())>0)
                this.direction = NavigationDirection.STAIR_UP;
            else
                this.direction = NavigationDirection.STAIR_DOWN;
        else{
            this.direction = NavigationDirection.STRAIGHT;
        }
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
    public ProcessedInformationImp(EnrichedEdge edge, int starterInformation) {
        //TODO: fare get da edge e includere le starterInformation come ???
        this.basic = edge.getBasicInformation();
        this.detailed = edge.getDetailedInformation();
        this.photos = edge.getPhotoInformation();
        if(edge instanceof ElevatorEdge)
            if((edge.getEndPoint().getFloor()-edge.getStarterPoint().getFloor())>0)
                this.direction = NavigationDirection.ELEVATOR_UP;
            else
                this.direction = NavigationDirection.ELEVATOR_DOWN;
        else if (edge instanceof StairEdge)
            if((edge.getEndPoint().getFloor()-edge.getStarterPoint().getFloor())>0)
                this.direction = NavigationDirection.STAIR_UP;
            else
                this.direction = NavigationDirection.STAIR_DOWN;
        else{
            if (starterInformation > 20 && starterInformation < 150)
                this.direction = NavigationDirection.RIGHT;
            else if (starterInformation >= 210 && starterInformation < 340)
                this.direction = NavigationDirection.LEFT;
            else if (starterInformation >= 150 && starterInformation <210)
                this.direction = NavigationDirection.TURN;
            else
                this.direction = NavigationDirection.STRAIGHT;
        }
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
    public NavigationDirection getDirection() {return this.direction;}

    /**
     * Metodo che ritorna la distanza da percorrere nell'arco in cui ci si trova
     * @return String
     */
    @Override
    public String getDistance() {
        return this.distance;
    }

    /**
     * Metodo che permette di comparare un oggetto con una istanza della classe ProcessedInformationImp
     * @param o Oggetto da comparare
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        boolean rx;
        if (o instanceof ProcessedInformation) {
            ProcessedInformation another = (ProcessedInformation)o;
            if (basic.equals(another.getProcessedBasicInstruction()) &&
                    detailed.equals(another.getDetailedInstruction()) &&
                    distance.equals(another.getDistance()))
                rx = true;
            else
                rx = false;
        } else
            rx = false;
        Log.i("EQUALSRX", rx+"");
        return rx;
    }
}

