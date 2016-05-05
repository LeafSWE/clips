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
public class SQLitePointOfInterestDaoTest extends InstrumentationTestCase {

    public class DatabaseTest implements BaseColumns {

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + PointOfInterestContract.TABLE_NAME + " (" +
                        PointOfInterestContract.COLUMN_ID + " INTEGER PRIMARY KEY," +
                        PointOfInterestContract.COLUMN_NAME + " TEXT, "+
                        PointOfInterestContract.COLUMN_DESCRIPTION + " TEXT, "+
                        PointOfInterestContract.COLUMN_CATEGORYID + " INTEGER "+
                        " )";
        public static final String SQL_CREATE_ROI_TABLE = "CREATE TABLE " +
                        RegionOfInterestContract.TABLE_NAME + " (" +
                        RegionOfInterestContract.COLUMN_ID + " INTEGER PRIMARY KEY," +
                        RegionOfInterestContract.COLUMN_MAJOR + " INTEGER " +
                        " )";
        public static final String SQL_CREATE_ROIPOITABLE = "CREATE TABLE " +
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

        private static final String DATABASE_NAME = "SQLitePointOfInterestDaoTest.db";
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
    private SQLitePointOfInterestDao pointOfInterestDao;
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
        database.execSQL(DatabaseTest.SQL_CREATE_ROIPOITABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        pointOfInterestDao = new SQLitePointOfInterestDao(database);
        Assert.assertNotNull(pointOfInterestDao);
        columns = new String[]{
                PointOfInterestContract.COLUMN_ID,
                PointOfInterestContract.COLUMN_NAME,
                PointOfInterestContract.COLUMN_DESCRIPTION,
                PointOfInterestContract.COLUMN_CATEGORYID
        };
        Assert.assertNotNull(columns);
    }

    private void databaseInsert(ContentValues values){
        database.insert(PointOfInterestContract.TABLE_NAME, null, values);
    }

    private void databaseInsert(ContentValues[] values){
        database.insert(PointOfInterestContract.TABLE_NAME, null, values[0]);
        database.insert(RegionOfInterestContract.TABLE_NAME, null, values[1]);
        database.insert(RoiPoiContract.TABLE_NAME, null, values[2]);
    }

    private ContentValues valuesWithId(int id) {
        ContentValues values = new ContentValues();
        values.put(PointOfInterestContract.COLUMN_ID, id);
        values.put(PointOfInterestContract.COLUMN_NAME, "Name");
        values.put(PointOfInterestContract.COLUMN_DESCRIPTION, "Description");
        values.put(PointOfInterestContract.COLUMN_CATEGORYID, "2");
        return values;
    }

    private ContentValues[] valuesWithIdAndRoi(int id, int idRoi, int major) {
        ContentValues[] values = {new ContentValues(), new ContentValues(), new ContentValues()};
        values[0].put(PointOfInterestContract.COLUMN_ID, id);
        values[0].put(PointOfInterestContract.COLUMN_NAME, "Name");
        values[0].put(PointOfInterestContract.COLUMN_DESCRIPTION, "Description");
        values[0].put(PointOfInterestContract.COLUMN_CATEGORYID, "2");
        values[1].put(RegionOfInterestContract.COLUMN_ID, idRoi);
        values[1].put(RegionOfInterestContract.COLUMN_MAJOR, major);
        values[2].put(RoiPoiContract.COLUMN_POIID, id);
        values[2].put(RoiPoiContract.COLUMN_ROIID, idRoi);
        return values;
    }

    @Test
    public void testCursorToType() throws Exception {
        setUp();
        databaseInsert(valuesWithId(1));
        Cursor cursor = database.query(true, PointOfInterestContract.TABLE_NAME, columns,
                PointOfInterestContract.COLUMN_ID+"=\"1\"", null, null, null, null, null);
        PointOfInterestTable pointOfInterestTable = pointOfInterestDao.cursorToType(cursor);
        Assert.assertEquals("Description", pointOfInterestTable.getDescription());
        Assert.assertEquals(1, pointOfInterestTable.getId());
        Assert.assertEquals("Name", pointOfInterestTable.getName());
        Assert.assertEquals(2, pointOfInterestTable.getCategoryId());
    }

