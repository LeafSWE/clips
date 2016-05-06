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
 *Classe che rappresenta un DAO per la tabella “Edge" del database locale
 */
public class SQLiteEdgeDao implements EdgeDao, CursorConverter {

    /**
     * Permette l'accesso al database locale
     */
    private final SQLDao sqlDao;

    /**
     * Costruttore della classe SQLiteEdgeDao
     * @param database Il database locale
     */
    public SQLiteEdgeDao(SQLiteDatabase database){
        sqlDao = new SQLDao(database);
    }

    /**
     * Metodo che viene utilizzato per convertire il risultato della query sulla tabella "Edge" del database locale in un oggetto EdgeTable
     * @param cursor Risultato della query sulla tabella "Edge" del database locale
     * @return  EdgeTable
     */
    @Override
    public EdgeTable cursorToType(Cursor cursor){
        int idIndex = cursor.getColumnIndex(EdgeContract.COLUMN_ID);
        int actionIndex = cursor.getColumnIndex(EdgeContract.COLUMN_ACTION);
        int coordinateIndex = cursor.getColumnIndex(EdgeContract.COLUMN_COORDINATE);
        int distanceIndex = cursor.getColumnIndex(EdgeContract.COLUMN_DISTANCE);
        int longDescriptionIndex = cursor.getColumnIndex(EdgeContract.COLUMN_LONGDESCRIPTION);
        int edgeTypeIndex = cursor.getColumnIndex(EdgeContract.COLUMN_TYPEID);
        int startROIIndex = cursor.getColumnIndex(EdgeContract.COLUMN_STARTROI);
        int endROIIndex = cursor.getColumnIndex(EdgeContract.COLUMN_ENDROI);
        cursor.moveToNext();
        return new EdgeTable(
                cursor.getInt(idIndex),
                cursor.getInt(startROIIndex),
                cursor.getInt(endROIIndex),
                cursor.getDouble(distanceIndex),
                cursor.getString(coordinateIndex),
                cursor.getInt(edgeTypeIndex),
                cursor.getString(actionIndex),
                cursor.getString(longDescriptionIndex));
    }

    /**
     * Metodo che permette la rimozione delle informazioni di un edificio dalla tabella "Edge" del database locale 
     * @param id Identificativo dell'arco di cui rimuovere le informazioni dal database locale
     */
    @Override
    public void deleteEdge(int id){
        sqlDao.delete(EdgeContract.TABLE_NAME,
                EdgeContract.COLUMN_ID + "=" + id,
                null);
    }

    /**
     * Metodo che viene utilizzato per recuperare le informazioni di tutti gli archi presenti nella tabella "Edge" del database locale
     * @param major Identificativo major dell'edificio di cui si vogliono recuperare tutti gli archi
     * @return  Collection<EdgeTable>
     */
    @Override
    public Collection<EdgeTable> findAllEdgesOfBuilding(int major){
        String[] columns = new String[]{
                EdgeContract.TABLE_NAME + "." + EdgeContract.COLUMN_ID,
                EdgeContract.TABLE_NAME + "." + EdgeContract.COLUMN_ACTION,
                EdgeContract.TABLE_NAME + "." + EdgeContract.COLUMN_COORDINATE,
                EdgeContract.TABLE_NAME + "." + EdgeContract.COLUMN_DISTANCE,
                EdgeContract.TABLE_NAME + "." + EdgeContract.COLUMN_LONGDESCRIPTION,
                EdgeContract.TABLE_NAME + "." + EdgeContract.COLUMN_TYPEID,
                EdgeContract.TABLE_NAME + "." + EdgeContract.COLUMN_STARTROI,
                EdgeContract.TABLE_NAME + "." + EdgeContract.COLUMN_ENDROI
        };
        StringBuilder stringBuilder = new StringBuilder(columns[0]);
        for (String string : columns) {
            stringBuilder.append(", " + string);
        }
        Cursor cursor = sqlDao.rawQuery("SELECT DISTINCT " + stringBuilder.toString() + " FROM " +
            EdgeContract.TABLE_NAME + ", " + RegionOfInterestContract.TABLE_NAME + " WHERE (" +
            EdgeContract.TABLE_NAME + "." + EdgeContract.COLUMN_STARTROI + "=" +
            RegionOfInterestContract.TABLE_NAME + "." + RegionOfInterestContract.COLUMN_ID +
            " OR " + EdgeContract.TABLE_NAME + "." + EdgeContract.COLUMN_ENDROI + "=" +
            RegionOfInterestContract.TABLE_NAME + "." + RegionOfInterestContract.COLUMN_ID + ") AND "
            + RegionOfInterestContract.COLUMN_MAJOR + "=" + major, null);
        int edgeNumber = cursor.getCount();
        PriorityQueue<EdgeTable> edgeTables = new PriorityQueue<>(edgeNumber,
                new Comparator<EdgeTable>() {
                    @Override
                    public int compare(EdgeTable lhs, EdgeTable rhs) {
                        if (lhs.getId() > rhs.getId())
                            return 1;
                        else if (lhs.getId() == rhs.getId())
                            return 0;
                        else
                            return -1;
                    }
                });
        for (int i = 0; i < edgeNumber; i++)
            edgeTables.add(cursorToType(cursor));
        return edgeTables;

    }

