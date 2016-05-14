package com.leaf.clips.presenter;

/**
 * @author Federico Tavella
 * @version 0.05
 * @since 0.04
 *
 */

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.leaf.clips.R;
import com.leaf.clips.model.InformationListener;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NoBeaconSeenException;
import com.leaf.clips.view.HomeView;
import com.leaf.clips.view.HomeViewImp;

import java.util.List;

import javax.inject.Inject;

/**Una HomeActivity funge da Presenter e si occupa di recuperare i dati dal Model e formattarli cosicchè siano pronti
 * per essere mostrati all'utente attraverso una classe che implementi {@link HomeView}.
 * @see <a href="http://tinyurl.com/pjuc3bd">AppCompatActivity</a>
 * @see InformationListener
 */
public class HomeActivity extends AppCompatActivity implements InformationListener{
    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal Model
     */
    @Inject
    InformationManager informationManager;

    /**
     * View associata a questa Activity
     */
    private HomeView view;

    boolean dialogChoice;

    /**
     *Chiamato quando si sta avviando l'activity. Questo metodo si occupa di inizializzare
     * i campi dati.
     *@param savedInstanceState se l'Actvity viene re-inizializzata dopo essere stata chiusa, allora
     *                           questo Bundle contiene i dati più recenti forniti al metodo
     *                           <a href="http://tinyurl.com/acaw22p">onSavedInstanceState(Bundle)</a>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyApplication)getApplication()).getInfoComponent().inject(this);
        dialogChoice = false;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        view = new HomeViewImp(this,fragmentManager);

        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M ){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            0);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
    }

    /**
     * Recupera le informazioni dell'edificio dal database ed utilizza la View associata per
     * mostrarle all'utente.
     */
    @Override
    protected void onResume() {
        super.onResume();

        informationManager.addListener(this);

        try {
            informationManager.getBuildingMap();
            onDatabaseLoaded();
        } catch (NoBeaconSeenException e) {
            e.printStackTrace();
        }
    }

    /**
     * Si occupa di controllare che Bluetooth e servizi di Localizzazione siano attivati sul
     * dispositivo.
     */
    @Override
    protected void onStart() {
        super.onStart();
        checkBluetoothConnection();
    }

