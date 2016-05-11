package com.leaf.clips.presenter;

/**
 * @author Federico Tavella
 * @version 0.05
 * @since 0.03
 */

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationListener;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.NoBeaconSeenException;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.NavigationExceptions;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.view.NavigationView;
import com.leaf.clips.view.NavigationViewImp;

import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;

public class NavigationActivity extends AppCompatActivity implements NavigationListener {

    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal model
     */

    @Inject
    InformationManager informationManager;
    @Inject
    NavigationManager navigationManager;

    private NavigationView view;
    private NavigationAdapter navigationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new NavigationViewImp(this);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        //Prova a recuperare la mappa dell'edificio in memoria
        BuildingMap map = null;
        try {
            map = informationManager.getBuildingMap();
        } catch (NoBeaconSeenException e) {
            e.printStackTrace();
        }

        MapGraph graph = navigationManager.getGraph();
        graph.addAllRegions(map.getAllROIs());
        graph.addAllEdges(map.getAllEdges());

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    /**
     * Metodo che gestisce l'Intent lanciato dalla ricerca nominativa di un POI.
     * @param intent: Intent attraverso il quale è stata creata la corrente istanza di
     *              NavigationActivity.
     */
    private void handleIntent(Intent intent) {
        PointOfInterest destinationPoi = null;
        List<PointOfInterest> poiList = null;
        try {
            //TODO: Introdurre suggerimenti nella SearchBox, altrimenti l'app si spacca se query non corretta
            poiList = (List<PointOfInterest>)informationManager.getBuildingMap().getAllPOIs();

            //Se l'Intent è stato generato dalla SearchBox
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String destinationPoiName = intent.getStringExtra(SearchManager.QUERY);

                boolean found = false;
                //Trova il POI corrispondente al nome digitato
                for(ListIterator<PointOfInterest> i = poiList.listIterator(); i.hasNext() && !found;){
                    PointOfInterest poi = i.next();
                    if(poi.getName().equals(destinationPoiName)){
                        destinationPoi = poi;
                        found = true;
                    }
                }

            }else { //Se l'Intent è stato generato dalla PoiCategoryActivity
                int destinationPOIid = getIntent().getIntExtra("poi_id",-1);

                boolean found = false;
                //Trova il POI all'id scelto
                for(ListIterator<PointOfInterest> i = poiList.listIterator(); i.hasNext() && !found;){
                    PointOfInterest poi = i.next();
                    if(poi.getId() == destinationPOIid){
                        destinationPoi = poi;
                        found = true;
                    }
                }
            }

            navigationManager.startNavigation(destinationPoi);
            List<ProcessedInformation> navigationInstruction = navigationManager.getAllNavigationInstruction();

            view.setInstructionAdapter(navigationInstruction);
        } catch (NoBeaconSeenException e) {
            e.printStackTrace();
        } catch (NavigationExceptions navigationExceptions) {
            navigationExceptions.printStackTrace();
        }
    }

    /**
     * Metodo che lancia un avviso in caso il sistema rilevi che l'utente è uscito dal percorso
     * consigliato.
     */
    public void pathError(){
        //TODO
    }

    /**
     * Metodo che viene invocato ogni volta che il sistema rileva un beacon durante la navigazione
     * @param info: istruzioni di navigazione utili per attraversare il prossimo arco.
     */
    public void informationUpdate(ProcessedInformation info){
        //TODO
    }

    /**
     * Metodo che avvia l'Activity deputata a spiegare l'istruzione selezionata dall'utente con
     * maggior dettaglio.
     * @param instructionPosition la posizione, nella lista, dell'istruzione selezionata
     */
    public void showDetailedInformation(int instructionPosition){
        //TODO
    }

    /**
     * Metodo che interrompe la navigazione in corso.
     */
    public void stopNavigation(){
        //TODO
    }
}