    /**
     * Metodo per recuperare le informazioni di un arco dal database locale tramite il suo identificativo, sotto forma di oggetto EdgeTable
     * @param id Identificativo dell'arco di cui recuperare le informazioni
     * @return  EdgeTable
     */
    @Override
    public EdgeTable findEdge(int id){
        String[] columns = new String[]{
                EdgeContract.COLUMN_ID,
                EdgeContract.COLUMN_ACTION,
                EdgeContract.COLUMN_COORDINATE,
                EdgeContract.COLUMN_DISTANCE,
                EdgeContract.COLUMN_LONGDESCRIPTION,
                EdgeContract.COLUMN_TYPEID,
                EdgeContract.COLUMN_STARTROI,
                EdgeContract.COLUMN_ENDROI
        };
        Cursor cursor = sqlDao.query(true, EdgeContract.TABLE_NAME, columns,
                EdgeContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        return cursorToType(cursor);
    }

    /**
     * Metodo che permette l'inserimento delle informazioni di un edificio in una entry della tabella "Edge" del database locale
     * @param toInsert Oggetto di tipo EdgeTable che contiene le informazioni dell'arco
     * @return  int
     */
    @Override
    public int insertEdge(EdgeTable toInsert){
        ContentValues values = new ContentValues();
        values.put(EdgeContract.COLUMN_ID, toInsert.getId());
        values.put(EdgeContract.COLUMN_ACTION, toInsert.getAction());
        values.put(EdgeContract.COLUMN_COORDINATE, toInsert.getCoordinate());
        values.put(EdgeContract.COLUMN_DISTANCE, toInsert.getDistance());
        values.put(EdgeContract.COLUMN_LONGDESCRIPTION, toInsert.getLongDescription());
        values.put(EdgeContract.COLUMN_TYPEID, toInsert.getTypeId());
        values.put(EdgeContract.COLUMN_STARTROI, toInsert.getStartROI());
        values.put(EdgeContract.COLUMN_ENDROI, toInsert.getEndROI());
        sqlDao.insert(EdgeContract.TABLE_NAME, values);
        return toInsert.getId();
    }

    /**
     * Metodo per aggiornare le informazioni di un edificio nella tabella "Edge" del database locale
     * @param id Identificativo dell'arco di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate dell'arco
     * @return  boolean
     */
    @Override
    public boolean updateEdge(int id, EdgeTable toUpdate){
        String[] columns = new String[]{
                EdgeContract.COLUMN_ID,
                EdgeContract.COLUMN_ACTION,
                EdgeContract.COLUMN_COORDINATE,
                EdgeContract.COLUMN_DISTANCE,
                EdgeContract.COLUMN_LONGDESCRIPTION,
                EdgeContract.COLUMN_TYPEID,
                EdgeContract.COLUMN_STARTROI,
                EdgeContract.COLUMN_ENDROI
        };
        Cursor cursor = sqlDao.query(true, EdgeContract.TABLE_NAME, columns,
                EdgeContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return false;
        else {
            ContentValues values = new ContentValues();
            values.put(EdgeContract.COLUMN_ID, toUpdate.getId());
            values.put(EdgeContract.COLUMN_ACTION, toUpdate.getAction());
            values.put(EdgeContract.COLUMN_COORDINATE, toUpdate.getCoordinate());
            values.put(EdgeContract.COLUMN_DISTANCE, toUpdate.getDistance());
            values.put(EdgeContract.COLUMN_LONGDESCRIPTION, toUpdate.getLongDescription());
            values.put(EdgeContract.COLUMN_TYPEID, toUpdate.getTypeId());
            values.put(EdgeContract.COLUMN_STARTROI, toUpdate.getStartROI());
            values.put(EdgeContract.COLUMN_ENDROI, toUpdate.getEndROI());
            sqlDao.update(EdgeContract.TABLE_NAME, values, EdgeContract.COLUMN_ID + "="
                    + id, null);
            return true;
        }
    }

}
