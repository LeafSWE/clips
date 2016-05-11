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
import android.widget.ListView;

import com.leaf.clips.R;
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

import org.jgrapht.Graph;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class NavigationActivity extends AppCompatActivity implements NavigationListener {

    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal model
     */
    //TODO: Serve NavigationManager!!
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
        BuildingMap map = null;
        try {
            map = informationManager.getBuildingMap();
        } catch (NoBeaconSeenException e) {
            e.printStackTrace();
        }
        handleIntent(getIntent());
        MapGraph graph = navigationManager.getGraph();
        graph.addAllRegions(map.getAllROIs());
        graph.addAllEdges(map.getAllEdges());

        int destinationPOIid = getIntent().getIntExtra("poi_id",-1);
        List<PointOfInterest> poiList = null;
        PointOfInterest destinationPoi = null;
        //TODO retrieve path instruction
        try {
            try {
                poiList = (List<PointOfInterest>)informationManager.getBuildingMap().getAllPOIs();
            } catch (NoBeaconSeenException e) {
                e.printStackTrace();
            }
            for(PointOfInterest poi : poiList){
                if(poi.getId() == destinationPOIid){
                    destinationPoi = poi;
                    break;
                }
            }
            navigationManager.startNavigation(destinationPoi);
            List<ProcessedInformation> navigationInstruction = navigationManager.getAllNavigationInstruction();
            NavigationAdapter adp = new NavigationAdapter(this, navigationInstruction);
            adp.notifyDataSetChanged();
            ListView listView = (ListView) findViewById(R.id.view_instruction_list);
            listView.setAdapter(adp);

        } catch (NavigationExceptions navigationExceptions) {
            navigationExceptions.printStackTrace();
        }


    }

    //TODO: aggiornare documentazione se test Search ok
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

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
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
