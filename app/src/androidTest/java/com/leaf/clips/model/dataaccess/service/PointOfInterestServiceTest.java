package com.leaf.clips.model.dataaccess.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.dao.PointOfInterestContract;
import com.leaf.clips.model.dataaccess.dao.RegionOfInterestContract;
import com.leaf.clips.model.dataaccess.dao.RemoteCategoryDao;
import com.leaf.clips.model.dataaccess.dao.RemotePointOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RemoteRoiPoiDao;
import com.leaf.clips.model.dataaccess.dao.RoiPoiContract;
import com.leaf.clips.model.dataaccess.dao.SQLiteCategoryDao;
import com.leaf.clips.model.dataaccess.dao.SQLitePointOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRoiPoiDao;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class PointOfInterestServiceTest {

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
                ") REFERENCES "+PointOfInterestContract.TABLE_NAME+
                "("+PointOfInterestContract.COLUMN_ID+"), "+
                " FOREIGN KEY ("+RoiPoiContract.COLUMN_ROIID+
                ") REFERENCES "+RegionOfInterestContract.TABLE_NAME+
                "("+RegionOfInterestContract.COLUMN_ID+"))";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                PointOfInterestContract.TABLE_NAME;
        public static final String SQL_DELETE_ROI_TABLE = "DROP TABLE IF EXISTS " +
                RegionOfInterestContract.TABLE_NAME;
        public static final String SQL_DELETE_ROIPOI_TABLE = "DROP TABLE IF EXISTS " +
                RoiPoiContract.TABLE_NAME;
    }

    private Context context;
    private MapsDbHelper dbHelper;
    private SQLiteDatabase database;
    private String[] columns;
    private SQLitePointOfInterestDao sqlitePoiDao;
    private RemotePointOfInterestDao remotePoiDao;
    private SQLiteRoiPoiDao sqLiteRoiPoiDao;
    private RemoteRoiPoiDao remoteRoiPoiDao;
    private SQLiteCategoryDao sqLiteCategoryDao;
    private RemoteCategoryDao remoteCategoryDao;
    private PointOfInterestService poiService;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MapsDbHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);
        database.execSQL(DatabaseTest.SQL_DELETE_ENTRIES);
        database.execSQL(DatabaseTest.SQL_DELETE_ROI_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_ROIPOI_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_ROI_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_ROIPOITABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        sqlitePoiDao = new SQLitePointOfInterestDao(database);
        Assert.assertNotNull(sqlitePoiDao);
        remotePoiDao = new RemotePointOfInterestDao();
        Assert.assertNotNull(remotePoiDao);
        sqLiteRoiPoiDao = new SQLiteRoiPoiDao(database);
        Assert.assertNotNull(sqLiteRoiPoiDao);
        remoteRoiPoiDao = new RemoteRoiPoiDao();
        Assert.assertNotNull(remoteRoiPoiDao);
        sqLiteCategoryDao = new SQLiteCategoryDao(database);
        Assert.assertNotNull(sqLiteCategoryDao);
        remoteCategoryDao = new RemoteCategoryDao();
        Assert.assertNotNull(remoteCategoryDao);
        poiService = new PointOfInterestService(sqlitePoiDao, remotePoiDao, sqLiteRoiPoiDao,
                remoteRoiPoiDao, sqLiteCategoryDao, remoteCategoryDao);
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

    /**
     * Viene testato che sia possibile eliminare un PointOfInterest dal database locale,
     * recuperarne uno o tutti quelli riguardanti un edificio, dato il major del suddetto edificio.
     */

    @Test
    public void shouldDeleteAPointOfInterest() throws Exception {
        setUp();
    }
}
