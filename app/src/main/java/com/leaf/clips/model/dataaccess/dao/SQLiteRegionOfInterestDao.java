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
 *Classe che rappresenta un DAO per la tabella “ROI" del database locale
 */
public class SQLiteRegionOfInterestDao implements RegionOfInterestDao, CursorConverter {

    /**
     * Permette l'accesso al database locale
     */
    private final SQLDao sqlDao;
    
    /**
     * Costruttore della classe SQLiteRegionOfInterestDao
     * @param database Il database locale
     */
    public SQLiteRegionOfInterestDao(SQLiteDatabase database){ 
        sqlDao = new SQLDao(database);    
    }

    /**
     * Metodo che viene utilizzato per convertire il risultato della query sulla tabella "ROI" del database locale in un oggetto RegionOfInterestTable
     * @param cursor Risultato della query sulla tabella "ROI" del database locale
     * @return  RegionOfInterestTable
     */
    @Override
    public RegionOfInterestTable cursorToType(Cursor cursor){
        int idIndex = cursor.getColumnIndex(RegionOfInterestContract.COLUMN_ID);
        int uuidIndex = cursor.getColumnIndex(RegionOfInterestContract.COLUMN_UUID);
        int majorIndex = cursor.getColumnIndex(RegionOfInterestContract.COLUMN_MAJOR);
        int minorIndex = cursor.getColumnIndex(RegionOfInterestContract.COLUMN_MINOR);
        cursor.moveToNext();
        return new RegionOfInterestTable(
                cursor.getInt(idIndex),
                cursor.getString(uuidIndex),
                cursor.getInt(majorIndex),
                cursor.getInt(minorIndex)
        );
    }

    /**
     * Metodo che permette la rimozione delle informazioni di una RegionOfInterest dalla tabella "ROI" del database locale
     * @param id Identificativo della RegionOfInterest di cui rimuovere le informazioni dal database locale
     */
    @Override
    public void deleteRegionOfInterest(int id){
        sqlDao.delete(
                RegionOfInterestContract.TABLE_NAME,
                RegionOfInterestContract.COLUMN_ID + "=" + id,
                null);
    }

    /**
     * Metodo che viene utilizzato per recuperare le informazioni di tutte le RegionOfInterest associato ad certo edificio, dato il major dell'edificio
     * @param major Major dell'edificio
     * @return  Collection<RegionOfInterestTable>
     */
    @Override
    public Collection<RegionOfInterestTable> findAllRegionsWithMajor(int major){
        String [] columns = new String[]{
                RegionOfInterestContract.COLUMN_ID,
                RegionOfInterestContract.COLUMN_UUID,
                RegionOfInterestContract.COLUMN_MAJOR,
                RegionOfInterestContract.COLUMN_MINOR
        };
        Cursor cursor = sqlDao.query(true, RegionOfInterestContract.TABLE_NAME, columns, 
                RegionOfInterestContract.COLUMN_MAJOR + "=" + major, null, null, null, null, null);
        int roisNumber = cursor.getCount();
        PriorityQueue<RegionOfInterestTable> regionOfInterestTables = new PriorityQueue<>(roisNumber,
                new Comparator<RegionOfInterestTable>() {
                    @Override
                    public int compare(RegionOfInterestTable lhs, RegionOfInterestTable rhs) {
                        if (lhs.getId() > rhs.getId())
                            return 1;
                        else if (lhs.getId() == rhs.getId())
                            return 0;
                        else
                            return -1;
                    }
                });
        for (int i = 0; i < roisNumber; i++)
            regionOfInterestTables.add(cursorToType(cursor));
        return regionOfInterestTables;
    }

    /**
     * Metodo per recuperare le informazioni di una RegionOfInterest dal database locale tramite il suo identificativo, sotto forma di oggetto RegionOfInterestTable
     * @param id Identificativo della RegionOfInterest di cui recuperare le informazioni
     * @return  RegionOfInterestTable
     */
    @Override
    public RegionOfInterestTable findRegionOfInterest(int id){
        String [] columns = new String[]{
                RegionOfInterestContract.COLUMN_ID,
                RegionOfInterestContract.COLUMN_UUID,
                RegionOfInterestContract.COLUMN_MAJOR,
                RegionOfInterestContract.COLUMN_MINOR
        };
        Cursor cursor = sqlDao.query(true, RegionOfInterestContract.TABLE_NAME, columns,
                RegionOfInterestContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        return cursorToType(cursor);
    }

    /**
     * Metodo che permette l'inserimento delle informazioni di una RegionOfInterest in una entry della tabella "ROI" del database locale
     * @param toInsert Oggetto di tipo RegionOfInterestTable che contiene le informazioni della RegionOfInterest
     * @return  int
     */
    @Override
    public int insertRegionOfInterest(RegionOfInterestTable toInsert){
        ContentValues values = new ContentValues();
        values.put(RegionOfInterestContract.COLUMN_ID, toInsert.getId());
        values.put(RegionOfInterestContract.COLUMN_UUID, toInsert.getUUID());
        values.put(RegionOfInterestContract.COLUMN_MAJOR, toInsert.getMajor());
        values.put(RegionOfInterestContract.COLUMN_MINOR, toInsert.getMinor());
        sqlDao.insert(RegionOfInterestContract.TABLE_NAME, values);
        return toInsert.getId();
    }

    /**
     * Metodo per aggiornare le informazioni di una RegionOfInterest nella tabella "ROI" del database locale
     * @param id Identificativo della RegionOfInterest di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate della RegionOfInterest
     * @return  boolean
     */
    @Override
    public boolean updateRegionOfInterest(int id, RegionOfInterestTable toUpdate){
        String [] columns = new String[]{
                RegionOfInterestContract.COLUMN_ID,
                RegionOfInterestContract.COLUMN_UUID,
                RegionOfInterestContract.COLUMN_MAJOR,
                RegionOfInterestContract.COLUMN_MINOR
        };
        Cursor cursor = sqlDao.query(true, RegionOfInterestContract.TABLE_NAME, columns,
                RegionOfInterestContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return false;
        else {
            ContentValues values = new ContentValues();
            values.put(RegionOfInterestContract.COLUMN_ID, toUpdate.getId());
            values.put(RegionOfInterestContract.COLUMN_UUID, toUpdate.getUUID());
            values.put(RegionOfInterestContract.COLUMN_MAJOR, toUpdate.getMajor());
            values.put(RegionOfInterestContract.COLUMN_MINOR, toUpdate.getMinor());
            sqlDao.update(BuildingContract.TABLE_NAME, values, BuildingContract.COLUMN_ID + "="
                    + id, null);
            return true;
        }
    }

}
