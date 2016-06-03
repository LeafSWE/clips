package com.leaf.clips.model;
/**
 * @author Federico Tavella
 * @version 0.11
 * @since 0.09
 *
 */

import android.content.Context;
import android.content.Intent;

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
import com.leaf.clips.presenter.MyApplication;

import junit.framework.Assert;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import javax.inject.Inject;

/**
 * Classe che si occupa della gestione della navigazione
 */

public class NavigationManagerImp extends AbsBeaconReceiverManager implements NavigationManager {

    /**
     * Oggetto che permette di recuperare i dati della bussola
     */
   // private final Compass compass = new Compass((SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE));

    /**
     * Grafo rappresentante la mappa dell'edificio
     */
    private final MapGraph graph;

    /**
     * PriorityQueue, eventualmente vuota, contenente gli ultimi beacon rilevati
     */
    //private PriorityQueue<MyBeacon> lastBeaconsSeen;

    @Inject
    Compass compass;

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
        //lastBeaconsSeen = new PriorityQueue<>();
        this.graph.setSettingAllEdge(new SettingImp(getContext()));
        // TODO: 18/05/16 questo è stato corretto -> chiedere perchè diverso da uml
        MyApplication.getInfoComponent().inject(this);
        navigator = new NavigatorImp(compass, new SettingImp(context));
        navigator.setGraph(this.graph);
    }

    /**
     * Metodo che permette di registrare un listener
     * @param listener Listener che deve essere aggiunto alla lista di NavigationListener
     */
    @Override
    public void addListener(NavigationListener listener){
        super.addListener(listener);
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
     * in base al beacon più potente ricavato da una PriorityQueue<MyBeacon>.
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
    public void removeListener(NavigationListener listener){
        super.removeListener(listener);
    }

    /**
     * Metodo che setta il campo dati lastBeaconsSeen
     * @param beacons Collection di beacon rilevati nell'area circostante
     */

    private void setVisibleBeacon(PriorityQueue<MyBeacon> beacons){
        lastBeaconsSeen.clear();
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
     * Metodo che permette di avviare la navigazione verso uno specifico POI
     * @param endPOI POI da raggiungere tramite navigazione
     * @return ProcessedInformation Informazioni necessarie per avviare la navigazione
     */

    @Override
    public ProcessedInformation startNavigation(PointOfInterest endPOI)
            throws NoNavigationInformationException {
        //navigator = new NavigatorImp(compass, new SettingImp(getContext()));
        MyBeacon beacon = lastBeaconsSeen.peek();
        Iterator<RegionOfInterest> iterator = graph.getGraph().vertexSet().iterator();
        boolean found = false;
        RegionOfInterest startROI = null;

        while(!found && iterator.hasNext()){
            startROI = iterator.next();
            found = startROI.contains(beacon);
        }

        try {
            Assert.assertNotNull(startROI);
            Assert.assertNotNull(endPOI);
            Assert.assertNotNull(navigator);
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
        for(Listener listener : super.listeners){
            NavigationListener nv = (NavigationListener) listener;
            nv.informationUpdate(processedInfo);
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
        super.listeners.clear();
        //navigator = null;
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

        PriorityQueue<MyBeacon> p;

        p = ((PriorityQueue<MyBeacon>)intent.getSerializableExtra("queueOfBeacons"));

        if(!lastBeaconsSeen.containsAll(p) || !p.containsAll(lastBeaconsSeen))
            setVisibleBeacon(p);
        lastBeaconsSeen = p;
        for(Listener listener : super.listeners){
                NavigationListener nv = (NavigationListener) listener;
                try {
                    nv.informationUpdate(navigator.toNextRegion(lastBeaconsSeen));
                } catch (NoNavigationInformationException navigationExceptions) {
                    navigationExceptions.printStackTrace();
                    nv.pathError();
                } catch (PathException pathException){
                    nv.pathError();
                } catch (NavigationExceptions navigationExceptions){
                    navigationExceptions.printStackTrace();
                }
            }



    }

    /**
     * Metodo che permette di accedere al grafo che rappresenta l'edificio
     * @return MapGraph
     */
    @Override
    public MapGraph getGraph(){
        return graph;
    }
}

