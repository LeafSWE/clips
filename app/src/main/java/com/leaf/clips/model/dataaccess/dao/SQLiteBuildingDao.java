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
 *Classe che rappresenta un DAO per la tabella "Building" del database locale
 */

// TODO: 30/04/16 controllare su tracy che ci siano entrambe le interfacce
public class SQLiteBuildingDao implements BuildingDao, CursorConverter {

    /**
     * Permette l'accesso al database locale
     */
    private final SQLDao sqlDao;

    /**
     * Costruttore della classe SQLiteBuildingDao
     * @param database Il database locale
     */
    public SQLiteBuildingDao(SQLiteDatabase database) {
        sqlDao = new SQLDao(database);
    }

    /**
     * Metodo che viene utilizzato per convertire il risultato della query sulla tabella "Building" del database locale in un oggetto BuildingTable
     * @param cursor Risultato della query sulla tabella "Building" del database locale
     * @return  BuildingTable
     */
    @Override
    public BuildingTable cursorToType(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(BuildingContract.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(BuildingContract.COLUMN_NAME);
        int addressIndex = cursor.getColumnIndex(BuildingContract.COLUMN_ADDRESS);
        int descriptionIndex = cursor.getColumnIndex(BuildingContract.COLUMN_DESCRIPTION);
        int mapSizeIndex = cursor.getColumnIndex(BuildingContract.COLUMN_MAPSIZE);
        int mapVersionIndex = cursor.getColumnIndex(BuildingContract.COLUMN_MAPVERSION);
        int openingHoursIndex = cursor.getColumnIndex(BuildingContract.COLUMN_OPENINGHOURS);
        int uuidIndex = cursor.getColumnIndex(BuildingContract.COLUMN_UUID);
        int majorIndex = cursor.getColumnIndex(BuildingContract.COLUMN_MAJOR);
        cursor.moveToNext();
        return new BuildingTable(
                cursor.getInt(idIndex),
                cursor.getString(uuidIndex),
                cursor.getInt(majorIndex),
                cursor.getString(nameIndex),
                cursor.getString(descriptionIndex),
                cursor.getString(openingHoursIndex),
                cursor.getString(addressIndex),
                cursor.getInt(mapVersionIndex),
                cursor.getString(mapSizeIndex)
        );
    }

    /**
     * Metodo che permette la rimozione delle informazioni di un edificio dalla tabella "Building" del database locale
     * @param id Identificativo dell'edificio di cui rimuovere le informazioni dal database locale
     */
    @Override
    public void deleteBuilding(int id) {
        sqlDao.delete(
                BuildingContract.TABLE_NAME,
                BuildingContract.COLUMN_ID + "=" + id,
                null);
    }

    /**
     * Metodo che viene utilizzato per recuperare le informazioni di tutti gli edifici presenti nella tabella "Building" del database locale
     * @return  Collection<BuildingTable>
     */
    @Override
    public Collection<BuildingTable> findAllBuildings() {
        String [] columns = new String[]{
                BuildingContract.COLUMN_ADDRESS,
                BuildingContract.COLUMN_DESCRIPTION,
                BuildingContract.COLUMN_ID,
                BuildingContract.COLUMN_MAPVERSION,
                BuildingContract.COLUMN_NAME,
                BuildingContract.COLUMN_OPENINGHOURS,
                BuildingContract.COLUMN_MAPSIZE,
                BuildingContract.COLUMN_MAJOR,
                BuildingContract.COLUMN_UUID
        };
        Cursor cursor = sqlDao.query(true, BuildingContract.TABLE_NAME, columns,
                "1", null, null, null, null, null);
        int buildingNumber = cursor.getCount();
        PriorityQueue<BuildingTable> buildingTables = new PriorityQueue<>(buildingNumber,
                new Comparator<BuildingTable>() {
            @Override
            public int compare(BuildingTable lhs, BuildingTable rhs) {
                if (lhs.getId() > rhs.getId())
                    return 1;
                else if (lhs.getId() == rhs.getId())
                    return 0;
                else
                    return -1;
            }
        });
        for (int i = 0; i < buildingNumber; i++)
            buildingTables.add(cursorToType(cursor));
        return buildingTables;
    }

    /**
     * Metodo per recuperare le informazioni di un edificio dal database locale tramite il suo identificativo, sotto forma di oggetto BuildingTable
     * @param id Identificativo dell'edificio di cui recuperare le informazioni
     * @return  BuildingTable
     */
    @Override
    public BuildingTable findBuildingById(int id) {
        String [] columns = new String[]{
                BuildingContract.COLUMN_ADDRESS,
                BuildingContract.COLUMN_DESCRIPTION,
                BuildingContract.COLUMN_ID,
                BuildingContract.COLUMN_MAPVERSION,
                BuildingContract.COLUMN_NAME,
                BuildingContract.COLUMN_OPENINGHOURS,
                BuildingContract.COLUMN_MAPSIZE,
                BuildingContract.COLUMN_MAJOR,
                BuildingContract.COLUMN_UUID
        };
        Cursor cursor = sqlDao.query(true, BuildingContract.TABLE_NAME, columns,
                BuildingContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        return cursorToType(cursor);
    }

    /**
     * Metodo per recuperare le informazioni di un edificio dal database locale tramite il suo major, sotto forma di oggetto BuildingTable
     * @param major Major dell'edificio di cui recuperare le informazioni
     * @return  BuildingTable
     */
    @Override
    public BuildingTable findBuildingByMajor(int major) {
        String [] columns = new String[]{
                BuildingContract.COLUMN_ADDRESS,
                BuildingContract.COLUMN_DESCRIPTION,
                BuildingContract.COLUMN_ID,
                BuildingContract.COLUMN_MAPVERSION,
                BuildingContract.COLUMN_NAME,
                BuildingContract.COLUMN_OPENINGHOURS,
                BuildingContract.COLUMN_MAPSIZE,
                BuildingContract.COLUMN_MAJOR,
                BuildingContract.COLUMN_UUID
        };
        Cursor cursor = sqlDao.query(true, BuildingContract.TABLE_NAME, columns,
                BuildingContract.COLUMN_MAJOR + "=" + major, null, null, null, null, null);
        return cursorToType(cursor);
    }

    /**
     * Metodo che permette l'inserimento delle informazioni di un edificio in una entry della tabella "Building" del database locale
     * @param toInsert Oggetto di tipo BuildingTable che contiene le informazioni dell'edificio
     * @return  int
     */
    @Override
    public int insertBuilding(BuildingTable toInsert) {
        ContentValues values = new ContentValues();
        values.put(BuildingContract.COLUMN_ADDRESS, toInsert.getAddress());
        values.put(BuildingContract.COLUMN_DESCRIPTION, toInsert.getDescription());
        values.put(BuildingContract.COLUMN_ID, toInsert.getId());
        values.put(BuildingContract.COLUMN_MAPVERSION, toInsert.getVersion());
        values.put(BuildingContract.COLUMN_NAME, toInsert.getName());
        values.put(BuildingContract.COLUMN_OPENINGHOURS, toInsert.getOpeningHours());
        values.put(BuildingContract.COLUMN_UUID, toInsert.getUUID());
        values.put(BuildingContract.COLUMN_MAPSIZE, toInsert.getSize());
        values.put(BuildingContract.COLUMN_MAJOR, toInsert.getMajor());
        sqlDao.insert(BuildingContract.TABLE_NAME, values);
        return toInsert.getId();
    }

    /**
     * Metodo per verificare la presenza nel database locale delle informazioni di un edificio
     * @param major major dell'edificio
     * @return  boolean
     */
    public boolean isBuildingMapPresent(int major) {
        String [] columns = new String[]{
                BuildingContract.COLUMN_ADDRESS,
                BuildingContract.COLUMN_DESCRIPTION,
                BuildingContract.COLUMN_ID,
                BuildingContract.COLUMN_MAPVERSION,
                BuildingContract.COLUMN_NAME,
                BuildingContract.COLUMN_OPENINGHOURS,
                BuildingContract.COLUMN_MAPSIZE,
                BuildingContract.COLUMN_MAJOR,
                BuildingContract.COLUMN_UUID
        };
        Cursor cursor = sqlDao.query(true, BuildingContract.TABLE_NAME, columns,
                BuildingContract.COLUMN_MAJOR + "=" + major, null, null, null, null, null);
        return (cursor.getCount() > 0);
    }

    /**
     * Metodo per aggiornare le informazioni di un edificio nella tabella "Building" del database locale
     * @param id Identificativo dell'edificio di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate dell'edificio
     * @return  boolean
     */
    @Override
    public boolean updateBuilding(int id, BuildingTable toUpdate) {
        String [] columns = new String[]{
                BuildingContract.COLUMN_ADDRESS,
                BuildingContract.COLUMN_DESCRIPTION,
                BuildingContract.COLUMN_ID,
                BuildingContract.COLUMN_MAPVERSION,
                BuildingContract.COLUMN_NAME,
                BuildingContract.COLUMN_OPENINGHOURS,
                BuildingContract.COLUMN_MAPSIZE,
                BuildingContract.COLUMN_MAJOR,
                BuildingContract.COLUMN_UUID
        };
        Cursor cursor = sqlDao.query(true, BuildingContract.TABLE_NAME, columns,
                BuildingContract.COLUMN_ID + "=" + id, null, null, null, null, null);
        if (cursor.getCount() == 0)
            return false;
        else {
            ContentValues values = new ContentValues();
            values.put(BuildingContract.COLUMN_ADDRESS, toUpdate.getAddress());
            values.put(BuildingContract.COLUMN_DESCRIPTION, toUpdate.getDescription());
            values.put(BuildingContract.COLUMN_ID, toUpdate.getId());
            values.put(BuildingContract.COLUMN_MAPVERSION, toUpdate.getVersion());
            values.put(BuildingContract.COLUMN_NAME, toUpdate.getName());
            values.put(BuildingContract.COLUMN_OPENINGHOURS, toUpdate.getOpeningHours());
            values.put(BuildingContract.COLUMN_UUID, toUpdate.getUUID());
            values.put(BuildingContract.COLUMN_MAPSIZE, toUpdate.getSize());
            values.put(BuildingContract.COLUMN_MAJOR, toUpdate.getMajor());
            sqlDao.update(BuildingContract.TABLE_NAME, values, BuildingContract.COLUMN_ID + "="
                    + id, null);
            return true;
        }
    }

}

