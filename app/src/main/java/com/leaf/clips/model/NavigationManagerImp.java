package com.leaf.clips.model;
/**
* @author Federico Tavella
* @version 0.01
* @since 0.00
*
*/

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;

import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.compass.Compass;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 *Classe che si occupa della gestione della navigazione
 */
public class NavigationManagerImp extends AbsBeaconReceiverManager implements NavigationManager { 

    /**
    * Oggetto che permette di recuperare i dati della bussola
    */
    private final Compass compass = new Compass(getContext().getSystemService(SensorManager.class));

    /**
    * Grafo rappresentante la mappa dell'edificio
    */
    private final MapGraph graph;// TODO: 02/05/2016

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
        listeners = new LinkedList<>();
        lastBeaconsSeen = new PriorityQueue<>();
        // TODO: 02/05/2016
        navigator = new NavigatorImp(compass);
        navigator.setGraph(graph);
    }

    /**
    * Metodo che permette di registrare un listener
    * @param listener Listener che deve essere aggiunto alla lista di NavigationListener
    * @return  void
    */
    @Override
    public void addBeaconListener(NavigationListener listener){
        ((List<NavigationListener>)listener).add(listener);
    }

    /**
    * Metodo che permette di recuperare tutte le istruzioni di navigazione per un percorso calcolato. Viene lanciata una eccezione di tipo NoNavigationInformationException nel caso in cui venga richiamato questo metodo senza aver prima avviato la navigazione
    * @return  List<ProcessedInformation>
    */
    @Override
    public List<ProcessedInformation> getAllNavigationInstruction(){

        return navigator.getAllInstruction();
    }

    /**
    * Metodo che permette di recuperare tutte le istruzioni di navigazione per un percorso calcolato in base al beacon più potente ricavato dalla PriorityQueue<MyBeacon> passata come argomento. Viene lanciata una eccezione di tipo NoNavigationInformationException nel caso in cui venga richiamato questo metodo senza aver prima avviato la navigazione.
    * @return  ProcessedInformation
    */
    @Override
    public ProcessedInformation getNextInstruction(){
        return navigator.toNextRegion(lastBeaconsSeen);
    }



    /**
    * Metodo che permette di rimuovere un listener
    * @param listener Listener che deve essere rimosso dalla lista di NavigationListener
    */
    @Override
    public void removeBeaconListener(NavigationListener listener){
        ((List<NavigationListener>)listener).remove(listener);
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
     * @return  void
     */
    @Override
    public void startCompass(){
        // TODO: 02/05/2016
    }

    /**
     *
     * @param endPOI POI da raggiungere tramite navigazione
     * @return ProcessedInformation Informazioni necessarie per avviare la navigazione
     */

    @Override
    public ProcessedInformation startNavigation(PointOfInterest endPOI) {
        // TODO: 02/05/2016
        return null;
    }

    /**
     * Metodo che permette di fermare il rilevamento dei dati ottenuti dalla bussola
     * @return  void
     */
    @Override
    public void stopCompass(){
        // TODO: 02/05/2016
    }

    /**
    * Metodo che permette di fermare la navigazione
    */
    @Override
    public void stopNavigation(){
        // TODO: 02/05/2016
    }

    /**
     * Metodo che si occupa di settare il campo dati lastBeaconsSeen con la PriorityQueue<MyBeacon>
     *     contenente gli ultimi beacon rilevati e di aggiornare tutti I listeners con le ultime istruzioni di navigazione
     * @param context Contesto dell'applicazione
     * @param intent Intent contenente le informazioni del messaggio
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        lastBeaconsSeen.clear();
        PriorityQueue<MyBeacon> p = ((PriorityQueue<MyBeacon>)intent.getSerializableExtra("beacons"));

        setVisibleBeacon(p);
    }
}
