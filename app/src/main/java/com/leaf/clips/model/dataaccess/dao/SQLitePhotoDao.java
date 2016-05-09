package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import junit.framework.Assert;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *Classe che rappresenta un DAO per la tabella “Photo" del database locale
 */
public class SQLitePhotoDao implements PhotoDao, CursorConverter {

    /**
     * Permette l'accesso al database locale
     */
    private final SQLDao sqlDao;

    /**
     * Costruttore della classe SQLitePhotoDao
     * @param database Il database locale
     */
    public SQLitePhotoDao(SQLiteDatabase database){
        sqlDao = new SQLDao(database);
    }

    /**
     * Metodo che viene utilizzato per convertire il risultato della query sulla tabella "Photo" del database locale in un oggetto PhotoTable
     * @param cursor Risultato della query sulla tabella "Photo" del database locale
     * @return  PhotoTable
     */
    @Override
    public PhotoTable cursorToType(Cursor cursor){
        int idIndex = cursor.getColumnIndex(PhotoContract.COLUMN_ID);
        int edgeIndex = cursor.getColumnIndex(PhotoContract.COLUMN_EDGEID);
        int urlIndex = cursor.getColumnIndex(PhotoContract.COLUMN_URL);
        cursor.moveToNext();
        System.out.println("%%%%" + cursor.getInt(idIndex)+ cursor.getString(urlIndex)+
                cursor.getInt(edgeIndex));
        return new PhotoTable(cursor.getInt(idIndex), cursor.getString(urlIndex),
                cursor.getInt(edgeIndex));
    }

    /**
     * Metodo che permette la rimozione delle informazioni di una foto dalla tabella "Photo" del database locale
     * @param id Identificativo della foto di cui rimuovere le informazioni dal database locale
     */
    @Override
    public void deletePhoto(int id){
        sqlDao.delete(PhotoContract.TABLE_NAME,
                PhotoContract.COLUMN_ID + "=" + id,
                null);
    }

    /**
     * Metodo che viene utilizzato per recuperare le informazioni di tutte foto associate ad un Edge presenti nella tabella "Photo" del database locale
     * @param id Identificativo dell'Edge
     * @return  Collection<PhotoTable>
     */
    @Override
    public Collection<PhotoTable> findAllPhotosOfEdge(int id){
        String [] columns = {
                PhotoContract.COLUMN_ID,
                PhotoContract.COLUMN_EDGEID,
                PhotoContract.COLUMN_URL
        };
        final Cursor cursor = sqlDao.query(true, PhotoContract.TABLE_NAME, columns,
            PhotoContract.COLUMN_EDGEID + "=" + id, null, null, null, null, null);

        short photoNumber = (short)cursor.getCount();

        Assert.assertEquals(3, photoNumber);
        List<PhotoTable> photoTables = new LinkedList<>();



        for (int i = 0; i < photoNumber; i++){
            PhotoTable pt = cursorToType(cursor);
            photoTables.add(pt);
        }

        return photoTables;
    }

    /**
     * Metodo per recuperare le informazioni di una foto dal database locale tramite il suo identificativo, sotto forma di oggetto PhotoTable
     * @param id Identificativo della foto
     * @return  PhotoTable
     */
    @Override
    public PhotoTable findPhoto(int id){
        String [] columns = {
                PhotoContract.COLUMN_ID,
                PhotoContract.COLUMN_EDGEID,
                PhotoContract.COLUMN_URL
        };
        Cursor cursor = sqlDao.query(true, PhotoContract.TABLE_NAME, columns,
                PhotoContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        return cursorToType(cursor);
    }

    /**
     * Metodo che permette l'inserimento delle informazioni di una foto in una entry della tabella "Photo" del database locale
     * @param toInsert Oggetto di tipo Photo che contiene le informazioni della foto
     * @return  int
     */
    @Override
    public int insertPhoto(PhotoTable toInsert){
        ContentValues values = new ContentValues();
        values.put(PhotoContract.COLUMN_ID, toInsert.getId());
        values.put(PhotoContract.COLUMN_EDGEID, toInsert.getEdgeId());
        values.put(PhotoContract.COLUMN_URL, toInsert.getUrl());
        sqlDao.insert(PhotoContract.TABLE_NAME, values);
        return toInsert.getId();
    }

    /**
     * Metodo per aggiornare le informazioni di una foto nella tabella "Photo" del database locale
     * @param id Identificativo della foto di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate della foto
     * @return  boolean
     */
    @Override
    public boolean updatePhoto(int id, PhotoTable toUpdate){
        String [] columns = {
                PhotoContract.COLUMN_ID,
                PhotoContract.COLUMN_EDGEID,
                PhotoContract.COLUMN_URL
        };
        Cursor cursor = sqlDao.query(true, PhotoContract.TABLE_NAME, columns,
                PhotoContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return false;
        else {
            ContentValues values = new ContentValues();
            values.put(PhotoContract.COLUMN_ID, toUpdate.getId());
            values.put(PhotoContract.COLUMN_EDGEID, toUpdate.getEdgeId());
            values.put(PhotoContract.COLUMN_URL, toUpdate.getUrl());
            sqlDao.update(PhotoContract.TABLE_NAME, values, PhotoContract.COLUMN_ID + "="
                    + id, null);
            return true;
        }
    }

}
