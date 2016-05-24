package com.leaf.clips.presenter;

/**
 * @author Federico Tavella
 * @version 0.10
 * @since 0.03
 */

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

    // TODO: 5/24/16 Aggiornare Asta + Tracy per i due campi privati sottostanti + OnDestroy()
    private AlertDialog dialogPathError;

    private AlertDialog.Builder builder;

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
        //poiId = savedInstanceState.getInt("poi_id");
        view = new NavigationViewImp(this);
                ((MyApplication) getApplication()).getInfoComponent().inject(this);

        if(savedInstanceState != null){
            poiId = savedInstanceState.getInt("poi_id");
            Log.d("LOADED_POI_ID", Integer.toString(poiId));
            handleIntent(getIntent());
        }
        else
            handleIntent(getIntent());
        Log.i("state%", "ONCREATE" + poiId);

       /* ConnectivityManager connectivityManager =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle(R.string.no_connection_title_alert_help);
            alertBuilder.setMessage(R.string.no_connection_message_alert_help);
            alertBuilder.setIcon(android.R.drawable.ic_dialog_alert);
            alertBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            });
            alertBuilder.create().show();
        }*/
        new NoInternetAlert().showIfNoConnection(this);
        builder = new AlertDialog.Builder(this);

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
                navigationManager.addListener(this);
                view.setInstructionAdapter(navigationInstruction);
            }else{
                view.noResult();
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
        stopNavigation();
        Log.i("Removelistener", "pathError");
        if(!isFinishing()){
            builder.setTitle("Percorso errato");
            builder.setMessage("Sembra che tu abbia sbagliato percorso, vuoi ricalcolare il percorso?");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("Ricalcolo",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                List<PointOfInterest> poiList = (List<PointOfInterest>) informationManager.getBuildingMap().getAllPOIs();
                                PointOfInterest destinationPoi = null;
                                boolean found = false;
                                //Trova il POI all'id scelto
                                for (ListIterator<PointOfInterest> i = poiList.listIterator(); i.hasNext() && !found; ) {
                                    PointOfInterest poi = i.next();
                                    if (poi.getId() == poiId) {
                                        destinationPoi = poi;
                                        found = true;
                                    }
                                }
                                navigationManager.startNavigation(destinationPoi);
                                navigationInstruction = navigationManager.getAllNavigationInstruction();
                                navigationManager.addListener(NavigationActivity.this);
                                view.setInstructionAdapter(navigationInstruction);
                            } catch (NoBeaconSeenException e){}
                            catch (NavigationExceptions e){}
                        }
                    });
            builder.setNegativeButton("Torna alla home",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            NavigationActivity.this.startActivity(new Intent(NavigationActivity.this, HomeActivity.class));
                        }
                    });
            if (dialogPathError == null)
                dialogPathError = builder.create();
            dialogPathError.show();
        }

    }

    /**
     * Metodo che viene invocato ogni volta che il sistema rileva un beacon durante la navigazione
     * @param info: istruzioni di navigazione utili per attraversare il prossimo arco.
     */
    public void informationUpdate(ProcessedInformation info){
        Log.i("informationUpdate", info.getProcessedBasicInstruction());
        int i = 0;
        boolean found = false;
        while (i<navigationInstruction.size() && !found) {
            if (info.getProcessedBasicInstruction().equals(navigationInstruction.get(i)
                    .getProcessedBasicInstruction()))
                found = true;
            else
                i++;
        }
        Log.i("informationUpdate", i + "");
        //view.refreshInstructions(i);
        if(found)
            view.refreshInstructions(i);
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
        intent.putStringArrayListExtra("photo_uri", uris);
        intent.putExtra("poi_id", poiId);
        startActivity(intent);
    }

    /**
     * Metodo che interrompe la navigazione in corso.
     */
    public void stopNavigation(){
        Log.i("Removelistener", "stopNavigation");
        navigationManager.removeListener(this);
        navigationManager.stopNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        builder = new AlertDialog.Builder(this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        poiId = savedInstanceState.getInt("poi_id");
        Log.i("state%", "ONRESTOREINSTANCESTATE" + poiId);
        // Restore state members from saved instance
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

    }

    /**
     * Salva lo stato corrente dell'Activity, in modo da poterlo ripristinare in caso di
     * interruzione coatta della stessa.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("poi_id", poiId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(dialogPathError != null)
            dialogPathError.dismiss();
    }
}
