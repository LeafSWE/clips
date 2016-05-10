package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.03
 * @since 0.00
 */

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.view.NavigationView;
import com.leaf.clips.view.NavigationViewImp;

import javax.inject.Inject;

public class NavigationActivity extends AppCompatActivity {

    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal model
     */
    //TODO: Serve NavigationManager!!
    @Inject
    InformationManager informationManager;

    private NavigationView view;
    private NavigationAdapter navigationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new NavigationViewImp(this);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        handleIntent(getIntent());

        //TODO retrieve path instruction
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
     *
     * @param info
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
