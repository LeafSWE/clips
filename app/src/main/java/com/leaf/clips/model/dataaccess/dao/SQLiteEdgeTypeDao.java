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
 *Classe che rappresenta un DAO per la tabella “EdgeType" del database locale
 */
public class SQLiteEdgeTypeDao implements EdgeTypeDao, CursorConverter {

    /**
     * Permette l'accesso al database locale
     */
    private final SQLDao sqlDao;

    /**
     * Costruttore della classe SQLiteEdgeTypeDao
     * @param database Il database locale
     */
    public SQLiteEdgeTypeDao(SQLiteDatabase database){
        sqlDao = new SQLDao(database);
    }

    /**
     * Metodo che viene utilizzato per convertire il risultato della query sulla tabella "EdgeType" del database locale in un oggetto EdgeTypeTable
     * @param cursor Risultato della query sulla tabella "EdgeType" del database locale
     * @return  EdgeTypeTable
     */
    @Override
    public EdgeTypeTable cursorToType(Cursor cursor){
        int idIndex = cursor.getColumnIndex(EdgeTypeContract.COLUMN_ID);
        int typeNameIndex = cursor.getColumnIndex(EdgeTypeContract.COLUMN_TYPENAME);
        cursor.moveToNext();
        return new EdgeTypeTable(cursor.getInt(idIndex), cursor.getString(typeNameIndex));
    }

    /**
     * Metodo che permette la rimozione delle informazioni di un tipo di Edge dalla tabella "EdgeType" del database locale
     * @param id Identificativo del tipo di Edge di cui rimuovere le informazioni dal database locale
     */
    @Override
    public void deleteEdgeType(int id){
        sqlDao.delete(EdgeTypeContract.TABLE_NAME,
                EdgeTypeContract.COLUMN_ID + "=" + id,
                null);
    }

    /**
     * Metodo per recuperare le informazioni di un tipo di Edge dal database locale tramite il suo identificativo, sotto forma di oggetto EdgeTypeTable
     * @param id Identificativo del tipo di Edge di cui recuperare le informazioni
     * @return  EdgeTypeTable
     */
    @Override
    public EdgeTypeTable findEdgeType(int id){
        String [] columns = {
                EdgeTypeContract.COLUMN_ID,
                EdgeTypeContract.COLUMN_TYPENAME
        };
        Cursor cursor = sqlDao.query(true, EdgeTypeContract.TABLE_NAME, columns,
                EdgeTypeContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        return cursorToType(cursor);
    }

    /**
     * Metodo che permette l'inserimento delle informazioni del tipo di Edge in una entry della tabella "EdgeType" del database locale
     * @param toInsert Oggetto di tipo EdgeTypeTable che contiene le informazioni di un tipo di Edge
     * @return  int
     */
    @Override
    public int insertEdgeType(EdgeTypeTable toInsert){
        ContentValues values = new ContentValues();
        values.put(EdgeTypeContract.COLUMN_ID, toInsert.getId());
        values.put(EdgeTypeContract.COLUMN_TYPENAME, toInsert.getTypeName());
        sqlDao.insert(EdgeTypeContract.TABLE_NAME, values);
        return toInsert.getId();
    }

    /**
     * Metodo per aggiornare le informazioni di un tipo di Edge nella tabella "EdgeType" del database locale
     * @param id Identificativo del tipo di Edge di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate del tipo di Edge
     * @return  boolean
     */
    @Override
    public boolean updateEdgeType(int id, EdgeTypeTable toUpdate){
        String [] columns = {
                EdgeTypeContract.COLUMN_ID,
                EdgeTypeContract.COLUMN_TYPENAME
        };
        Cursor cursor = sqlDao.query(true, EdgeTypeContract.TABLE_NAME, columns,
                EdgeTypeContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return false;
        else {
            ContentValues values = new ContentValues();
            values.put(EdgeTypeContract.COLUMN_ID, toUpdate.getId());
            values.put(EdgeTypeContract.COLUMN_TYPENAME, toUpdate.getTypeName());
            sqlDao.update(EdgeTypeContract.TABLE_NAME, values, EdgeTypeContract.COLUMN_ID + "="
                    + id, null);
            return true;
        }
    }

}

