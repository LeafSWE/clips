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
 *Classe che rappresenta un DAO per la tabella “ROIPOI" del database locale
 */
public class SQLiteRoiPoiDao implements RoiPoiDao, CursorConverter {

    /**
     * Permette l'accesso al database locale
     */
    private final SQLDao sqlDao;

    /**
     * Costruttore della classe SQLiteRoiPoiDao
     * @param database Il database locale
     */
    public SQLiteRoiPoiDao(SQLiteDatabase database){
        sqlDao = new SQLDao(database);
    }

    /**
     * Metodo che viene utilizzato per convertire il risultato della query sulla tabella "ROIPOI" del database locale in un oggetto RoiPoiTable
     * @param cursor Risultato della query sulla tabella "ROIPOI" del database locale
     * @return  RoiPoiTable
     */
    @Override
    public RoiPoiTable cursorToType(Cursor cursor){
        int poiIdIndex = cursor.getColumnIndex(RoiPoiContract.COLUMN_POIID);
        int roiIdIndex = cursor.getColumnIndex(RoiPoiContract.COLUMN_ROIID);
        cursor.moveToNext();
        return new RoiPoiTable(
                cursor.getInt(roiIdIndex),
                cursor.getInt(poiIdIndex)
        );
    }

    /**
     * Metodo che permette la rimozione delle associazioni tra un ROI e i POI ad esso associato dalla tabella "ROIPOI" del database locale
     * @param poi Identificativo del POI di cui rimuovere le associazioni con i ROI dal database locale
     */
    @Override
    public void deleteRoiPoisWherePoi(int poi){
        sqlDao.delete(RoiPoiContract.TABLE_NAME,
                RoiPoiContract.COLUMN_POIID + "=" + poi,
                null);
    }

    /**
     * Metodo che permette la rimozione delle associazioni tra un POI e i ROI ad esso associato dalla tabella "ROIPOI" del database locale
     * @param roi Identificativo del ROI di cui rimuovere le associazioni con i POI dal database locale
     */
    @Override
    public void deleteRoiPoisWhereRoi(int roi){
        sqlDao.delete(RoiPoiContract.TABLE_NAME,
                RoiPoiContract.COLUMN_ROIID + "=" + roi,
                null);
    }

    /**
     * Metodo per recuperare tutti gli identificativi dei POI associati ad un ROI
     * @param roi Identificativo del ROI di cui recuperare gli identificativi di tutti i POI associati
     * @return  int[]
     */
    @Override
    public int[] findAllPointsWithRoi(int roi){
        Cursor cursor = sqlDao.query(true, RoiPoiContract.TABLE_NAME, new String[]{
            RoiPoiContract.COLUMN_POIID}, RoiPoiContract.COLUMN_ROIID + "=" + roi, null, null,
            null, null, null);
        int poiIndex = cursor.getColumnIndex(RoiPoiContract.COLUMN_POIID);
        int [] points = new int[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            points[i] = cursor.getInt(poiIndex);
            i++;
        }
        return points;
    }

    /**
     * Metodo per recuperare tutti gli identificativi dei ROI associati ad un POI
     * @param poi Identificativo del POI di cui recuperare gli identificativi di tutti i ROI associati
     * @return  int[]
     */
    @Override
    public int[] findAllRegionsWithPoi(int poi){
        Cursor cursor = sqlDao.query(true, RoiPoiContract.TABLE_NAME, new String[]{
                        RoiPoiContract.COLUMN_ROIID}, RoiPoiContract.COLUMN_POIID + "=" + poi, null, null,
                null, null, null);
        int roiIndex = cursor.getColumnIndex(RoiPoiContract.COLUMN_ROIID);
        int [] regions = new int[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            regions[i] = cursor.getInt(roiIndex);
            i++;
        }
        return regions;
    }

    /**
     * Metodo che permette l'inserimento tra ROI ed POI nel database locale utilizzando un oggetto RoiPoiTable
     * @param toInsert Oggetto di tipo RoiPoiTable che contiene le associazioni tra ROI e POI
     * @return  int
     */
    @Override
    public int insertRoiPoi(RoiPoiTable toInsert){
        ContentValues values = new ContentValues();
        values.put(RoiPoiContract.COLUMN_POIID, toInsert.getPoiID());
        values.put(RoiPoiContract.COLUMN_ROIID, toInsert.getRoiID());
        sqlDao.insert(RoiPoiContract.TABLE_NAME, values);
        return 1;
    }

    /**
     * Metodo per aggiornare le associazioni tra POI e ROI
     * @param poi Identificativo del POI di cui aggiungere una associazione con un ROI
     * @param roi Identificativo del ROI di cui aggiungere una associazione con un POI
     * @param toUpdate Oggetto che contiene le associazioni tra ROI e POI
     * @return  boolean
     */
    @Override
    public boolean updateRoiPoi(int poi, int roi, RoiPoiTable toUpdate){
        String [] columns = new String[]{
                RoiPoiContract.COLUMN_POIID,
                RoiPoiContract.COLUMN_ROIID
        };
        Cursor cursor = sqlDao.query(true, RoiPoiContract.TABLE_NAME, columns,
                RoiPoiContract.COLUMN_POIID + "=" + poi + " AND " +
                RoiPoiContract.COLUMN_ROIID + "=" + roi, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return false;
        else {
            ContentValues values = new ContentValues();
            values.put(RoiPoiContract.COLUMN_ROIID, toUpdate.getRoiID());
            values.put(RoiPoiContract.COLUMN_POIID, toUpdate.getPoiID());
            sqlDao.update(RoiPoiContract.TABLE_NAME, values, RoiPoiContract.COLUMN_POIID + "=" + poi
                    + " AND " + RoiPoiContract.COLUMN_ROIID + "=" + roi, null);
            return true;
        }
    }

}
