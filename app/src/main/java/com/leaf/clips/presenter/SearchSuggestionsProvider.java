package com.leaf.clips.presenter;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import java.util.Collection;

import android.app.SearchManager;
import android.database.MatrixCursor;
import android.support.annotation.Nullable;
import android.util.Log;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NoBeaconSeenException;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;

import javax.inject.Inject;
/**
 *Classe che estende content Provider e si occupa della gestione suggerimenti di ricerca per la navigazione
 */
public class SearchSuggestionsProvider extends ContentProvider{

    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal model
     */
    @Inject
    InformationManager informationManager;

    /**
     * Insieme di tutti i PointOfInterest di un edificio
     */
    Collection<PointOfInterest> pois;

    /**
     * Identifica la struttura di un singolo suggerimento
     */
    private static final String[] COLUMNS = { //colonne per impostare le informazioni da attribuire il suggerimento
            "_id", // must include this column
            SearchManager.SUGGEST_COLUMN_TEXT_1,
            SearchManager.SUGGEST_COLUMN_INTENT_DATA,
    };

    /**
     * Metodo che inizializza un oggetto di tipo SearchSuggestionProvider
     * @return  boolean
     */
    public boolean onCreate() {
        return true;
    }
    /**
     * Metodo che serve per popolare i suggerimenti della SearchView in base al testo inserito
     * @param uri URI su cui fare la query
     * @param projection Lista delle colonne della tabella del content provider
     * @param selection Criterio da applicare per filtrare le righe del content provider
     * @param selectionArgs Insieme di argomenti su cui fare la selezione
     * @param sortOrder Ordine dei risultati
     * @return  Cursor
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String query = selectionArgs[0];
        if (query == null || query.length() == 0) {
            return null;
        }else {
            if(informationManager == null)
            MyApplication.getInfoComponent().inject(this);
            MatrixCursor cursor = new MatrixCursor(COLUMNS);
            try {
                pois = informationManager.getBuildingMap().getAllPOIs();
                int n = 0;
                for (PointOfInterest s : pois) {
                    if (s.getName().matches("(?i:^(" + query + ").*)")) {
                        //creazione di un suggerimento in base alle colonne define in COLUMNS
                        cursor.addRow(new Object[]{n, s.getName(), s.getId()});
                        n++;
                    }
                }
            }
            catch(NoBeaconSeenException e){
                 e.printStackTrace();
            }
            catch (Exception e) {
                Log.e("SearchSuggProvider", "Failed to lookup " + query, e);
            }

            return cursor;
        }
    }

    /**
     * Metodo che ritorna il MIME type associato al parametro passato
     * @param uri URI su cui fare la query
     * @return  String
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/vnd.com.leaf.clips.provider.suggestions";
    }

    /**
     * Metodo che serve per inserire i suggerimenti nel content provider
     * @param uri URI in cui fare l'inserimento
     * @param values Insieme di coppie nome-colonna/valore da inserire nel content provider
     * @return  Uri
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    /**
     * Metodo che serve per eliminare elementi dal Content Provider
     * @param uri URI su cui fare la query
     * @param selection Criterio da applicare per filtrare le righe del content provider
     * @param selectionArgs Insieme di argomenti su cui fare la selezione
     * @return  int
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Metodo utilizzato per aggiornare il content provider
     * @param uri URI su cui fare la query
     * @param values Valori da aggiungere
     * @param selection Criterio da applicare per filtrare le righe del content provider
     * @param selectionArgs Insieme di argomenti su cui fare la selezione
     * @return  int
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}