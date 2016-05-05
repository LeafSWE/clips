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
public class SQLiteRoiPoiDaoTest extends InstrumentationTestCase {

    public class DatabaseTest implements BaseColumns {

        public static final String SQL_CREATE_POI_TABLE = "CREATE TABLE " +
                PointOfInterestContract.TABLE_NAME + " (" +
                PointOfInterestContract.COLUMN_ID + " INTEGER PRIMARY KEY" +
                " )";
        public static final String SQL_CREATE_ROI_TABLE = "CREATE TABLE " +
                RegionOfInterestContract.TABLE_NAME + " (" +
                RegionOfInterestContract.COLUMN_ID + " INTEGER PRIMARY KEY" +
                " )";
        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
                RoiPoiContract.TABLE_NAME + " (" +
                RoiPoiContract.COLUMN_POIID + " INTEGER," +
                RoiPoiContract.COLUMN_ROIID + " INTEGER," +
                " PRIMARY KEY ("+RoiPoiContract.COLUMN_POIID+","
                +RoiPoiContract.COLUMN_ROIID+"), "+
                " FOREIGN KEY ("+RoiPoiContract.COLUMN_POIID+
                ") REFERENCES "+PointOfInterestContract.TABLE_NAME+"("+PointOfInterestContract.COLUMN_ID+"), "+
                " FOREIGN KEY ("+RoiPoiContract.COLUMN_ROIID+
                ") REFERENCES "+RegionOfInterestContract.TABLE_NAME+"("+RegionOfInterestContract.COLUMN_ID+"))";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                PointOfInterestContract.TABLE_NAME;
        public static final String SQL_DELETE_ROI_TABLE = "DROP TABLE IF EXISTS " +
                RegionOfInterestContract.TABLE_NAME;
        public static final String SQL_DELETE_ROIPOI_TABLE = "DROP TABLE IF EXISTS " +
                RoiPoiContract.TABLE_NAME;
    }

    public class MySQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "SQLiteRoiPoiDaoTest.db";
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
    private SQLiteRoiPoiDao roiPoiDao;
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
        database.execSQL(DatabaseTest.SQL_DELETE_ROI_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_ROIPOI_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_ROI_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_POI_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        roiPoiDao = new SQLiteRoiPoiDao(database);
        Assert.assertNotNull(roiPoiDao);
        columns = new String[]{
                RoiPoiContract.COLUMN_ROIID,
                RoiPoiContract.COLUMN_POIID
        };
        Assert.assertNotNull(columns);
    }

    private void databaseInsert(ContentValues values){
        database.insert(RoiPoiContract.TABLE_NAME, null, values);
    }
    private void databaseInsert(ContentValues[] values){
        database.insert(RoiPoiContract.TABLE_NAME, null, values[0]);
        database.insert(RegionOfInterestContract.TABLE_NAME, null, values[1]);
        database.insert(PointOfInterestContract.TABLE_NAME, null, values[2]);
    }

    private ContentValues values(int idPoi, int idRoi) {
        ContentValues values = new ContentValues();
        values.put(RoiPoiContract.COLUMN_ROIID, idRoi);
        values.put(RoiPoiContract.COLUMN_POIID, idPoi);
        return values;
    }

    private ContentValues[] valuesAndROIandPPOI(int idPoi, int idRoi) {
        ContentValues values[] = {new ContentValues(), new ContentValues(), new ContentValues()};
        values[0].put(RoiPoiContract.COLUMN_ROIID, idRoi);
        values[0].put(RoiPoiContract.COLUMN_POIID, idPoi);
        values[1].put(RegionOfInterestContract.COLUMN_ID, idRoi);
        values[2].put(PointOfInterestContract.COLUMN_ID, idPoi);
        return values;
    }

