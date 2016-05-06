package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *Classe che rappresenta un POI per la tabella “POI" del database locale
 */
public class SQLitePointOfInterestDao implements PointOfInterestDao, CursorConverter {

    /**
     * Permette l'accesso al database locale
     */
    private final SQLDao sqlDao;

    /**
     * Costruttore della classe SQLitePointOfInterestDao
     * @param database Il database locale
     */
    public SQLitePointOfInterestDao(SQLiteDatabase database){
        sqlDao = new SQLDao(database);
    }

    /**
     * Metodo che viene utilizzato per convertire il risultato della query sulla tabella "POI" del database locale in un oggetto PointOfInterestTable
     * @param cursor Risultato della query sulla tabella "POI" del database locale
     * @return  PointOfInterestTable
     */
    @Override
    public PointOfInterestTable cursorToType(Cursor cursor){
        int idIndex = cursor.getColumnIndex(PointOfInterestContract.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(PointOfInterestContract.COLUMN_NAME);
        int descriptionIndex = cursor.getColumnIndex(PointOfInterestContract.COLUMN_DESCRIPTION);
        int categoryIndex = cursor.getColumnIndex(PointOfInterestContract.COLUMN_CATEGORYID);
        cursor.moveToNext();
        return new PointOfInterestTable(
                cursor.getString(descriptionIndex),
                cursor.getInt(idIndex),
                cursor.getString(nameIndex),
                cursor.getInt(categoryIndex)
        );
    }

    /**
     * Metodo che permette la rimozione delle informazioni di un edificio dalla tabella "POI" del database locale
     * @param id Identificativo del POI di cui rimuovere le informazioni dal database locale
     */
    @Override
    public void deletePointOfInterest(int id){
        sqlDao.delete(
                PointOfInterestContract.TABLE_NAME,
                PointOfInterestContract.COLUMN_ID + "=" + id,
                null);
    }

    /**
     * Metodo che viene utilizzato per recuperare le informazioni di tutti gli edifici presenti nella tabella "POI" del database locale
     * @param major Identificativo Major associato a tutti i beacon presenti in uno stesso edificio
     * @return  Collection<PointOfInterestTable>
     */
    @Override
    public Collection<PointOfInterestTable> findAllPointsWithMajor(int major){
        String [] columns = new String[]{
                PointOfInterestContract.TABLE_NAME + "." + PointOfInterestContract.COLUMN_ID,
                PointOfInterestContract.TABLE_NAME + "." + PointOfInterestContract.COLUMN_NAME,
                PointOfInterestContract.TABLE_NAME + "." + PointOfInterestContract.COLUMN_DESCRIPTION,
                PointOfInterestContract.TABLE_NAME + "." + PointOfInterestContract.COLUMN_CATEGORYID
        };
        Cursor cursor = sqlDao.rawQuery("SELECT " + columns[0] + ", " + columns[1] + ", " +
                columns[2] + ", " + columns[3] + " "+ " FROM " +
                RegionOfInterestContract.TABLE_NAME + " JOIN " +
                RoiPoiContract.TABLE_NAME + " ON " +
                RegionOfInterestContract.TABLE_NAME + "." + RegionOfInterestContract.COLUMN_ID + "=" +
                RoiPoiContract.COLUMN_ROIID + " JOIN " + PointOfInterestContract.TABLE_NAME + " ON "
                + PointOfInterestContract.TABLE_NAME + "." + PointOfInterestContract.COLUMN_ID +
                "=" + RoiPoiContract.COLUMN_POIID + " WHERE "
                + RegionOfInterestContract.COLUMN_MAJOR + "=" + major, null);
        int poisNumber = cursor.getCount();
        PriorityQueue<PointOfInterestTable> pointOfInterestTables = new PriorityQueue<>(poisNumber,
                new Comparator<PointOfInterestTable>() {
                    @Override
                    public int compare(PointOfInterestTable lhs, PointOfInterestTable rhs) {
                        if (lhs.getId() > rhs.getId())
                            return 1;
                        else if (lhs.getId() == rhs.getId())
                            return 0;
                        else
                            return -1;
                    }
                });
        for (int i = 0; i < poisNumber; i++)
            pointOfInterestTables.add(cursorToType(cursor));
        return pointOfInterestTables;
    }

    /**
     * Metodo per recuperare le informazioni di un edificio dal database locale tramite il suo identificativo, sotto forma di oggetto PointOfInterestTable
     * @param id Identificativo del POI di cui recuperare le informazioni
     * @return  PointOfInterestTable
     */
    @Override
    public PointOfInterestTable findPointOfInterest(int id){
        String [] columns = new String[]{
                PointOfInterestContract.COLUMN_ID,
                PointOfInterestContract.COLUMN_NAME,
                PointOfInterestContract.COLUMN_DESCRIPTION,
                PointOfInterestContract.COLUMN_CATEGORYID
        };
        Cursor cursor = sqlDao.query(true, PointOfInterestContract.TABLE_NAME, columns,
                PointOfInterestContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        return cursorToType(cursor);
    }

    /**
     * Metodo che permette l'inserimento delle informazioni di un edificio in una entry della tabella "POI" del database locale
     * @param toInsert Oggetto di tipo PointOfInterestTable che contiene le informazioni dell'edificio
     * @return  int
     */
    @Override
    public int insertPointOfInterest(PointOfInterestTable toInsert){
        ContentValues values = new ContentValues();
        values.put(PointOfInterestContract.COLUMN_ID, toInsert.getId());
        values.put(PointOfInterestContract.COLUMN_NAME, toInsert.getName());
        values.put(PointOfInterestContract.COLUMN_DESCRIPTION, toInsert.getDescription());
        values.put(PointOfInterestContract.COLUMN_CATEGORYID, toInsert.getCategoryId());
        sqlDao.insert(PointOfInterestContract.TABLE_NAME, values);
        return toInsert.getId();
    }

    /**
     * Metodo per aggiornare le informazioni di un edificio nella tabella "POI" del database locale
     * @param id Identificativo del POI di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate del POI
     * @return  boolean
     */
    @Override
    public boolean updatePointOfInterest(int id, PointOfInterestTable toUpdate){
        String [] columns = new String[]{
                PointOfInterestContract.COLUMN_ID,
                PointOfInterestContract.COLUMN_NAME,
                PointOfInterestContract.COLUMN_DESCRIPTION,
                PointOfInterestContract.COLUMN_CATEGORYID
        };
        Cursor cursor = sqlDao.query(true, PointOfInterestContract.TABLE_NAME, columns,
                PointOfInterestContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return false;
        else {
            ContentValues values = new ContentValues();
            values.put(PointOfInterestContract.COLUMN_ID, toUpdate.getId());
            values.put(PointOfInterestContract.COLUMN_NAME, toUpdate.getName());
            values.put(PointOfInterestContract.COLUMN_DESCRIPTION, toUpdate.getDescription());
            values.put(PointOfInterestContract.COLUMN_CATEGORYID, toUpdate.getCategoryId());
            sqlDao.update(BuildingContract.TABLE_NAME, values, BuildingContract.COLUMN_ID + "="
                    + id, null);
            return true;
        }
    }

}

