package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.02
 * @since 0.00
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.R;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.view.DetailedInformationView;
import com.leaf.clips.view.DetailedInformationViewImp;

import java.util.ArrayList;

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
        view = new DetailedInformationViewImp(this);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        ArrayList<String> uris = getIntent().getStringArrayListExtra("photo_uri");

        imgListFragment = ImageListFragment.newInstance(uris);

        updatePhoto();
        updateDetailedDescription();
    }

    /**
     * Aggiorna la sezione della UI contente le immagini del prossimo ROI da raggiungere.
     */
    public void updatePhoto(){
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,imgListFragment).commit();
    }

    /**
     * Aggiorna la sezione della UI contente i passi dettagliati da compiere per raggiungere il
     * prossimo ROI.
     */
    public void updateDetailedDescription(){
        String detailedInfo = getIntent().getStringExtra("detailed_info");
        view.setDetailedDescription(detailedInfo);
    }
}
