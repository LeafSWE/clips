package com.leaf.clips.view;

/**
 * @author Marco Zanella
 * @version 0.02
 * @since 0.01
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.PoiCategoryActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *Classe che si occupa di mostrare la lista dei POI relativi ad una certa categoria. La UI legata a questa classe permette all'utente di accedere alle informazioni di un certo POI appartenente alla categoria.
 */
public class PoiCategoryViewImp implements PoiCategoryView {

    /**
     * View che permette di visualizzare la lista delle categorie di POI
     */
    private ListView pois;

    /**
     * Costruttore della classe PoiCategoryViewImp
     * @param presenter Presenter della View che viene creata
     */
    public PoiCategoryViewImp(PoiCategoryActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_poi_category);

        //pois.findViewById(R.id.category_list);
        pois = (ListView)presenter.findViewById(R.id.category_list);
    }

    /**
     * Presenter della View
     */
    private PoiCategoryActivity presenter;

    /**
     * Metodo utilizzato per visualizzare tutti i POI appartenenti ad una certa categoria
     * @param toDisplay Array di stringhe che rappresentano le categorie che devono essere mostrate
     */
    @Override
    public void setPoiListAdapter(final String[][] toDisplay) {

        //costruisco una mappa con chiave categoria e valore una lista di stringhe, che viene
        //riempita con i nomi dei POI associati a quella categoria
        final Map<String, ArrayList<String>> map = new HashMap<>();
        for(int i = 0; i < toDisplay.length; i ++){
            if (!map.containsKey(toDisplay[i][1]))
                map.put(toDisplay[i][1], new ArrayList<String>());
            (map.get(toDisplay[i][1])).add(toDisplay[i][0]);
        }

        //viene creato un array di stringhe contenenti tutte le categorie
        final String[] categories = (map.keySet()).toArray(new String[map.size()]);

        //viene settato l'adapter con le categorie
        pois.setAdapter(new ArrayAdapter<String>(presenter, android.R.layout.simple_list_item_1,
                categories));

        //viene impostato l'azione quando viene selezionata una categoria
        pois.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                //viene creato un builder per costruire un alert contenente tutti i POI di una certa
                //categoria
                AlertDialog.Builder builder = new AlertDialog.Builder(presenter);

                //viene impostato il titolo dell'alert
                builder.setTitle(R.string.poi_category_choose_title);

                //viene creato un adapter contenente tutti i POI di una certa categoria
                final ListAdapter adapter = new ArrayAdapter<String>(presenter,
                        android.R.layout.simple_list_item_1, map.get(categories[position]));

                //viene impostato l'adapter settando l'azione da fare quando un POI è stato
                //selezionato
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //viene recuperata la posizione del POI selezionato
                        int i = 0;
                        boolean found = false;
                        while(i < toDisplay.length && !found){
                            if (toDisplay[i][0] == adapter.getItem(which) &&
                                    toDisplay[i][1] == categories[position])
                                found = true;
                            else
                                i++;
                        }
                        //viene richiamato il metodo del presenter per iniziare la navigazione
                        presenter.startNavigation(i);
                    }
                });

                //viene agginto il bottone per tornare alle categorie
                builder.setCancelable(true);
                builder.setNegativeButton(
                        "cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                //viene creato e mostrato l'alert
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });
    }

}