    @Test
    public void testCursorToType() throws Exception {
        setUp();
        databaseInsert(values(1, 2));
        Cursor cursor = database.query(true, RoiPoiContract.TABLE_NAME, columns,
                RoiPoiContract.COLUMN_POIID+"=\"1\"", null, null, null, null, null);
        RoiPoiTable roiPoiTable = roiPoiDao.cursorToType(cursor);
        Assert.assertEquals(2, roiPoiTable.getRoiID());
        Assert.assertEquals(1, roiPoiTable.getPoiID());
    }

    @Test
    public void testDeleteRoiPoisWherePoi() throws Exception {
        setUp();
        databaseInsert(values(1, 3));
        databaseInsert(values(1, 2));
        databaseInsert(values(1, 1));
        databaseInsert(values(2, 4));
        databaseInsert(values(3, 1));
        roiPoiDao.deleteRoiPoisWherePoi(1);
        Cursor cursor = database.query(true, RoiPoiContract.TABLE_NAME, columns,
                RoiPoiContract.COLUMN_POIID+"=\"1\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
        cursor = database.query(true, RoiPoiContract.TABLE_NAME, columns,
                "1", null, null, null, null, null);
        Assert.assertEquals(2, cursor.getCount());
    }

    @Test
    public void testDeleteRoiPoisWhereRoi() throws Exception {
        setUp();
        databaseInsert(values(3, 1));
        databaseInsert(values(2, 1));
        databaseInsert(values(1, 1));
        databaseInsert(values(4, 2));
        databaseInsert(values(1, 3));
        roiPoiDao.deleteRoiPoisWhereRoi(1);
        Cursor cursor = database.query(true, RoiPoiContract.TABLE_NAME, columns,
                RoiPoiContract.COLUMN_ROIID+"=1", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
        cursor = database.query(true, RoiPoiContract.TABLE_NAME, columns,
                "1", null, null, null, null, null);
        Assert.assertEquals(2, cursor.getCount());
    }

    @Test
    public void testFindAllPointsWithRoi() throws Exception {
        setUp();
        databaseInsert(values(3, 1));
        databaseInsert(values(2, 1));
        databaseInsert(values(1, 1));
        databaseInsert(values(4, 2));
        databaseInsert(values(1, 3));
        int [] points = roiPoiDao.findAllPointsWithRoi(1);
        Assert.assertEquals(3, points.length);
    }

    @Test
    public void testFindAllRegionsWithPoi() throws Exception {
        setUp();
        databaseInsert(values(1, 3));
        databaseInsert(values(1, 2));
        databaseInsert(values(1, 1));
        databaseInsert(values(2, 4));
        databaseInsert(values(3, 1));
        int [] regions = roiPoiDao.findAllRegionsWithPoi(1);
        Assert.assertEquals(3, regions.length);
    }

    @Test
    public void testInsertRoiPoi() throws Exception {
        setUp();
        RoiPoiTable roiPoiTable = new RoiPoiTable(44, 45);
        Cursor cursor = database.query(true, RoiPoiContract.TABLE_NAME, columns,
                RoiPoiContract.COLUMN_POIID + "=" + 45 + " AND " + RoiPoiContract.COLUMN_ROIID
                + "=" + 44, null, null, null, null, null);
        roiPoiDao.insertRoiPoi(roiPoiTable);
        Assert.assertEquals(1, cursor.getCount());
    }

    @Test
    public void testUpdateRoiPoi() throws Exception {
        setUp();
        databaseInsert(values(23, 24));
        RoiPoiTable roiPoiTable = new RoiPoiTable(88,99);
        roiPoiDao.updateRoiPoi(23, 24, roiPoiTable);
        Cursor cursor = database.query(true, RoiPoiContract.TABLE_NAME, columns,
                RoiPoiContract.COLUMN_POIID + "=" + 99 + " AND " + RoiPoiContract.COLUMN_ROIID
                        + "=" + 88, null, null, null, null, null);
        Assert.assertEquals(1, cursor.getCount());
    }
}