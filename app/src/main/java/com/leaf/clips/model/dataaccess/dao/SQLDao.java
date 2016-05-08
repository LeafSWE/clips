package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *Classe che contiene le operazioni di query dirette
 */
public class SQLDao {

    /**
     * Database locale
     */
    private final SQLiteDatabase database;

    /**
     * Costruttore della classe SQLDao
     * @param database Database locale dell'applicazione
     */
    public SQLDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Metodo per la rimozione di valori dal database locale. Ritorna il numero delle righe rimosse
     * @param tableName Nome della tabella su cui eseguire l'operazione
     * @param where Condizioni utilizzate per filtrare le righe su cui effettuare l'operazione
     * @param whereArgs Valori delle condizioni where
     * @return  int
     */
    public int delete(String tableName, String where, String[] whereArgs) {
        return database.delete(tableName, where, whereArgs);
    }

    /**
     * Metodo per l'inserimento di valori in una tabella del database locale. Ritorna l'id della riga inserita
     * @param tableName Nome della tabella su cui effettuare l'operazione
     * @param values Valori da inserire nella tabella
     * @return  long
     */
    public long insert(String tableName, ContentValues values) {
        return database.insert(tableName, null, values);
    }

    /**
     * Metodo per effettuare una query sul database locale
     * @param distinct Parametro che indica se applicare o meno la clausola DISTINCT alla query
     * @param tableName Nome della tabella si cui effettuare la query
     * @param columns Lista delle colonne da ritornare
     * @param where Condizioni utilizzate per filtrare le righe su cui effettuare l'operazione
     * @param whereArgs Valori delle condizioni where
     * @param groupBy Parametro su cui effettuare il raggruppamento del risultati della query
     * @param having Condizioni utilizzate per filtrare le righe dopo aver applicato la clausola HAVING
     * @param orderBy Parametro su cui effettuare l'ordinamento dei risultati della query
     * @param limit Limite di righe che la query può restituire
     * @return  Cursor
     */
    public Cursor query(boolean distinct, String tableName, String[] columns, String where, String[]
            whereArgs, String groupBy, String having, String orderBy, String limit) {
        return database.query(distinct, tableName, columns, where, whereArgs, groupBy, having, orderBy, limit);
    }

    /**
     * Metodo per eseguire una query fornendola sotto forma di stringa
     * @param sqlQuery Query da eseguire sotto forma di stringa
     * @param whereArgs Argomenti della clausola WHERE
     * @return  Cursor
     */
    public Cursor rawQuery(String sqlQuery, String[] whereArgs) {
        return database.rawQuery(sqlQuery, whereArgs);
    }

    /**
     * Metodo per l'aggiornamento di valori in una tabella del database locale. Ritorna il
     * numero di righe modificate
     * @param tableName Nome della tabella su cui eseguire l'operazione
     * @param values Valori aggiornati che sostituiranno i presenti
     * @param where Condizioni utilizzate per filtrare le righe su cui effettuare l'operazione
     * @param whereArgs Valori delle condizioni where
     * @return  int
     */
    public int update(String tableName, ContentValues values, String where, String[] whereArgs) {
        return database.update(tableName, values, where, whereArgs);
    }

}

