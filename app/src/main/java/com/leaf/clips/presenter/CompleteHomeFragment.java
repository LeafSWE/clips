package com.leaf.clips.presenter;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.leaf.clips.R;

import java.util.List;

/**
 * Un CompleteHomeFragment viene mostrato dalla HomeActivity se il processo di
 * recupero delle informazioni dell'edificio è andato a buon fine.
 */
public class CompleteHomeFragment extends Fragment {

    /**
     * Riferimento al widget adibito a search box.
     */
    SearchView searchView;

    /**
     * Riferimento al widget che mostra l'indirizzo dell'edificio rilevato.
     */
    TextView buildingAddress;

    /**
     * Riferimento al widget che mostra il nome dell'edificio rilevato.
     */
    TextView buildingName;

    /**
     * Riferimento al widget che mostra la descrizione dell'edificio rilevato.
     */
    TextView buildingDescription;

    /**
     * Riferimento al widget che mostra gli orari di apertura al pubblico dell'edificio rilevato.
     */
    TextView buildingOpeningHours;

    /**
     * Riferimento al widget che mostra le categorie di POI presenti nell'edificio rilevato.
     */
    ListView poiCategories;

    /**
     * Costruttore di default. Richiesto dalla documentazione di {@link Fragment}.
     */
    public CompleteHomeFragment() {}

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

        View view = inflater.inflate(R.layout.fragment_complete_home, container, false);

        //Setup della funzinalità di Search
        searchView = (SearchView)view.findViewById(R.id.searchview_poi);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        buildingAddress = (TextView)view.findViewById(R.id.view_address);
        buildingName = (TextView)view.findViewById(R.id.view_building_name);
        buildingDescription = (TextView)view.findViewById(R.id.view_building_description);
        buildingOpeningHours = (TextView)view.findViewById(R.id.view_building_opening_hours);
        poiCategories = (ListView)view.findViewById(R.id.view_poi_category_list);

        //workaround per rendere scrollabile lista categorie // TODO: 18/05/16 togliere
        poiCategories.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //Imposta il Listener sugli item della lista di categorie
        poiCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = (String)parent.getAdapter().getItem(position);

                HomeActivity homeActivity = (HomeActivity)getActivity();
                homeActivity.showPoisCategory(categoryName);
            }
        });
        return view;
    }

    /**
     * Imposta il nome dell'edificio nel relativo widget.
     * @param name nome dell'edificio.
     */
    public void setBuildingName(String name) {
        buildingName.setText(name);
    }

    /**
     * Imposta la descrizione dell'edificio nel relativo widget.
     * @param description descrizione dell'edificio.
     */
    public void setBuildingDescription(String description) {
        buildingDescription.setText(description);
    }

    /**
     * Imposta le ore di apertura al pubblico dell'edificio, nel relativo widget.
     * @param hours orario di apertura.
     */
    public void setBuildingOpeningHours(String hours) {
        buildingOpeningHours.setText(hours);
    }

    /**
     * Imposta l'indirizzo dell'edificio nel relativo widget.
     * @param address indirizzo dell'edificio.
     */
    public void setBuildingAddress(String address) {
        buildingAddress.setText(address);
    }

    /**
     * Si occupa del binding tra la List, contenente i nomi delle categorie di POI, ed il relativo
     * {@link ArrayAdapter}.
     * @param list lista contenente i nomi delle categorie di POI presenti nell'edificio.
     */
    public void setPoiCategoryListAdapter(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);

        LinearLayout categoryLayout = (LinearLayout)getView().findViewById(R.id.category_search);

        //Imposta l'altezza della ListView in modo da mostrarne tutti gli item, evitando scroll interno.
        ViewGroup.LayoutParams params = categoryLayout.getLayoutParams();

        //height = altezza_titolo + numero_item * altezza_item
        params.height = 100 + list.size()*100;

        poiCategories.setAdapter(adapter);
    }

}
