package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 */

import android.nfc.Tag;
import android.util.Log;

import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.compass.Compass;
import com.leaf.clips.model.navigator.algorithm.DijkstraPathFinder;
import com.leaf.clips.model.navigator.algorithm.PathFinder;
import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.usersetting.Setting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Classe che si occupa della navigazione
 */
public class NavigatorImp implements Navigator {

    /**
     * Grafo dell'edificio in cui si desidera navigare
     */
    private MapGraph buildingGraph;

    /**
     * Sensore di tipo Compass utilizzato all'avvio della navigazione
     */
    private Compass compass;

    /**
     * Lista di EnrichedEdge rappresentanti le indicazioni da seguire per raggiungere la
     * destinazione
     */
    private List<EnrichedEdge> path;

    /**
     * Campo dati che si occupa del calcolo del percorso
     */
    private PathFinder pathFinder;

    /**
     * Iteratore rappresentante il punto che è stato raggiunto durante la navigazione
     */
    private Iterator<EnrichedEdge> progress;

    /**
     * Costruttore della classe NavigatorImp
     *
     * @param compass Sensore di tipo Compass utilizzato durante la navigazione
     */
    public NavigatorImp(Compass compass) {
        this.compass = compass;
        this.pathFinder = new DijkstraPathFinder();
        this.path = null;
        this.buildingGraph = null;
        this.progress = null;
    }

    /**
     * TODO: attenzione, costruttore non previsto dalla progettazione Costruttore presente solo a
     * fini di testing
     */
    public NavigatorImp(Compass compass, PathFinder dijkstraPathFinder) {
        this.compass = compass;
        this.pathFinder = dijkstraPathFinder;
        this.path = null;
        this.buildingGraph = null;
        this.progress = null;
    }

    /**
     * Metodo che calcola un percorso tra due RegionOfInterest viste come vertici di un grafo
     * MapGraph utilizzando un oggetto PathFinder. Il punto di partenza e il punto di arrivo sono i
     * parametri richiesti in input dal metodo mentre il grafo è un campo dati. Viene lanciata un
     * eccezione di tipo NoGraphSetException nel caso in cui non sia settato alcun grafo
     *
     * @param startRoi Punto di partenza del percorso
     * @param endPoi   Punto di arrivo del percorso
     */
    @Override
    public void calculatePath(RegionOfInterest startRoi, PointOfInterest endPoi) throws NavigationExceptions {
        if (buildingGraph != null) {
            Collection<RegionOfInterest> endRois = endPoi.getAllBelongingROIs();
            Iterator<RegionOfInterest> endRoisIterator = endRois.iterator();
            if (endRoisIterator.hasNext()) {
                // prelevo il primo path considerandolo il migliore
                RegionOfInterest endRoi = endRoisIterator.next();
                List<EnrichedEdge> shortestPath = pathFinder.calculatePath(this.buildingGraph, startRoi, endRoi);

                // prelevo gli altri path e li confronto con il migliore attuale
                while (endRoisIterator.hasNext()) {
                    RegionOfInterest nextEndRoi = endRoisIterator.next();
                    List<EnrichedEdge> newPath = pathFinder.calculatePath(this.buildingGraph, startRoi, endRoi);
                    if (isShorter(newPath, shortestPath)) {
                        shortestPath = newPath;
                    }
                }
                path = shortestPath;
            } else {
                throw new PathException("endPoi without ROI");
            }
        } else {
            throw new NoGraphSetException();
        }
    }

    /**
     * Metodo che crea le ProcessedInformation in base al tipo di arco e in base alle informazioni
     * provenienti dal beacon e da eventuali sensori utilizzati
     *
     * @param edge Edge di cui devono essere recuperate le informazioni
     * @return ProcessedInformation
     */
    private ProcessedInformation createInformation(EnrichedEdge edge) {
        return new ProcessedInformationImp(edge);
    }

    /**
     * Metodo che ritorna la lista completa delle ProcessedInstruction da seguire per percorrere un
     * percorso calcolato
     *
     * @return List<ProcessedInformation>
     */
    @Override
    public List<ProcessedInformation> getAllInstructions() throws NavigationExceptions {
        if (path != null) {
            ArrayList<ProcessedInformation> result = new ArrayList<>();
            for (EnrichedEdge edge : path) {
                ProcessedInformation edgeProcessedInformation = new ProcessedInformationImp(edge);
                result.add(edgeProcessedInformation);
            }
            return result;
        } else {
            throw new NoNavigationInformationException();
        }
    }

