package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.02
 * @since 0.00
 */

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.leaf.clips.R;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.view.DetailedInformationView;
import com.leaf.clips.view.DetailedInformationViewImp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Una DetailedInformationActivity si occupa di gestire le informazioni dettagliate relative ad una
 * certa istruzione di navigazione.
 * @see AppCompatActivity
 */
public class DetailedInformationActivity extends AppCompatActivity {

    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal Model.
     */
    @Inject
    InformationManager informationManager;

    /**
     * Riferimento utilizzato per accedere alle istruzioni di navigazione trattate dal Model.
     */
    @Inject
    NavigationManager navigationManager;

    /**
     * Riferimento alla relativa View.
     */
    private DetailedInformationView view;

    /**
     * Riferimento alle istruzioni di navigazione arricchite con informazioni di contesto.
     */
    private ProcessedInformation processedInfo;

    /**
     * Riferimento al {@link ImageListFragment} contentente lo slideshow delle immagini relative al
     * prossimo ROI da raggiungere.
     */
    private ImageListFragment imgListFragment;

    /**
     * Chiamato quando si sta avviando l'activity. Questo metodo si occupa di inizializzare i
     * campi dati.
     * @param savedInstanceState se l'Actvity viene re-inizializzata dopo essere stata chiusa, allora
     *                           questo Bundle contiene i dati più recenti forniti al metodo
     *                           <a href="http://tinyurl.com/acaw22p">onSavedInstanceState(Bundle)</a>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("POI_ID", getIntent().getIntExtra("poi_id", -1) + "");
        savedInstanceState = new Bundle();
        savedInstanceState.putSerializable("poi_id", getIntent().getIntExtra("poi_id", -1));
        view = new DetailedInformationViewImp(this);
        MyApplication.getInfoComponent().inject(this);

        ArrayList<String> uris = getIntent().getStringArrayListExtra("photo_uri");

        imgListFragment = ImageListFragment.newInstance(uris);

        updatePhoto();
        updateDetailedDescription();
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation with older versions
     * of the platform, at the point of this call the fragments attached to the activity are
     * <em>not</em> resumed.  This means that in some cases the previous state may still be saved,
     * not allowing fragment transactions that modify the state.  To correctly interact with
     * fragments in their proper state, you should instead override {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(checkConnection()){
            updatePhoto();
        }
    }

    /**
     * Aggiorna la sezione della UI contente le immagini del prossimo ROI da raggiungere.
     */
    public void updatePhoto(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments == null || fragments.isEmpty())
            fragmentManager.beginTransaction().add(R.id.fragment_container, imgListFragment).commit();
        else
            fragmentManager.beginTransaction().replace(R.id.fragment_container, imgListFragment).commit();
    }

    /**
     * Aggiorna la sezione della UI contente i passi dettagliati da compiere per raggiungere il
     * prossimo ROI.
     */
    public void updateDetailedDescription(){
        String detailedInfo = getIntent().getStringExtra("detailed_info");
        view.setDetailedDescription(detailedInfo);
    }

    /**
     * Controlla che sia disponibile una connessione ad Internet.
     * @return true se e solo se è disponibile una connessione ad Internet.
     */
    //TODO:astah
    private boolean checkConnection(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (!(networkInfo != null && networkInfo.isConnected())) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle(R.string.no_connection_title_alert_help);
            alertBuilder.setMessage(R.string.no_connection_photo_alert);
            alertBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            });
            alertBuilder.create().show();
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Metodo che viene invocato alla pressione del tasto indietro in alto
     * @return true
     */
    @Override
    //TODO:astah
    public boolean onSupportNavigateUp() {
        Log.i("state%", "onNavigateup");
        onBackPressed();
        return true;
    }
}
