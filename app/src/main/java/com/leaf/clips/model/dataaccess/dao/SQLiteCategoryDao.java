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
 *Classe che rappresenta un DAO per la tabella “Category" del database locale
 */
public class SQLiteCategoryDao implements CategoryDao, CursorConverter {

    /**
     * Permette l'accesso al database locale
     */
    private final SQLDao sqlDao;
    
    /**
     * Costruttore della classe SQLiteCategoryDao
     * @param database Il database locale
     */
    public SQLiteCategoryDao(SQLiteDatabase database){
        sqlDao = new SQLDao(database);
    }

    /**
     * Metodo che viene utilizzato per convertire il risultato della query sulla tabella "Category" del database locale in un oggetto CategoryTable
     * @param cursor Risultato della query sulla tabella "Category" del database locale
     * @return  CategoryTable
     */
    @Override
    public CategoryTable cursorToType(Cursor cursor){
        int idIndex = cursor.getColumnIndex(CategoryContract.COLUMN_ID);
        int descriptionIndex = cursor.getColumnIndex(CategoryContract.COLUMN_DESCRIPTION);
        cursor.moveToNext();
        return new CategoryTable(cursor.getInt(idIndex), cursor.getString(descriptionIndex));
    }

    /**
     * Metodo che permette la rimozione delle informazioni di un edificio dalla tabella "Category" del database locale
     * @param id Identificativo della categoria da rimuovere dal database locale
     */
    @Override
    public void deleteCategory(int id){
        sqlDao.delete(CategoryContract.TABLE_NAME,
                CategoryContract.COLUMN_ID + "=" + id,
                null);
    }

    /**
     * Metodo per recuperare le informazioni di una categoria dal database locale tramite il suo identificativo, sotto forma di oggetto CategoryTable
     * @param id Identificativo della categoria di cui recuperare le informazioni
     * @return  CategoryTable
     */
    @Override
    public CategoryTable findCategory(int id){
        String [] columns = {
                CategoryContract.COLUMN_ID,
                CategoryContract.COLUMN_DESCRIPTION
        };
        Cursor cursor = sqlDao.query(true, CategoryContract.TABLE_NAME, columns,
                CategoryContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        return cursorToType(cursor);
    }

    /**
     * Metodo che permette l'inserimento di una categoria nella tabella "Category" del database locale
     * @param toInsert Oggetto di tipo CategoryTable che contiene le informazioni della categoria
     * @return  int
     */
    @Override
    public int insertCategory(CategoryTable toInsert){
        ContentValues values = new ContentValues();
        values.put(CategoryContract.COLUMN_ID, toInsert.getId());
        values.put(CategoryContract.COLUMN_DESCRIPTION, toInsert.getDescription());
        sqlDao.insert(CategoryContract.TABLE_NAME, values);
        return toInsert.getId();
    }

    /**
     * Metodo per aggiornare le informazioni di una categoria nella tabella "Category" del database locale
     * @param id Identificativo della categoria di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate della categoria
     * @return  boolean
     */
    @Override
    public boolean updateCategory(int id, CategoryTable toUpdate){
        String [] columns = {
                CategoryContract.COLUMN_ID,
                CategoryContract.COLUMN_DESCRIPTION
        };
        Cursor cursor = sqlDao.query(true, CategoryContract.TABLE_NAME, columns,
                CategoryContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return false;
        else {
            ContentValues values = new ContentValues();
            values.put(CategoryContract.COLUMN_ID, toUpdate.getId());
            values.put(CategoryContract.COLUMN_DESCRIPTION, toUpdate.getDescription());
            sqlDao.update(CategoryContract.TABLE_NAME, values, CategoryContract.COLUMN_ID + "="
                    + id, null);
            return true;
        }
    }

}
