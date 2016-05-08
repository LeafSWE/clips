package com.leaf.clips.model;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 *
 *
 */

import com.leaf.clips.model.navigator.NavigationExceptions;
import com.leaf.clips.model.navigator.NoNavigationInformationException;
import com.leaf.clips.model.navigator.PathException;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;

import java.util.List;

/**
 *Interfaccia che si occupa di esporre tutti i metodi utili alla navigazione
 */

public interface NavigationManager { 

    /**
    * Metodo che permette di registrare un listener
    * @param listener Listener che deve essere aggiunto alla lista di NavigationListener
    * @return  void
    */
    void addBeaconListener(NavigationListener listener);

    /**
    * Metodo che permette di recuperare tutte le istruzioni di navigazione per un percorso
     * calcolato. Viene lanciata una eccezione di tipo NoNavigationInformationException nel caso
     * in cui venga richiamato questo metodo senza aver prima avviato la navigazione
    * @return  List<ProcessedInformation>
    */
    List<ProcessedInformation> getAllNavigationInstruction() throws NavigationExceptions;

    /**
    * Metodo che permette di recuperare tutte le istruzioni di navigazione per un percorso calcolato
     * in base al beacon più potente ricavato dalla PriorityQueue<MyBeacon> passata come argomento.
     * Viene lanciata una eccezione di tipo NoNavigationInformationException nel caso in cui venga
     * richiamato questo metodo senza aver prima avviato la navigazione.
    * @return  ProcessedInformation
    */
    ProcessedInformation getNextInstruction() throws NoNavigationInformationException;

    /**
    * Metodo che permette di rimuovere un listener
    * @param listener Listener che deve essere rimosso dalla lista di NavigationListener
    * @return  void
    */
    void removeBeaconListener(NavigationListener listener);

    /**
    * Metodo che permette di attivare il rilevamento dei dati dalla bussola
    * @return  void
    */
    void startCompass();

    /**
    * Metodo che permette di avviare la navigazione verso uno specifico POI
    * @param endPOI POI da raggiungere tramite navigazione
    * @return  ProcessedInformation
    */
    ProcessedInformation startNavigation(PointOfInterest endPOI) throws NavigationExceptions;

    /**
    * Metodo che permette di fermare il rilevamento dei dati ottenuti dalla bussola
    * @return  void
    */
    void stopCompass();

    /**
    * Metodo che permette di fermare la navigazione
    * @return  void
    */
    void stopNavigation();

}

