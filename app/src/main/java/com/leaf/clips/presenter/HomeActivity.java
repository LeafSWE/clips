package com.leaf.clips.presenter;


/**
 * @author Federico Tavella
 * @version 0.05
 * @since 0.04
 *
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.leaf.clips.R;
import com.leaf.clips.model.AbsBeaconReceiverManager;
import com.leaf.clips.model.InformationListener;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NoBeaconSeenException;
import com.leaf.clips.view.HomeView;
import com.leaf.clips.view.HomeViewImp;

import org.altbeacon.bluetooth.BluetoothCrashResolver;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements InformationListener{
    /**
     *  TODO
     Enable Suggestion
     */

    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal model
     */
    @Inject
    InformationManager informationManager;

    /**
     * View associata a tale Activity
     */
    private HomeView view;

    BluetoothCrashResolver bluetoothCrashResolver ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        view = new HomeViewImp(this);
        bluetoothCrashResolver = new BluetoothCrashResolver(this);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);
        informationManager.addListener(this);
        // TODO: 11/05/2016 rimuovere gli update dal costruttore e tenerli onDatabaseLoaded 
       /* updateBuildingAddress();
        updateBuildingName();
        updateBuildingDescription();
        updateBuildingOpeningHours();
        updatePoiCategoryList();*/
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
        //((AbsBeaconReceiverManager)informationManager).stopService();
        informationManager.addListener(this);
    }

    /**
     * Chiude il Drawer se utente esegue tap sul tasto Back.
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
     * Metodo che viene invocato a seguito della richiesta di visualizzazione della modalità esplora
     * @return  void
     */
    public void showExplorer(){
        Intent intent = new Intent(this, NearbyPoiActivity.class);
        startActivity(intent);
    }

    /**
     * Metodo che viene invocato a seguito della richiesta di visualizzazione della guida
     * @return  void
     */
    public void showHelp(){
        // TODO: 5/3/16  codify
    }

    /**
     * Metodo che viene invocato a seguito della richiesta di visualizzazione della mappe salvate nel database locale
     * @return  void
     */
    public void showLocalMaps(){
        // TODO: 5/3/16  
    }

    /**
     * Metodo che viene invocato a seguito della richiesta di visualizzazione di tutti i POI appartenenti ad un certa categoria
     * @param categoryName Nome della categoria di cui visualizzare l'insieme di POI appartenente
     * @return  void
     */
    public void showPoisCategory(String categoryName){
        Intent intent = new Intent(this, PoiCategoryActivity.class);
        intent.putExtra("category_name", categoryName);
        startActivity(intent);
    }

    /**
     * Metodo che viene invocato a seguito della richiesta di visualizzazione delle preferenze dell'utente
     * @return  void
     */
    public void showPreferences(){
        // TODO: 5/3/16  
    }

    /**
     * Metodo che viene invocato a seguito della richiesta di inizio della navigazione
     * @param poiPosition Identificativo del POI verso il quale si vuole effettuare una navigazione
     * @return  void
     */
    public void startNavigation(int poiPosition){
        //TODO: 5/3/16
    }

    /**
     * Metodo che recupera l'indirizzo dell'edificio e lo passa alla View corrispondente
     * @return  void
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
     * Metodo che recupera la descrizione dell'edificio e lo passa alla View corrispondente
     * @return  void
     */
    public void updateBuildingDescription(){
        try {
            String desc = informationManager.getBuildingMap().getDescription()+"Versione Davide";
            Log.d("DESC",desc);
            view.setBuildingDescription(desc);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo che recupera il nome dell'indirizzo dell'edificio e lo passa alla View corrispondente
     * @return  void
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
     * Metodo che recupera l'orario di apertura dell'edificio e lo passa alla View corrispondente
     * @return  void
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
     * Metodo che recupera la lista di categorie di POI nell'edificio e lo passa alla View corrispondente
     * @return  void
     */
    public void updatePoiCategoryList(){
        List<String> categories = (List<String>) informationManager.getAllCategories();
        view.setPoiCategoryListAdapter(categories);
    }

    /**
     * Metodo che permette di attivare la lista dei possibili POI raggiungibili a partire da una stringa
     * @return  void
     */
    public void enableSuggestions(){
        // TODO: 5/3/16  
    }

    @Override
    public void onDatabaseLoaded() {
        updateBuildingAddress();
        updateBuildingName();
        updateBuildingDescription();
        updateBuildingOpeningHours();
        updatePoiCategoryList();
    }

    @Override
    public boolean onLocalMapNotFound() {
        Log.d("HOMEACTIVITY", "LOCAL MAP NOT FOUND");
        return true;
    }

    @Override
    public void onRemoteMapNotFound() {
        Log.d("HOMEACTIVITY", "REMOTE MAP NOT FOUND");
    }

    @Override
    public void cannotRetrieveRemoteMapDetails() {
        Log.d("HOMEACTIVITY", "CAN'T RETRIEVE REMOTE DETAILS");
    }

    @Override
    public boolean noLastMapVersion() {
        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        ((AbsBeaconReceiverManager)informationManager).stopService();
        informationManager = null;

    }
}
