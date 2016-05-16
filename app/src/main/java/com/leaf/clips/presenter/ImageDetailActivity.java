package com.leaf.clips.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.leaf.clips.view.ImageDetailView;
import com.leaf.clips.view.ImageDetailViewImp;

import java.util.List;

/**
 * Una ImageDetailActivity si occupa di mostrare a schermo intero le immagini relative ad una
 * certa istruzione.
 */
public class ImageDetailActivity extends AppCompatActivity {

    /**
     * Riferimento alla VIew associata.
     */
    private ImageDetailView view;

    /**
     * Riferimento alla lista di URI delle immagini associate all'istruzione scelta.
     */
    private List<String> listPhotos;

    /**
     * Riferimento alla prima immagine da visualizzare nello slideshow.
     */
    private int startItem;

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
        view = new ImageDetailViewImp(this);

        listPhotos = getIntent().getStringArrayListExtra("photo_uris");
        startItem = getIntent().getIntExtra("image_selected",-1);

        view.setAdapter(listPhotos.size());
    }

    /**
     * Restituisce la lista di immagini.
     * @return la lista di immagini.
     */
    public List<String> getListPhotos() {
        return listPhotos;
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.i("state%", "onNavigateup");
        onBackPressed();
        return true;
    }
}
