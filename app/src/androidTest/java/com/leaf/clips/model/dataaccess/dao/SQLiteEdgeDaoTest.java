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
public class SQLiteEdgeDaoTest extends InstrumentationTestCase {

    public class DatabaseTest implements BaseColumns {

        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
                EdgeContract.TABLE_NAME + " (" +
                EdgeContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                EdgeContract.COLUMN_ACTION + " TEXT," +
                EdgeContract.COLUMN_COORDINATE + " TEXT," +
                EdgeContract.COLUMN_DISTANCE + " DOUBLE," +
                EdgeContract.COLUMN_LONGDESCRIPTION + " TEXT," +
                EdgeContract.COLUMN_TYPEID + " INTEGER," +
                EdgeContract.COLUMN_STARTROI + " INTEGER," +
                EdgeContract.COLUMN_ENDROI + " INTEGER" +
                " )";
        public static final String SQL_CREATE_ROI_TABLE = "CREATE TABLE " +
                RegionOfInterestContract.TABLE_NAME + " (" +
                RegionOfInterestContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                RegionOfInterestContract.COLUMN_MAJOR + " INTEGER" +
                ")";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                EdgeContract.TABLE_NAME;
        public static final String SQL_DELETE_ROI_TABLE = "DROP TABLE IF EXISTS " +
                RegionOfInterestContract.TABLE_NAME;
    }

