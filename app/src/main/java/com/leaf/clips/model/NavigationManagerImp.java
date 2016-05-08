package com.leaf.clips.model;
/**
* @author Federico Tavella
* @version 0.08
* @since 0.00
*
*/

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;

import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.compass.Compass;
import com.leaf.clips.model.navigator.NavigationExceptions;
import com.leaf.clips.model.navigator.Navigator;
import com.leaf.clips.model.navigator.NavigatorImp;
import com.leaf.clips.model.navigator.NoNavigationInformationException;
import com.leaf.clips.model.navigator.PathException;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.usersetting.SettingImp;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Classe che si occupa della gestione della navigazione
 */

public class NavigationManagerImp extends AbsBeaconReceiverManager implements NavigationManager {

    /**
    * Oggetto che permette di recuperare i dati della bussola
    */
    private final Compass compass = new Compass((SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE));

    /**
    * Grafo rappresentante la mappa dell'edificio
    */
    private final MapGraph graph;

    /**
    * PriorityQueue, eventualmente vuota, contenente gli ultimi beacon rilevati
    */
    private PriorityQueue<MyBeacon> lastBeaconsSeen;

    /**
    * Collezione contenenti tutti i listener da aggiornare ad ogni nuova istruzione da inviare
    */
    private Collection<NavigationListener> listeners;

    /**
    * Oggetto per la navigazione
    */
    private Navigator navigator;

    /**
    * Costruttore della classe NavigationManagerImp
    * @param graph Grafo dell'edificio in cui si desidera navigare
    * @param context Contesto dell'applicazione
    */
    public NavigationManagerImp(MapGraph graph, Context context){
        super(context);
        this.graph = graph;
        listeners = new LinkedList<>();
        lastBeaconsSeen = new PriorityQueue<>();

    }

    /**
    * Metodo che permette di registrare un listener
    * @param listener Listener che deve essere aggiunto alla lista di NavigationListener
    * @return  void
    */
    @Override
    public void addBeaconListener(NavigationListener listener){
        listeners.add(listener);
    }

    /**
    * Metodo che permette di recuperare tutte le istruzioni di navigazione per un percorso
     * calcolato. Viene lanciata una eccezione di tipo NoNavigationInformationException nel caso in
     * cui venga richiamato questo metodo senza aver prima avviato la navigazione
    * @return  List<ProcessedInformation>
    */
    @Override
    public List<ProcessedInformation> getAllNavigationInstruction() throws NavigationExceptions {

        LinkedList<ProcessedInformation> list = new LinkedList<>();
        list.addAll(navigator.getAllInstructions());
        return list;
    }

    /**
     * Metodo che permette di recuperare tutte le istruzioni di navigazione per un percorso calcolato
     * in base al beacon più potente ricavato dalla PriorityQueue<MyBeacon> passata come argomento.
     * Viene lanciata una eccezione di tipo NoNavigationInformationException nel caso in cui venga
     * richiamato questo metodo senza aver prima avviato la navigazione.
     * @return  ProcessedInformation
     */
    @Override
    public ProcessedInformation getNextInstruction() throws NoNavigationInformationException {
        try {
            return navigator.toNextRegion(lastBeaconsSeen);
        } catch (NavigationExceptions navigationExceptions) {
            navigationExceptions.printStackTrace();
            throw new NoNavigationInformationException();
        }
    }



    /**
    * Metodo che permette di rimuovere un listener
    * @param listener Listener che deve essere rimosso dalla lista di NavigationListener
    */
    @Override
    public void removeBeaconListener(NavigationListener listener){
        listeners.remove(listener);
    }

    /**
    * Metodo che setta il campo dati lastBeaconsSeen
    * @param beacons Collection di beacon rilevati nell'area circostante
    */

    private void setVisibleBeacon(PriorityQueue<MyBeacon> beacons){
        for(MyBeacon oneBeacon : beacons){
            lastBeaconsSeen.add(oneBeacon);
        }
    }

    /**
     * Metodo che permette di attivare il rilevamento dei dati dalla bussola
     */
    @Override
    public void startCompass(){
        compass.registerListener();
    }

    /**
     *
     * @param endPOI POI da raggiungere tramite navigazione
     * @return ProcessedInformation Informazioni necessarie per avviare la navigazione
     */

    @Override
    public ProcessedInformation startNavigation(PointOfInterest endPOI)
            throws NoNavigationInformationException {
        graph.setSettingAllEdge(new SettingImp(getContext()));
        navigator = new NavigatorImp(compass);
        navigator.setGraph(graph);
        MyBeacon beacon = lastBeaconsSeen.peek();
        Iterator<RegionOfInterest> iterator = graph.getGraph().vertexSet().iterator();
        boolean found = false;
        RegionOfInterest startROI = null;

        while(!found && iterator.hasNext()){
            startROI = iterator.next();
            found = startROI.contains(beacon);
        }

        try {
            navigator.calculatePath(startROI, endPOI);
        } catch (NavigationExceptions navigationExceptions) {
            navigationExceptions.printStackTrace();
        }

        ProcessedInformation processedInfo = null;

        try {
            processedInfo = navigator.toNextRegion(lastBeaconsSeen);
        } catch (NoNavigationInformationException noNavInfoExc) {
            noNavInfoExc.printStackTrace();
            try {
                navigator.calculatePath(startROI, endPOI);
            } catch (NavigationExceptions navigationExceptions) {
                navigationExceptions.printStackTrace();
            }
            throw new NoNavigationInformationException("Impossibile risolvere il problema");
        } catch (PathException pathException){
            pathException.printStackTrace();
        } catch (NavigationExceptions navigationExceptions) {
            navigationExceptions.printStackTrace();
        }

        return processedInfo;
    }

    /**
     * Metodo che permette di fermare il rilevamento dei dati ottenuti dalla bussola
     */
    @Override
    public void stopCompass(){
        compass.unregisterListener();
    }

    /**
    * Metodo che permette di fermare la navigazione
    */
    @Override
    public void stopNavigation(){
        listeners.clear();
        navigator = null;
    }

    /**
     * Metodo che si occupa di settare il campo dati lastBeaconsSeen con la PriorityQueue<MyBeacon>
     *     contenente gli ultimi beacon rilevati e di aggiornare tutti I listeners con le ultime
     *     istruzioni di navigazione
     * @param context Contesto dell'applicazione
     * @param intent Intent contenente le informazioni del messaggio
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        lastBeaconsSeen.clear();
        PriorityQueue<MyBeacon> p;

        p = ((PriorityQueue<MyBeacon>)intent.getSerializableExtra("queueOfBeacons"));

        if(!lastBeaconsSeen.containsAll(p) || !p.containsAll(lastBeaconsSeen)){

            setVisibleBeacon(p);
            for(NavigationListener nv : listeners){
                try {
                    nv.informationUpdate(navigator.toNextRegion(lastBeaconsSeen));
                } catch (NoNavigationInformationException navigationExceptions) {
                    navigationExceptions.printStackTrace();
                    nv.pathError();
                } catch (NavigationExceptions navigationExceptions){
                    navigationExceptions.printStackTrace();
                }
            }
        }


    }
}