    /**
     * Metodo che ritorna il beacon con potenza maggiore tra quelli rilevati
     *
     * @param beacons Queue dei beacon rilevati
     * @return MyBeacon
     */
    private MyBeacon getMostPowerfulBeacon(PriorityQueue<MyBeacon> beacons) {
        return beacons.peek();
    }

    /**
     * Metodo per ottenere la lista di EnrichedEdge rappresentanti un percorso
     *
     * @return List<EnrichedEdge>
     */
    public List<EnrichedEdge> getPath() {
        return this.path;
    }

    /**
     * Metodo che ritorna le prime informazioni utili alla navigazione
     *
     * @return String
     */
    private String getStarterInformation() {
        Float deviceGrade = compass.getLastCoordinate();
        int correctGrade = 0;
        if (progress.hasNext()) {
            correctGrade = progress.next().getCoordinate();
        } else {
            //TODO: Attenzione cosa fare nel caso iterator sia alla fine o non sia inizializzato???
        }
        float negRange = correctGrade - 90;
        float posRange = (correctGrade + 90) % 360;
        if (negRange < 0) {
            negRange = negRange + 360;
        }
        if (negRange < deviceGrade || deviceGrade < posRange) {
            return "Direzione corretta"; // TODO: usare una Android Resource
        } else {
            return "Direzione sbagliata, voltati";
        }
    }

    /**
     * Metodo per determinare se un percorso è più lungo o più breve di un altro percorso
     *
     * @param firstPath  Lista di EnrichedEdge rappresentante un percorso
     * @param secondPath Lista di EnrichedEdge rappresentante un percorso
     * @return boolean
     */
    private boolean isShorter(List<EnrichedEdge> firstPath, List<EnrichedEdge> secondPath) {
        double weightFirstPath = 0;
        double weightSecondPath = 0;
        for (EnrichedEdge firstPathEdge : firstPath) {
            weightFirstPath += firstPathEdge.getWeight();
        }
        for (EnrichedEdge secondPathEdge : secondPath) {
            weightSecondPath += secondPathEdge.getWeight();
        }
        if (weightFirstPath < weightSecondPath) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo per settare il grafo sul quale calcolare il percorso
     *
     * @param graph Grafo sul quale si vogliono calcolare dei percorsi
     */
    @Override
    public void setGraph(MapGraph graph) {
        this.buildingGraph = graph;
    }

    /**
     * Metodo che ritorna le informazioni da seguire per raggiungere la prossima RegionOfInterest.
     * Le informazioni fornite dipendono dalla lista di beacon passata come parametro in ingrasso e
     * dal beacon più potente tra quelli in essa contenuti. Viene lanciata una eccezione di tipo
     * NoNavigationInformationException nel caso in cui si cerchi di accedere a tale metodo senza
     * prima aver calcolato un percorso di navigazione. Viene lanciata una eccezione di tipo
     * PathException nel caso in cui il beacon più potente nella lista di beacon in ingrasso sia
     * associato ad una RegionOfInterest non appartenente ad una di quelle presenti nel percorso
     * calcolato
     *
     * @param visibleBeacons Insieme di beacon visibili al momento della chiamata al metodo
     * @return ProcessedInformation
     */
    @Override
    public ProcessedInformation toNextRegion(PriorityQueue<MyBeacon> visibleBeacons) throws NavigationExceptions {
        String startInformation = "";
        // Capisco se è la prima richiesta di informazioni
        if (progress == null) { // È all'inizio della navigazione
            this.initIterator();
            startInformation = this.getStarterInformation();
        }
        // Prelevo il beacon più potente per capire se l'utente è nel percorso previsto
        MyBeacon nearBeacon = this.getMostPowerfulBeacon(visibleBeacons);
        if (progress.hasNext()) {
            EnrichedEdge nextEdge = progress.next();
            RegionOfInterest endEdgeROI = nextEdge.getEndPoint();
            if (endEdgeROI.contains(nearBeacon)) { // OK: percorso corretto
                //TODO: non si utilizza il metodo this.createInformation(edge)
                return new ProcessedInformationImp(nextEdge, startInformation);
            } else { // ERROR: errore percorso seguito errato
                throw new PathException();
            }
        } else {
            Log.d("NAVIGATOR", "toNextRegion: Progress iterator at end");
            throw new PathException("Navigation finish, progress iterator at end");
        }
    }

    /**
     * Metodo che ritorna un booleano false se il percorso è concluso
     *
     * @return boolean
     */
    @Override
    public boolean hasFinishedPath() {
        return progress.hasNext();
    }

    /**
     * Metodo che inizializza l'iteratore del percorso calcolato
     */
    private void initIterator() throws NavigationExceptions {
        if (path != null) {
            progress = path.iterator();
        } else {
            throw new NoNavigationInformationException();
        }
    }

}