    public class MySQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "SQLiteEdgeDaoTest.db";
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
            db.execSQL("DROP TABLE IF EXISTS " + EdgeContract.TABLE_NAME);
            onCreate(db);
        }

    }

    private Context context;
    private SQLiteDatabase database;
    private SQLiteEdgeDao edgeDao;
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
        database.execSQL(DatabaseTest.SQL_CREATE_ROI_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        edgeDao = new SQLiteEdgeDao(database);
        Assert.assertNotNull(edgeDao);
        columns = new String[]{
                EdgeContract.COLUMN_ID,
                EdgeContract.COLUMN_ACTION,
                EdgeContract.COLUMN_COORDINATE,
                EdgeContract.COLUMN_DISTANCE,
                EdgeContract.COLUMN_LONGDESCRIPTION,
                EdgeContract.COLUMN_TYPEID,
                EdgeContract.COLUMN_STARTROI,
                EdgeContract.COLUMN_ENDROI
        };
        Assert.assertNotNull(columns);
    }

    private void databaseInsert(ContentValues values){
        database.insert(EdgeContract.TABLE_NAME, null, values);
    }

    private void databaseInsert(ContentValues[] values){
        database.insert(EdgeContract.TABLE_NAME, null, values[0]);
        database.insert(RegionOfInterestContract.TABLE_NAME, null, values[1]);
        database.insert(RegionOfInterestContract.TABLE_NAME, null, values[2]);
    }

    private ContentValues values(int id) {
        ContentValues values = new ContentValues();
        values.put(EdgeContract.COLUMN_ID, id);
        values.put(EdgeContract.COLUMN_ACTION, "action");
        values.put(EdgeContract.COLUMN_COORDINATE, "coordinate");
        values.put(EdgeContract.COLUMN_DISTANCE, 4.0);
        values.put(EdgeContract.COLUMN_LONGDESCRIPTION, "longDescription");
        values.put(EdgeContract.COLUMN_TYPEID, 1);
        values.put(EdgeContract.COLUMN_STARTROI, 2);
        values.put(EdgeContract.COLUMN_ENDROI, 3);
        return values;
    }

    private ContentValues[] valuesAndRois(int id, int startRoi, int endRoi, int major) {
        ContentValues[] values = {new ContentValues(), new ContentValues(), new ContentValues()};
        values[0].put(EdgeContract.COLUMN_ID, id);
        values[0].put(EdgeContract.COLUMN_ACTION, "action");
        values[0].put(EdgeContract.COLUMN_COORDINATE, "coordinate");
        values[0].put(EdgeContract.COLUMN_DISTANCE, 4.0);
        values[0].put(EdgeContract.COLUMN_LONGDESCRIPTION, "longDescription");
        values[0].put(EdgeContract.COLUMN_TYPEID, 1);
        values[0].put(EdgeContract.COLUMN_STARTROI, startRoi);
        values[0].put(EdgeContract.COLUMN_ENDROI, endRoi);
        values[1].put(RegionOfInterestContract.COLUMN_ID, startRoi);
        values[1].put(RegionOfInterestContract.COLUMN_MAJOR, major);
        values[2].put(RegionOfInterestContract.COLUMN_ID, endRoi);
        values[2].put(RegionOfInterestContract.COLUMN_MAJOR, major);
        return values;
    }

    @Test
    public void testCursorToType() throws Exception {
        setUp();
        databaseInsert(values(127));
        Cursor cursor = database.query(true, EdgeContract.TABLE_NAME, columns,
                EdgeContract.COLUMN_ID + "=\"127\"", null, null, null, null, null);
        EdgeTable edgeTable = edgeDao.cursorToType(cursor);
        Assert.assertEquals(127, edgeTable.getId());
        Assert.assertEquals("action", edgeTable.getAction());
        Assert.assertEquals("coordinate", edgeTable.getCoordinate());
        Assert.assertEquals(4.0, edgeTable.getDistance());
        Assert.assertEquals("longDescription", edgeTable.getLongDescription());
        Assert.assertEquals(1, edgeTable.getTypeId());
        Assert.assertEquals(2, edgeTable.getStartROI());
        Assert.assertEquals(3, edgeTable.getEndROI());
    }

    @Test
    public void testDeleteEdge() throws Exception {
        setUp();
        databaseInsert(values(1));
        databaseInsert(values(2));
        edgeDao.deleteEdge(1);
        edgeDao.deleteEdge(2);
        Cursor cursor = database.query(true, EdgeContract.TABLE_NAME, columns,
                EdgeContract.COLUMN_ID+"=\"1\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
        cursor = database.query(true, EdgeContract.TABLE_NAME, columns,
                EdgeContract.COLUMN_ID+"=\"2\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
    }

    @Test
    public void testFindAllEdgesOfBuilding() throws Exception {
        setUp();
        databaseInsert(valuesAndRois(1, 1, 2, 666));
        databaseInsert(valuesAndRois(2, 2, 3, 666));
        databaseInsert(valuesAndRois(3, 3, 4, 666));
        databaseInsert(valuesAndRois(4, 4, 5, 666));
        databaseInsert(valuesAndRois(5, 5, 6, 667));
        databaseInsert(valuesAndRois(6, 6, 7, 667));
        Collection<EdgeTable> edgeTables = edgeDao.findAllEdgesOfBuilding(666);
        Assert.assertEquals(5, edgeTables.size());
    }

    @Test
    public void testFindEdge() throws Exception {
        setUp();
        databaseInsert(values(1));
        databaseInsert(values(2));
        EdgeTable edgeTable = edgeDao.findEdge(2);
        Assert.assertEquals(2, edgeTable.getId());
        Assert.assertEquals("action", edgeTable.getAction());
        Assert.assertEquals("coordinate", edgeTable.getCoordinate());
        Assert.assertEquals(4.0, edgeTable.getDistance());
        Assert.assertEquals("longDescription", edgeTable.getLongDescription());
        Assert.assertEquals(1, edgeTable.getTypeId());
        Assert.assertEquals(2, edgeTable.getStartROI());
        Assert.assertEquals(3, edgeTable.getEndROI());
    }

    @Test
    public void testInsertEdge() throws Exception {
        setUp();
        EdgeTable edgeTable = new EdgeTable(127, 11, 22, 4.0, "coordinate", 33, "action", "longDescription");
        edgeDao.insertEdge(edgeTable);
        Cursor cursor = database.query(true, EdgeContract.TABLE_NAME, columns,
                EdgeContract.COLUMN_ID + "=" + 127, null, null, null, null, null);
        Assert.assertEquals(1, cursor.getCount());
    }

    @Test
    public void testUpdateEdge() throws Exception {
        setUp();
        databaseInsert(values(22));
        EdgeTable edgeTable = new EdgeTable(127, 11, 22, 4.0, "updated", 33, "updated", "updated");
        edgeDao.updateEdge(22, edgeTable);
        Cursor cursor = database.query(true, EdgeContract.TABLE_NAME, columns,
                EdgeContract.COLUMN_ID + "=" + 127, null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(EdgeContract.COLUMN_ID);
        int actionIndex = cursor.getColumnIndex(EdgeContract.COLUMN_ACTION);
        int coordinateIndex = cursor.getColumnIndex(EdgeContract.COLUMN_COORDINATE);
        int distanceIndex = cursor.getColumnIndex(EdgeContract.COLUMN_DISTANCE);
        int longDescriptionIndex = cursor.getColumnIndex(EdgeContract.COLUMN_LONGDESCRIPTION);
        int edgeTypeIndex = cursor.getColumnIndex(EdgeContract.COLUMN_TYPEID);
        int startROIIndex = cursor.getColumnIndex(EdgeContract.COLUMN_STARTROI);
        int endROIIndex = cursor.getColumnIndex(EdgeContract.COLUMN_ENDROI);
        Assert.assertEquals(1, cursor.getCount());
        cursor.moveToNext();
        Assert.assertEquals(127, cursor.getInt(idIndex));
        Assert.assertEquals("updated", cursor.getString(actionIndex));
        Assert.assertEquals("updated", cursor.getString(longDescriptionIndex));
        Assert.assertEquals("updated", cursor.getString(coordinateIndex));
        Assert.assertEquals(4.0, cursor.getDouble(distanceIndex));
        Assert.assertEquals(11, cursor.getInt(startROIIndex));
        Assert.assertEquals(22, cursor.getInt(endROIIndex));
        Assert.assertEquals(33, cursor.getInt(edgeTypeIndex));
    }
}