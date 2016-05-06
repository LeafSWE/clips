package com.leaf.clips.model.dataaccess.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SQLiteBuildingDaoTest extends InstrumentationTestCase {

    public class DatabaseTest implements BaseColumns {

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + BuildingContract.TABLE_NAME + " (" +
                        BuildingContract.COLUMN_ID + " INTEGER PRIMARY KEY," +
                        BuildingContract.COLUMN_NAME + " TEXT, "+
                        BuildingContract.COLUMN_ADDRESS + " TEXT, "+
                        BuildingContract.COLUMN_UUID + " TEXT, "+
                        BuildingContract.COLUMN_DESCRIPTION + " TEXT, "+
                        BuildingContract.COLUMN_MAPVERSION + " INTEGER, "+
                        BuildingContract.COLUMN_MAJOR + " INTEGER, "+
                        BuildingContract.COLUMN_OPENINGHOURS + " TEXT,"+
                        BuildingContract.COLUMN_MAPSIZE + " TEXT"+
                        " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + BuildingContract.TABLE_NAME;
    }

    public class MySQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "SQLiteBuildingDaoTest.db";
        private static final int DATABASE_VERSION = 1;

        public MySQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(MySQLiteHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + BuildingContract.TABLE_NAME);
            onCreate(db);
        }

    }

    private Context context;
    private SQLiteDatabase database;
    private SQLiteBuildingDao buildingDao;
    private MySQLiteHelper dbHelper;
    private String [] columns;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MySQLiteHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);
        database.execSQL(DatabaseTest.SQL_DELETE_ENTRIES);
        database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        buildingDao = new SQLiteBuildingDao(database);
        Assert.assertNotNull(buildingDao);
        columns = new String[]{
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
        Assert.assertNotNull(columns);
    }

    private void databaseInsert(ContentValues values){
        database.insert(BuildingContract.TABLE_NAME, null, values);
    }

    private ContentValues valuesWithId(int id) {
        ContentValues values = new ContentValues();
        values.put(BuildingContract.COLUMN_ADDRESS, "Address");
        values.put(BuildingContract.COLUMN_DESCRIPTION, "Description");
        values.put(BuildingContract.COLUMN_ID, id);
        values.put(BuildingContract.COLUMN_MAPVERSION, "2");
        values.put(BuildingContract.COLUMN_NAME, "Name");
        values.put(BuildingContract.COLUMN_OPENINGHOURS, "Opening Hours");
        values.put(BuildingContract.COLUMN_UUID, "UUID");
        values.put(BuildingContract.COLUMN_MAPSIZE, "5.2KB");
        values.put(BuildingContract.COLUMN_MAJOR, "666");
        return values;
    }

    private ContentValues valuesWithIdAndMajor(int id, int major) {
        ContentValues values = new ContentValues();
        values.put(BuildingContract.COLUMN_ADDRESS, "Address");
        values.put(BuildingContract.COLUMN_DESCRIPTION, "Description");
        values.put(BuildingContract.COLUMN_ID, id);
        values.put(BuildingContract.COLUMN_MAPVERSION, "2");
        values.put(BuildingContract.COLUMN_NAME, "Name");
        values.put(BuildingContract.COLUMN_OPENINGHOURS, "Opening Hours");
        values.put(BuildingContract.COLUMN_UUID, "UUID");
        values.put(BuildingContract.COLUMN_MAPSIZE, "5.2KB");
        values.put(BuildingContract.COLUMN_MAJOR, major);
        return values;
    }

    @Test
    public void testCursorToType() throws Exception {
        setUp();
        databaseInsert(valuesWithId(1));
        Cursor cursor = database.query(true, BuildingContract.TABLE_NAME, columns,
                BuildingContract.COLUMN_ID+"=\"1\"", null, null, null, null, null);
        BuildingTable buildingTable = buildingDao.cursorToType(cursor);
        Assert.assertEquals("Address", buildingTable.getAddress());
        Assert.assertEquals("Description", buildingTable.getDescription());
        Assert.assertEquals(1, buildingTable.getId());
        Assert.assertEquals(2, buildingTable.getVersion());
        Assert.assertEquals("Name", buildingTable.getName());
        Assert.assertEquals("Opening Hours", buildingTable.getOpeningHours());
        Assert.assertEquals("UUID", buildingTable.getUUID());
        Assert.assertEquals("5.2KB", buildingTable.getSize());
        Assert.assertEquals(666, buildingTable.getMajor());
    }

    @Test
    public void testDeleteBuilding() throws Exception {
        setUp();
        databaseInsert(valuesWithId(1));
        databaseInsert(valuesWithId(2));
        buildingDao.deleteBuilding(1);
        buildingDao.deleteBuilding(2);
        Assert.assertEquals(0, database.query(true, BuildingContract.TABLE_NAME, columns,
                BuildingContract.COLUMN_ID + "=1", null, null, null, null, null).getCount());
        Assert.assertEquals(0, database.query(true, BuildingContract.TABLE_NAME, columns,
                BuildingContract.COLUMN_ID+"=2", null, null, null, null, null).getCount());
    }

    @Test
    public void testFindAllBuildings() throws Exception {
        setUp();
        databaseInsert(valuesWithId(1));
        databaseInsert(valuesWithId(2));
        databaseInsert(valuesWithId(3));
        Collection<BuildingTable> buildingTables = buildingDao.findAllBuildings();
        Assert.assertEquals(3, buildingTables.size());
        int i = 1;
        for(BuildingTable buildingTable : buildingTables) {
            Assert.assertEquals(i, buildingTable.getId());
            i++;
        }
    }

    @Test
    public void testFindBuildingById() throws Exception {
        setUp();
        databaseInsert(valuesWithId(1));
        databaseInsert(valuesWithId(2));
        databaseInsert(valuesWithId(3));
        BuildingTable buildingTable = buildingDao.findBuildingById(2);
        Assert.assertEquals("Address", buildingTable.getAddress());
        Assert.assertEquals("Description", buildingTable.getDescription());
        Assert.assertEquals(2, buildingTable.getId());
        Assert.assertEquals(2, buildingTable.getVersion());
        Assert.assertEquals("Name", buildingTable.getName());
        Assert.assertEquals("Opening Hours", buildingTable.getOpeningHours());
        Assert.assertEquals("UUID", buildingTable.getUUID());
        Assert.assertEquals("5.2KB", buildingTable.getSize());
        Assert.assertEquals(666, buildingTable.getMajor());
    }

    @Test
    public void testFindBuildingByMajor() throws Exception {
        setUp();
        databaseInsert(valuesWithIdAndMajor(1, 666));
        databaseInsert(valuesWithIdAndMajor(2, 667));
        databaseInsert(valuesWithIdAndMajor(3, 668));
        BuildingTable buildingTable = buildingDao.findBuildingByMajor(668);
        Assert.assertEquals("Address", buildingTable.getAddress());
        Assert.assertEquals("Description", buildingTable.getDescription());
        Assert.assertEquals(3, buildingTable.getId());
        Assert.assertEquals(2, buildingTable.getVersion());
        Assert.assertEquals("Name", buildingTable.getName());
        Assert.assertEquals("Opening Hours", buildingTable.getOpeningHours());
        Assert.assertEquals("UUID", buildingTable.getUUID());
        Assert.assertEquals("5.2KB", buildingTable.getSize());
        Assert.assertEquals(668, buildingTable.getMajor());
    }

    @Test
    public void testInsertBuilding() throws Exception {
        setUp();
        BuildingTable buildingTable = new BuildingTable(1, "uuid", 666, "name", "description",
                "openingHours", "address", 2, "5.2KB");
        buildingDao.insertBuilding(buildingTable);
        Cursor cursor = database.query(true, BuildingContract.TABLE_NAME, columns,
                BuildingContract.COLUMN_ID+"=1", null, null, null, null, null);
        Assert.assertEquals(1, cursor.getCount());
    }

    @Test
    public void testIsBuildingMapPresent() throws Exception {
        setUp();
        Assert.assertFalse(buildingDao.isBuildingMapPresent(127));
        databaseInsert(valuesWithIdAndMajor(127, 127));
        Assert.assertTrue(buildingDao.isBuildingMapPresent(127));
    }

    @Test
    public void testUpdateBuilding() throws Exception {
        setUp();
        databaseInsert(valuesWithIdAndMajor(127, 127));
        buildingDao.updateBuilding(127, new BuildingTable(127, "updated", 128, "updated", "updated", "updated", "updated", 33, "updated"));
        Cursor cursor = database.query(true, BuildingContract.TABLE_NAME, columns,
                BuildingContract.COLUMN_ID+"=127", null, null, null, null, null);
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
        Assert.assertEquals(127, cursor.getInt(idIndex));
        Assert.assertEquals("updated", cursor.getString(uuidIndex));
        Assert.assertEquals(128, cursor.getInt(majorIndex));
        Assert.assertEquals("updated", cursor.getString(nameIndex));
        Assert.assertEquals("updated", cursor.getString(descriptionIndex));
        Assert.assertEquals("updated", cursor.getString(openingHoursIndex));
        Assert.assertEquals("updated", cursor.getString(addressIndex));
        Assert.assertEquals(33, cursor.getInt(mapVersionIndex));
        Assert.assertEquals("updated", cursor.getString(mapSizeIndex));
    }
}