    @Test
    public void testDeletePointOfInterest() throws Exception {
        setUp();
        databaseInsert(valuesWithId(1));
        databaseInsert(valuesWithId(2));
        pointOfInterestDao.deletePointOfInterest(1);
        pointOfInterestDao.deletePointOfInterest(2);
        Assert.assertEquals(0, database.query(true, PointOfInterestContract.TABLE_NAME, columns,
                PointOfInterestContract.COLUMN_ID + "=1", null, null, null, null, null).getCount());
        Assert.assertEquals(0, database.query(true, PointOfInterestContract.TABLE_NAME, columns,
                PointOfInterestContract.COLUMN_ID+"=2", null, null, null, null, null).getCount());
    }

    @Test
    public void testFindAllPointsWithMajor() throws Exception {
        setUp();
        databaseInsert(valuesWithIdAndRoi(1, 1, 668));
        databaseInsert(valuesWithIdAndRoi(2, 2, 667));
        databaseInsert(valuesWithIdAndRoi(3, 3, 667));
        databaseInsert(valuesWithIdAndRoi(4, 3, 667));
        databaseInsert(valuesWithIdAndRoi(5, 4, 666));
        Collection<PointOfInterestTable> pointOfInterestTables = pointOfInterestDao.findAllPointsWithMajor(667);
        Assert.assertEquals(3, pointOfInterestTables.size());
        int i = 2;
        for(PointOfInterestTable pointOfInterestTable : pointOfInterestTables) {
            Assert.assertEquals(i, pointOfInterestTable.getId());
            i++;
        }
    }

    @Test
    public void testFindPointOfInterest() throws Exception {
        setUp();
        databaseInsert(valuesWithId(1));
        databaseInsert(valuesWithId(2));
        databaseInsert(valuesWithId(3));
        PointOfInterestTable pointOfInterestTable = pointOfInterestDao.findPointOfInterest(2);
        Assert.assertEquals("Description", pointOfInterestTable.getDescription());
        Assert.assertEquals(2, pointOfInterestTable.getId());
        Assert.assertEquals("Name", pointOfInterestTable.getName());
        Assert.assertEquals(2, pointOfInterestTable.getCategoryId());
    }

    @Test
    public void testInsertPointOfInterest() throws Exception {
        setUp();
        PointOfInterestTable pointOfInterestTable = new PointOfInterestTable("description", 1, "name", 2);
        pointOfInterestDao.insertPointOfInterest(pointOfInterestTable);
        Cursor cursor = database.query(true, PointOfInterestContract.TABLE_NAME, columns,
                PointOfInterestContract.COLUMN_ID+"=1", null, null, null, null, null);
        Assert.assertEquals(1, cursor.getCount());
    }

    @Test
    public void testUpdatePointOfInterest() throws Exception {
        setUp();
        databaseInsert(valuesWithId(127));
        PointOfInterestTable pointOfInterestTable = new PointOfInterestTable("updated", 127, "updated", 33);
        Cursor cursor = database.query(true, PointOfInterestContract.TABLE_NAME, columns,
                PointOfInterestContract.COLUMN_ID + "=127", null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(PointOfInterestContract.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(PointOfInterestContract.COLUMN_NAME);
        int descriptionIndex = cursor.getColumnIndex(PointOfInterestContract.COLUMN_DESCRIPTION);
        int categoryIndex = cursor.getColumnIndex(PointOfInterestContract.COLUMN_CATEGORYID);
        cursor.moveToNext();
        Assert.assertEquals("updated", pointOfInterestTable.getDescription());
        Assert.assertEquals(127, pointOfInterestTable.getId());
        Assert.assertEquals("updated", pointOfInterestTable.getName());
        Assert.assertEquals(33, pointOfInterestTable.getCategoryId());
    }
}