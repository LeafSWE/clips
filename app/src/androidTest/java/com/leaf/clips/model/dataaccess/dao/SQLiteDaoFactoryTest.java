package com.leaf.clips.model.dataaccess.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SQLiteDaoFactoryTest extends InstrumentationTestCase {

    private Context context;
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public class MySQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "DatabaseTest.db";
        private static final int DATABASE_VERSION = 1;

        public MySQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {}

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MySQLiteHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);
    }

    @Test
    public void testGetBuildingDao() throws Exception {
        SQLiteBuildingDao buildingDao = new SQLiteBuildingDao(database);
        Assert.assertNotNull(buildingDao);
    }

    @Test
    public void testGetCategoryDao() throws Exception {
        SQLiteCategoryDao categoryDao = new SQLiteCategoryDao(database);
        Assert.assertNotNull(categoryDao);
    }

    @Test
    public void testGetEdgeDao() throws Exception {
        SQLiteEdgeDao edgeDao = new SQLiteEdgeDao(database);
        Assert.assertNotNull(edgeDao);
    }

    @Test
    public void testGetEdgeTypeDao() throws Exception {
        SQLiteEdgeTypeDao edgeTypeDao = new SQLiteEdgeTypeDao(database);
        Assert.assertNotNull(edgeTypeDao);
    }

    @Test
    public void testGetPhotoDao() throws Exception {
        SQLitePhotoDao photoDao = new SQLitePhotoDao(database);
        Assert.assertNotNull(photoDao);
    }

    @Test
    public void testGetPointOfInterestDao() throws Exception {
        SQLitePointOfInterestDao pointOfInterestDao = new SQLitePointOfInterestDao(database);
        Assert.assertNotNull(pointOfInterestDao);
    }

    @Test
    public void testGetRegionOfInterestDao() throws Exception {
        SQLiteRegionOfInterestDao regionOfInterestDao = new SQLiteRegionOfInterestDao(database);
        Assert.assertNotNull(regionOfInterestDao);
    }

    @Test
    public void testGetRoiPoiDao() throws Exception {
        SQLiteRoiPoiDao roiPoiDao = new SQLiteRoiPoiDao(database);
        Assert.assertNotNull(roiPoiDao);
    }
}