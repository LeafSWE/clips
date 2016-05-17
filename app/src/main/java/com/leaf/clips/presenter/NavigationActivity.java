package com.leaf.clips.presenter;

/**
 * @author Federico Tavella
 * @version 0.10
 * @since 0.03
 */

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationListener;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.NoBeaconSeenException;
import com.leaf.clips.model.navigator.NavigationExceptions;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;
import com.leaf.clips.view.NavigationView;
import com.leaf.clips.view.NavigationViewImp;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;

/**
 * Una NavigationActivity si occupa di recuperare e gestire le istruzioni di navigazione utili perchè
 * l'utente possa raggiungere la destinazione scelta.
 */
public class NavigationActivity extends AppCompatActivity implements NavigationListener {

    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal model
     */
    @Inject
    InformationManager informationManager;
    /**
     * Riferimento utilizzato per accedere alle istruzioni di navigazione.
     */
    @Inject
    NavigationManager navigationManager;
    /**
     * Riferimento alla relativa VIew.
     */
    private NavigationView view;
    /**
     * Riferimento alla lista di istruzioni di navigazione.
     */
    private  List<ProcessedInformation> navigationInstruction;
    /**
     * Id del POI che l'utente ha indicato come destinazione.
     */
    private int poiId;

    /**
     *Chiamato quando si sta avviando l'activity. Questo metodo si occupa di inizializzare
     * i campi dati.
     *@param savedInstanceState se l'Actvity viene re-inizializzata dopo essere stata chiusa, allora
     *                           questo Bundle contiene i dati più recenti forniti al metodo
     *                           <a href="http://tinyurl.com/acaw22p">onSavedInstanceState(Bundle)</a>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new NavigationViewImp(this);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        if(savedInstanceState != null){
            poiId = savedInstanceState.getInt("poi_id");
            Log.d("LOADED_POI_ID", Integer.toString(poiId));
            handleIntent(getIntent());
        }
        else
            handleIntent(getIntent());
    }

    /**
     * @inheritDoc
     * @param intent {@link Intent} con il quale è stata avviata al corrente Activity.
     */
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
        poiId=-1;
        try {
            //TODO: Introdurre suggerimenti nella SearchBox
            poiList = (List<PointOfInterest>)informationManager.getBuildingMap().getAllPOIs();

            //Se l'Intent è stato generato dalla SearchBox
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String destinationPoiName = intent.getStringExtra(SearchManager.QUERY);
                Log.d("RICERCA", "INIZIO");
                boolean found = false;
                //Trova il POI corrispondente al nome digitato
                for(ListIterator<PointOfInterest> i = poiList.listIterator(); i.hasNext() && !found;){
                    PointOfInterest poi = i.next();
                    if(poi.getName().toLowerCase().equals(destinationPoiName.toLowerCase())){
                        destinationPoi = poi;
                        found = true;
                    }
                }

            }else { //Se l'Intent è stato generato dalla PoiCategoryActivity
                int destinationPOIid = getIntent().getIntExtra("poi_id",-1);
                if(destinationPOIid != -1)
                    poiId = destinationPOIid;
                else
                    poiId = Integer.valueOf(getIntent().getDataString());
                Log.d("DEST_POI_ID", Integer.toString(poiId));

                    boolean found = false;
                    //Trova il POI all'id scelto
                    for(ListIterator<PointOfInterest> i = poiList.listIterator(); i.hasNext() && !found;){
                        PointOfInterest poi = i.next();
                        if(poi.getId() == poiId){
                            destinationPoi = poi;
                            found = true;
                        }
                    }
            }
            if(destinationPoi != null) {
                Log.d("NAVIGAZIONE", "OK");
                navigationManager.startNavigation(destinationPoi);
                navigationInstruction = navigationManager.getAllNavigationInstruction();
                view.setInstructionAdapter(navigationInstruction);
            }else{
                Toast.makeText(this,"Nessun Risultato",Toast.LENGTH_SHORT).show();
                Log.d("NAVIGAZIONE", "NESSUN RISULTATO");
            }

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
        ProcessedInformation information = navigationInstruction.get(instructionPosition);

        String detailedInformation = information.getDetailedInstruction();
        List<PhotoRef> photoRefs = (List<PhotoRef>) information.getPhotoInstruction().getPhotoInformation();
        ArrayList<String> uris = new ArrayList<>();
        for (PhotoRef ref:photoRefs) {
            String uri = ref.getPhotoUri().toString();
            uris.add(uri);
        }

        Intent intent = new Intent(this,DetailedInformationActivity.class);
        intent.putExtra("detailed_info",detailedInformation);
        intent.putStringArrayListExtra("photo_uri",uris);
        startActivity(intent);
    }

    /**
     * Metodo che interrompe la navigazione in corso.
     */
    public void stopNavigation(){
        //TODO
    }

    /**
     * Salva lo stato corrente dell'Activity, in modo da poterlo ripristinare in caso di
     * interruzione coatta della stessa.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int poiId = getIntent().getIntExtra("poi_id",-1);
        Log.d("SAVED_POI_ID", Integer.toString(poiId));
        if(poiId != -1)
            outState.putInt("poi_id",poiId);
    }
}
