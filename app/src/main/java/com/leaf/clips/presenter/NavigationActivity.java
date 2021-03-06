package com.leaf.clips.presenter;

/**
 * @author Federico Tavella
 * @version 0.10
 * @since 0.03
 */

import android.app.SearchManager;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;

import com.leaf.clips.R;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationListener;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.NoBeaconSeenException;
import com.leaf.clips.model.compass.Compass;
import com.leaf.clips.model.compass.CompassListener;
import com.leaf.clips.model.navigator.NavigationDirection;
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
public class NavigationActivity extends AppCompatActivity implements NavigationListener, CompassListener {

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
     * Riferimento alla classe che si occupa del recupero dei dati della bussola
     */
    @Inject
    Compass compass;

    /**
     * Riferimento alla lista di istruzioni di navigazione.
     */
    private  List<ProcessedInformation> navigationInstruction;

    /**
     * Id del POI che l'utente ha indicato come destinazione.
     */
    private int poiId;

    /**
     * Alert che viene mostrato nel caso un cui l'utente sbagli percorso
     */
    private AlertDialog dialogPathError;

    /**
     * Oggetto che si occupa della creazione dell'alert di avviso in caso di percorso errato
     */
    private AlertDialog.Builder builder;

    /**
     * Alert che viene mostrato nel caso in cui non sia presente internet
     */
    private AlertDialog noInternetConnection;

    /**
     * Informazione attuale mostrata
     */
    private ProcessedInformation actualInformation;

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
        MyApplication.getInfoComponent().inject(this);

        if(savedInstanceState != null){
            poiId = savedInstanceState.getInt("poi_id");
            Log.d("LOADED_POI_ID", Integer.toString(poiId));
            handleIntent(getIntent());
        }
        else
            handleIntent(getIntent());
        Log.i("state%", "ONCREATE" + poiId);
        if (noInternetConnection == null || !noInternetConnection.isShowing())
            noInternetConnection = new NoInternetAlert().showIfNoConnection(this);
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
        List<PointOfInterest> poiList;
        poiId=-1;
        try {
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
                int destinationPOIid = getIntent().getIntExtra("poi_id", -1);
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
            if (destinationPoi != null) {
                setTitle("Raggiungi " + destinationPoi.getName());
                Log.d("NAVIGAZIONE", "OK");
                if (noInternetConnection == null || !noInternetConnection.isShowing())
                    noInternetConnection = new NoInternetAlert().showIfNoConnection(this);
                final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //Abilita Bluetooth se disabilitato
                if (!mBluetoothAdapter.isEnabled()) {

                    builder.setTitle(R.string.dialog_title_bluetooth_not_enabled)
                            .setMessage(R.string.dialog_bluetooth_not_enabled)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mBluetoothAdapter.enable();
                                }
                            })
                            .setNegativeButton(R.string.back_to_home, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    NavigationActivity.this.startActivity(new
                                            Intent(NavigationActivity.this, HomeActivity.class));
                                }
                            });
                    if (!isFinishing()) {
                        AlertDialog bluetoothDialog = builder.create();
                        bluetoothDialog.setCanceledOnTouchOutside(false);
                        bluetoothDialog.show();
                    }
                }
                navigationManager.startNavigation(destinationPoi);
                navigationInstruction = navigationManager.getAllNavigationInstruction();
                navigationManager.addListener(this);
                view.setInstructionAdapter(navigationInstruction);
                compass.addListener(this);

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
            builder.setTitle(R.string.wrong_path);
            builder.setMessage(R.string.wrong_path_question);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton(R.string.recalculate,
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
                                actualInformation = navigationInstruction.get(0);
                                navigationManager.addListener(NavigationActivity.this);
                                view.setInstructionAdapter(navigationInstruction);
                            }catch (NavigationExceptions e){
                                e.printStackTrace();
                            }
                            catch (NoBeaconSeenException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            builder.setNegativeButton(R.string.back_to_home,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            NavigationActivity.this.startActivity(new Intent(NavigationActivity.this, HomeActivity.class));
                        }
                    });
            if (dialogPathError == null) {
                dialogPathError = builder.create();
                dialogPathError.setCanceledOnTouchOutside(false);
            }
            if(!isFinishing())
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
            if ((info.equals(navigationInstruction.get(i))))
                found = true;
            else
                i++;
        }
        Log.i("informationUpdate", i + "");
        if(found) {
            view.refreshInstructions(i);
            actualInformation = info;
        }
    }

    /**
     * Metodo che avvia l'Activity deputata a spiegare l'istruzione selezionata dall'utente con
     * maggior dettaglio.
     * @param instructionPosition la posizione, nella lista, dell'istruzione selezionata
     */
    public void showDetailedInformation(int instructionPosition){
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
        view.refreshInstructions();
    }

    /**
     * Metodo che viene invocato al Resume dell'activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        builder = new AlertDialog.Builder(this);
    }

    /**
     * Metodo che viene invocato quando viene ripristinato lo stato dell'Activity
     * @param savedInstanceState Stato precedente dell'activity
     */
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

    /**
     * Metodo che viene invocato alla distruzione dell'Activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(dialogPathError != null)
            dialogPathError.dismiss();

        if(noInternetConnection != null)
            noInternetConnection.dismiss();
    }

    /**
     * Metodo che viene invocato al cambio di gradazione rilevato dalla bussola
     * @param orientation Gradazione rilevata dalla bussola
     */
    @Override
    public void changed(float orientation) {
        if (actualInformation != null) {
            int orientationInt = (int) orientation;
            int calculateOrientation = actualInformation.getCoordinate();
            if (calculateOrientation >= 0) {
                orientationInt = orientationInt - calculateOrientation;
            }
            if (orientationInt < 0) {
                orientationInt += 360;
            }
            NavigationDirection direction;
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            switch (rotation) {
                case Surface.ROTATION_90:
                    orientationInt += 90;
                    break;
                case Surface.ROTATION_180:
                    orientationInt += 180;
                    break;
                case Surface.ROTATION_270:
                    orientationInt += 270;
                    break;
            }
            if (orientationInt > 360)
                orientationInt -= 360;
            if (orientationInt > 20 && orientationInt < 150)
                direction = NavigationDirection.LEFT;
            else if (orientationInt >= 210 && orientationInt < 340)
                direction = NavigationDirection.RIGHT;
            else if (orientationInt >= 150 && orientationInt < 210)
                direction = NavigationDirection.TURN;
            else
                direction = NavigationDirection.STRAIGHT;
            view.updateArrow(direction);
        }
    }
}