    /**
     * Controlla che la connettività Bluetoooth sia attiva. In caso negativo domanda il permesso di
     * attivarla.
     */
    public void checkBluetoothConnection(){
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Abilita Bluetooth se disabilitato
        if (!mBluetoothAdapter.isEnabled()) {

            builder.setTitle(R.string.dialog_title_bluetooth_not_enabled)
                    .setMessage(R.string.dialog_bluetooth_not_enabled)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mBluetoothAdapter.enable();
                            checkLocationService();
                        }
                    });

            builder.create().show();
        }
    }

    /**
     * Controlla che i servizi di Localizzazione siano attivi. In caso negativo chiede all'utente di
     * attivarli.
     */
    public void checkLocationService(){
        // Get Location Manager and check for GPS & Network location services
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            builder.setTitle(R.string.dialog_title_position_not_enabled)
                    .setMessage(R.string.dialog_position_not_enabled)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Show location settings when the user acknowledges the alert dialog
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });
            builder.create().show();
        }
    }

    /**
     * Gestisce il tap dell'utente sul tasto Back. Implementato in modo che chiuda il Drawer se
     * questo è aperto.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        if(drawer != null){
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    /**
     * Avvia {@link NearbyPoiActivity}.
     */
    public void showExplorer(){
        Intent intent = new Intent(this, NearbyPoiActivity.class);
        startActivity(intent);
    }

    /**
     * Avvia {@link PoiCategoryActivity}.
     * @param categoryName Nome della categoria di POI alla quale si vuole accedere.
     */
    public void showPoisCategory(String categoryName){
        Intent intent = new Intent(this, PoiCategoryActivity.class);
        intent.putExtra("category_name", categoryName);
        startActivity(intent);
    }

    /**
     * Recupera dal Model l'indirizzo dell'edificio rilevato e aggiorna la {@link HomeView} con tale
     * informazione.
     */
    public void updateBuildingAddress(){
        try {
            String address = informationManager.getBuildingMap().getAddress();
            view.setBuildingAddress(address);
        } catch (NoBeaconSeenException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera dal Model la descrizione dell'edificio rilevato e aggiorna la {@link HomeView} con tale
     * informazione.
     */
    public void updateBuildingDescription(){
        try {
            String desc = informationManager.getBuildingMap().getDescription();
            Log.d("DESC", desc);
            view.setBuildingDescription(desc);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Recupera dal Model il nome dell'edificio rilevato e aggiorna la {@link HomeView} con tale
     * informazione.
     */
    public void updateBuildingName(){
        try {
            String name = informationManager.getBuildingMap().getName();
            view.setBuildingName(name);
        } catch (NoBeaconSeenException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera dal Model le ore di apertura al pubblico dell'edificio rilevato e aggiorna la
     * {@link HomeView} con tale informazione.
     */
    public void updateBuildingOpeningHours(){
        try {
            String hours = informationManager.getBuildingMap().getOpeningHours();
            view.setBuildingOpeningHours(hours);
        } catch (NoBeaconSeenException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera dal Model le categorie di POI presenti nell'edificio rilevato e aggiorna la
     * {@link HomeView} con tale informazione.
     */
    public void updatePoiCategoryList(){
        List<String> categories = (List<String>) informationManager.getAllCategories();
        view.setPoiCategoryListAdapter(categories);
    }

    /**
     * Invocato una volta che il caricamento del database locale è avvenuto con successo.
     * Si occupa di aggiornare tutti le informazioni che la {@link HomeView} si propone di mostrare.
     */
    @Override
    public void onDatabaseLoaded() {
        //Imposta il fragment vuoto, come base del layout
        CompleteHomeFragment completeHomeFragment = new CompleteHomeFragment();
        List<Fragment> fragments= getSupportFragmentManager().getFragments();
        if(fragments == null ){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.linear_layout_home, completeHomeFragment, "COMPLETE_FRAGMENT")
                    .addToBackStack("COMPLETE_FRAGMENT")
                    .commitAllowingStateLoss();
            getSupportFragmentManager().executePendingTransactions();

            updateBuildingAddress();
            updateBuildingName();
            updateBuildingDescription();
            updateBuildingOpeningHours();
            updatePoiCategoryList();
        }
    }

    /**
     * @inheritDoc
     * @return TRUE se l'utente concede il permesso di effettuare il download della mappa dal
     * server remoto, FALSE altrimenti.
     */
    @Override
    public boolean onLocalMapNotFound() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title_map_not_found)
                .setMessage(R.string.dialog_map_not_found)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialogChoice = true;
                    }
                })
                .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialogChoice = false;
                    }
                });

        builder.create().show();
        return dialogChoice;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onRemoteMapNotFound() {
        Log.d("HOMEACTIVITY", "REMOTE MAP NOT FOUND");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void cannotRetrieveRemoteMapDetails() {
        Log.d("HOMEACTIVITY", "CAN'T RETRIEVE REMOTE DETAILS");
    }

    /**
     * @inheritDoc
     * @return TRUE se l'utente concede il permesso di aggiornare la mappa all'ultima versione
     * della mappa dal server remoto, FALSE altrimenti.
     */
    @Override
    public boolean noLastMapVersion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title_not_updated_map)
                .setMessage(R.string.dialog_not_updated_map)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialogChoice = true;
                    }
                })
                .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialogChoice = false;
                    }
                });

        builder.create().show();
        return dialogChoice;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        informationManager = null;
    }

    /**
     * Avvia l'Activity deputata a gestire la guida utente.
     */
    public void showHelp(){
        // TODO: 5/3/16
    }

    /**
     * Avvia l'Activity deputata a gestire le mappe salvate sul dispositivo.
     */
    public void showLocalMaps(){
        // TODO: 5/3/16
    }

    /**
     * Avvia l'Activity deputata a gestire le mappe salvate sul dispositivo.
     */
    public void showPreferences(){
        // TODO: 5/3/16
    }

    /**
     * Associa una lista di suggerimenti alla ricerca della destinazione.
     */
    public void enableSuggestions(){
        // TODO: 5/3/16
    }
}
