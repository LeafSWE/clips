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
public class SQLiteRegionOfInterestDaoTest extends InstrumentationTestCase {

    public class DatabaseTest implements BaseColumns {

        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
                RegionOfInterestContract.TABLE_NAME + " (" +
                RegionOfInterestContract.COLUMN_ID + " INTEGER PRIMARY KEY," +
                RegionOfInterestContract.COLUMN_MAJOR + " INTEGER, " +
                RegionOfInterestContract.COLUMN_MINOR + " INTEGER, " +
                RegionOfInterestContract.COLUMN_UUID + " TEXT " +
                " )";

        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                RegionOfInterestContract.TABLE_NAME;

    }

    public class MySQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "SQLiteRegionOfInterestDaoTest.db";
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
            db.execSQL("DROP TABLE IF EXISTS " + PointOfInterestContract.TABLE_NAME);
            onCreate(db);
        }

    }

    private Context context;
    private SQLiteDatabase database;
    private SQLiteRegionOfInterestDao regionOfInterestDao;
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
        regionOfInterestDao = new SQLiteRegionOfInterestDao(database);
        Assert.assertNotNull(regionOfInterestDao);
        columns = new String[]{
                RegionOfInterestContract.COLUMN_ID,
                RegionOfInterestContract.COLUMN_MAJOR,
                RegionOfInterestContract.COLUMN_MINOR,
                RegionOfInterestContract.COLUMN_UUID
        };
        Assert.assertNotNull(columns);
    }

    private void databaseInsert(ContentValues values){
        database.insert(RegionOfInterestContract.TABLE_NAME, null, values);
    }

    private ContentValues valuesWithId(int id) {
        ContentValues values = new ContentValues();
        values.put(RegionOfInterestContract.COLUMN_ID, id);
        values.put(RegionOfInterestContract.COLUMN_UUID, "UUID");
        values.put(RegionOfInterestContract.COLUMN_MAJOR, "666");
        values.put(RegionOfInterestContract.COLUMN_MINOR, "2");
        return values;
    }

    private ContentValues valuesWithIdAndMajor(int id, int major) {
        ContentValues values = new ContentValues();
        values.put(RegionOfInterestContract.COLUMN_ID, id);
        values.put(RegionOfInterestContract.COLUMN_UUID, "UUID");
        values.put(RegionOfInterestContract.COLUMN_MAJOR, major);
        values.put(RegionOfInterestContract.COLUMN_MINOR, "2");
        return values;
    }

    @Test
    public void testCursorToType() throws Exception {
        setUp();
        databaseInsert(valuesWithId(1));
        Cursor cursor = database.query(true, RegionOfInterestContract.TABLE_NAME, columns,
                RegionOfInterestContract.COLUMN_ID+"=\"1\"", null, null, null, null, null);
        RegionOfInterestTable regionOfInterestTable = regionOfInterestDao.cursorToType(cursor);
        Assert.assertEquals("UUID", regionOfInterestTable.getUUID());
        Assert.assertEquals(1, regionOfInterestTable.getId());
        Assert.assertEquals(2, regionOfInterestTable.getMinor());
        Assert.assertEquals(666, regionOfInterestTable.getMajor());
    }

    @Test
    public void testDeleteRegionOfInterest() throws Exception {
        setUp();
        databaseInsert(valuesWithId(1));
        databaseInsert(valuesWithId(2));
        regionOfInterestDao.deleteRegionOfInterest(1);
        regionOfInterestDao.deleteRegionOfInterest(2);
        Assert.assertEquals(0, database.query(true, RegionOfInterestContract.TABLE_NAME, columns,
                RegionOfInterestContract.COLUMN_ID + "=1", null, null, null, null, null).getCount());
        Assert.assertEquals(0, database.query(true, RegionOfInterestContract.TABLE_NAME, columns,
                RegionOfInterestContract.COLUMN_ID + "=2", null, null, null, null, null).getCount());
    }

    @Test
    public void testFindAllRegionsWithMajor() throws Exception {
        setUp();
        databaseInsert(valuesWithIdAndMajor(1, 1));
        databaseInsert(valuesWithIdAndMajor(2, 2));
        databaseInsert(valuesWithIdAndMajor(3, 2));
        databaseInsert(valuesWithIdAndMajor(4, 2));
        databaseInsert(valuesWithIdAndMajor(5, 2));
        databaseInsert(valuesWithIdAndMajor(6, 3));
        databaseInsert(valuesWithIdAndMajor(7, 4));
        Collection<RegionOfInterestTable> regionOfInterestTables = regionOfInterestDao.findAllRegionsWithMajor(2);
        Assert.assertEquals(4, regionOfInterestTables.size());
        int i = 2;
        for(RegionOfInterestTable regionOfInterestTable : regionOfInterestTables) {
            Assert.assertEquals(i, regionOfInterestTable.getId());
            i++;
        }
    }

    @Test
    public void testFindRegionOfInterest() throws Exception {
        setUp();
        databaseInsert(valuesWithId(1));
        databaseInsert(valuesWithId(2));
        databaseInsert(valuesWithId(3));
        RegionOfInterestTable regionOfInterestTable = regionOfInterestDao.findRegionOfInterest(3);
        Assert.assertEquals("UUID", regionOfInterestTable.getUUID());
        Assert.assertEquals(3, regionOfInterestTable.getId());
        Assert.assertEquals(2, regionOfInterestTable.getMinor());
        Assert.assertEquals(666, regionOfInterestTable.getMajor());
    }

    @Test
    public void testInsertRegionOfInterest() throws Exception {
        setUp();
        RegionOfInterestTable regionOfInterestTable = new RegionOfInterestTable(127, "UUID", 666, 100);
        regionOfInterestDao.insertRegionOfInterest(regionOfInterestTable);
        Cursor cursor = database.query(true, RegionOfInterestContract.TABLE_NAME, columns,
                PointOfInterestContract.COLUMN_ID+"=127", null, null, null, null, null);
        Assert.assertEquals(1, cursor.getCount());
    }

    @Test
    public void testUpdateRegionOfInterest() throws Exception {
        setUp();
        databaseInsert(valuesWithId(127));
        RegionOfInterestTable regionOfInterestTable = new RegionOfInterestTable(127, "updated", 34, 33);
        Cursor cursor = database.query(true, RegionOfInterestContract.TABLE_NAME, columns,
                RegionOfInterestContract.COLUMN_ID + "=127", null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(RegionOfInterestContract.COLUMN_ID);
        int uuidIndex = cursor.getColumnIndex(RegionOfInterestContract.COLUMN_UUID);
        int majorIndex = cursor.getColumnIndex(RegionOfInterestContract.COLUMN_MAJOR);
        int minorIndex = cursor.getColumnIndex(RegionOfInterestContract.COLUMN_MINOR);
        cursor.moveToNext();
        Assert.assertEquals("updated", regionOfInterestTable.getUUID());
        Assert.assertEquals(127, regionOfInterestTable.getId());
        Assert.assertEquals(33, regionOfInterestTable.getMinor());
        Assert.assertEquals(34, regionOfInterestTable.getMajor());
    }
}