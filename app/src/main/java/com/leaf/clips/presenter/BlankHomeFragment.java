package com.leaf.clips.presenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leaf.clips.R;

/**
 * Un BlankHomeFragment viene mostrato dalla HomeActivity in caso di errori nel processo di
 * recupero delle informazioni dell'edificio.
 * Alcuni esempi di situazioni erronee sono: Bluetooth non attivo, geolocalizzazione non attiva,
 * nessun beacon conosciuto rilevato, beacon conosciuto rilevato ma nessuna connessione internet
 * presente (e conseguente impossibilità di scaricare la mappa dell'edificio rilevato).
 */
public class BlankHomeFragment extends Fragment {

    /**
     * Costruttore di default. Richiesto dalla documentazione di {@link Fragment}.
     */
    public BlankHomeFragment() {}

    /** Chiamato per fare in modo che il Fragment istanzi la propria UI.
     * Si occupa dell'infalte
     * @param inflater oggetto {@link LayoutInflater} usato per eseguire l'inflate di qualsiasi view
     *                 nel fragment.
     * @param container la parent view a cui è associato il fragment.
     * @param savedInstanceState l'eventuale stato precedentemente salvato del fragment.
     * @return la View corrispondente alla UI del fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_blank_home, container, false);
    }

}
