package com.leaf.clips.model.dataaccess.service;

import com.google.gson.JsonObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.dataaccess.dao.CategoryContract;
import com.leaf.clips.model.dataaccess.dao.CategoryTable;
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
import com.leaf.clips.model.navigator.graph.area.PointOfInterestImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestInformation;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterestImp;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class PointOfInterestServiceTest {

    public class DatabaseTest implements BaseColumns {

        public static final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " +
                CategoryContract.TABLE_NAME + " (" +
                CategoryContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CategoryContract.COLUMN_DESCRIPTION + " TEXT" +
                " )";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + PointOfInterestContract.TABLE_NAME + " (" +
                        PointOfInterestContract.COLUMN_ID + " INTEGER PRIMARY KEY," +
                        PointOfInterestContract.COLUMN_NAME + " TEXT, "+
                        PointOfInterestContract.COLUMN_DESCRIPTION + " TEXT, "+
                        PointOfInterestContract.COLUMN_CATEGORYID + " INTEGER, "+
                        "FOREIGN KEY ("+PointOfInterestContract.COLUMN_CATEGORYID+
                        ") REFERENCES "+CategoryContract.TABLE_NAME+
                        "("+CategoryContract.COLUMN_ID+"))";
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
        public static final String SQL_DELETE_CATEGORY_TABLE = "DROP TABLE IF EXISTS " +
                CategoryContract.TABLE_NAME;
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
    private String[] categoryColumns;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MapsDbHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);
        database.execSQL(DatabaseTest.SQL_DELETE_ROIPOI_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_ENTRIES);
        database.execSQL(DatabaseTest.SQL_DELETE_CATEGORY_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_ROI_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_ROI_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_CATEGORY_TABLE);
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
        categoryColumns = new String[] {
                CategoryContract.COLUMN_ID,
                CategoryContract.COLUMN_DESCRIPTION
        };
        Assert.assertNotNull(columns);
    }

    private void databaseInsert(ContentValues values){
        database.insert(PointOfInterestContract.TABLE_NAME, null, values);
    }

    private void databaseInsert(ContentValues[] values){
        database.insert(CategoryContract.TABLE_NAME, null, values[0]);
        database.insert(PointOfInterestContract.TABLE_NAME, null, values[1]);
        database.insert(RegionOfInterestContract.TABLE_NAME, null, values[2]);
        database.insert(RoiPoiContract.TABLE_NAME, null, values[3]);
    }

    private void databaseInsertNoRoiPoi(ContentValues[] values){
        database.insert(CategoryContract.TABLE_NAME, null, values[0]);
        database.insert(PointOfInterestContract.TABLE_NAME, null, values[1]);
        database.insert(RegionOfInterestContract.TABLE_NAME, null, values[2]);
    }

    private ContentValues valuesWithId(int id) {
        ContentValues values = new ContentValues();
        values.put(PointOfInterestContract.COLUMN_ID, id);
        values.put(PointOfInterestContract.COLUMN_NAME, "Name");
        values.put(PointOfInterestContract.COLUMN_DESCRIPTION, "Description");
        values.put(PointOfInterestContract.COLUMN_CATEGORYID, "2");
        return values;
    }

    private ContentValues[] valuesWithIdAndRoiAndCategory(int id, String name, int idRoi, int major,
                                                          int categoryId, String category) {
        ContentValues[] values = {new ContentValues(), new ContentValues(), new ContentValues(),
                new ContentValues()};
        values[0].put(CategoryContract.COLUMN_ID, categoryId);
        values[0].put(CategoryContract.COLUMN_DESCRIPTION, category);
        values[1].put(PointOfInterestContract.COLUMN_ID, id);
        values[1].put(PointOfInterestContract.COLUMN_NAME, name);
        values[1].put(PointOfInterestContract.COLUMN_DESCRIPTION, "Description");
        values[1].put(PointOfInterestContract.COLUMN_CATEGORYID, categoryId);
        values[2].put(RegionOfInterestContract.COLUMN_ID, idRoi);
        values[2].put(RegionOfInterestContract.COLUMN_MAJOR, major);
        values[3].put(RoiPoiContract.COLUMN_POIID, id);
        values[3].put(RoiPoiContract.COLUMN_ROIID, idRoi);

        return values;
    }

    private ContentValues[] valuesWithPoiAndRoiButNotRoiPoi(int id, String name, int idRoi, int
            major, int categoryId, String category) {
        ContentValues[] values = {new ContentValues(), new ContentValues(), new ContentValues()};
        values[0].put(CategoryContract.COLUMN_ID, categoryId);
        values[0].put(CategoryContract.COLUMN_DESCRIPTION, category);
        values[1].put(PointOfInterestContract.COLUMN_ID, id);
        values[1].put(PointOfInterestContract.COLUMN_NAME, name);
        values[1].put(PointOfInterestContract.COLUMN_DESCRIPTION, "Description");
        values[1].put(PointOfInterestContract.COLUMN_CATEGORYID, categoryId);
        values[2].put(RegionOfInterestContract.COLUMN_ID, idRoi);
        values[2].put(RegionOfInterestContract.COLUMN_MAJOR, major);

        return values;
    }

    /**
     * Viene testato che sia possibile eliminare un PointOfInterest dal database locale,
     * recuperarne uno o tutti quelli riguardanti un edificio, dato il major del suddetto edificio.
     */

    @Test
    public void shouldDeleteAPointOfInterest() throws Exception {
        setUp();
        int poiId = 1;
        databaseInsert(valuesWithId(poiId));
        poiService.deletePointOfInterest(poiId);

        Assert.assertEquals(0, database.query(true, PointOfInterestContract.TABLE_NAME, columns,
                PointOfInterestContract.COLUMN_ID + "=1", null, null, null, null, null).getCount());
    }

    @Test
    public void shouldRetrieveAPointOfInterest() throws Exception {
        setUp();
        int poiId = 1;
        String myPoiName = "myPointOfInterest";
        int categoryId = 12;
        String category = "myCategory";
        databaseInsert(valuesWithIdAndRoiAndCategory(poiId, myPoiName, 1, 666, categoryId,
                category));

        PointOfInterest poi = poiService.findPointOfInterest(poiId);
        Assert.assertEquals(myPoiName, poi.getName());
        Assert.assertEquals(category, poi.getCategory());
    }

    @Test
    public void shouldRetrieveAllPOIsOfABuilding() throws Exception {
        setUp();
        int major = 666;
        int poi1_id = 1;
        String poi1_name = "poi1";
        int poi2_id = 2;
        String poi2_name = "poi2";
        databaseInsert(valuesWithIdAndRoiAndCategory(poi1_id, poi1_name, 1, major, 1, "Aula"));
        databaseInsert(valuesWithIdAndRoiAndCategory(poi2_id, poi2_name, 1, major, 1, "Aula"));

        Collection<PointOfInterest> pois = poiService.findAllPointsWithMajor(major);
        int i = 1;
        for (PointOfInterest poi: pois) {
            Assert.assertEquals(poi.getId(), i);
            Assert.assertEquals(poi.getName(), "poi"+i);
            i++;
        }
    }

    /**
     * Viene testato che, dato un oggetto JsonObject che possiede gli stessi valori di un oggetto
     * PointOfInterestTable, sia possibile costruire un oggetto PointOfInterestTable e
     * inserirlo nel database locale.
     */
    @Test
    public void shouldCreateRegionOfInterestTableAndInsertItInTheDB() throws Exception {
        setUp();
        int poiId = 1;
        String poiName = "MyPointOfInterest";
        String poiDescription = "Description";
        int categoryId = 2;
        String category = "MyCategory";
        databaseInsert(valuesWithIdAndRoiAndCategory(2, "AnotherPOI", 2, 666, categoryId, category));
        // Now the category exists
        JsonObject js = new JsonObject();
        js.addProperty("description", poiDescription);
        js.addProperty("id", poiId);
        js.addProperty("name", poiName);
        js.addProperty("categoryId", categoryId);
        poiService.convertAndInsert(js);

        PointOfInterest poi = poiService.findPointOfInterest(1);
        Assert.assertEquals(poi.getId(), poiId);
        Assert.assertEquals(poi.getName(), poiName);
        Assert.assertEquals(poi.getDescription(), poiDescription);
        Assert.assertEquals(poi.getCategory(), category);
    }

    /**
     * Viene testato che, dato un oggetto JsonObject che possiede gli stessi valori di un
     * oggetto CategoryTable, sia possibile costruire un oggetto CategoryTable e
     * inserirlo nel database locale.
     */
    @Test
    public void shouldCreateACategoryTableAndInsertItInTheDB() throws Exception {
        setUp();
        int categoryId = 1;
        String description = "Aule";
        JsonObject js = new JsonObject();
        js.addProperty("id", categoryId);
        js.addProperty("description", description);
        poiService.convertAndInsertCategory(js);

        CategoryTable table = sqLiteCategoryDao.findCategory(categoryId);
        Assert.assertEquals(table.getDescription(), description);
    }

    /**
     * Viene testato che, dato un oggetto JsonObject che possiede gli stessi valori di un oggetto
     * RoiPoiTable, sia possibile costruire un oggetto RoiPoiTable e inserirlo nel database locale.
     */
    @Test
    public void shouldCreateARoiPoiTableAndInsertItInTheDB() throws Exception {
        setUp();
        int poiID = 1;
        int roiID = 2;
        // this method inserts the POI and the ROI without the ROIPOI association
        databaseInsertNoRoiPoi(valuesWithPoiAndRoiButNotRoiPoi(poiID, "myPoi", roiID, 666, 12, "category"));
        // Now I can insert the RoiPoi
        JsonObject js = new JsonObject();
        js.addProperty("roiid", roiID);
        js.addProperty("poiid", poiID);
        poiService.convertAndInsertRoiPoi(js);

        int[] pois = sqLiteRoiPoiDao.findAllPointsWithRoi(roiID);
        Assert.assertEquals(pois[0], poiID);
        int[] rois = sqLiteRoiPoiDao.findAllRegionsWithPoi(poiID);
        Assert.assertEquals(rois[0], roiID);
    }

    /**
     * Viene testato che sia possibile recuperare gli identificativi di tutte le RegionOfInterest
     * associate ad uno specifico PointOfInterest
     */
    @Test
    public void shouldRetrieveAllAssociatedROIsIDs() throws Exception {
        setUp();
        // costruisco una ROI
        RegionOfInterest roi1 = new RegionOfInterestImp(1, "UUID", 666, 3);
        RegionOfInterest roi2 = new RegionOfInterestImp(2, "UUID", 666, 4);
        List<RegionOfInterest> nearbyROIs = new LinkedList<>();
        nearbyROIs.add(roi1);
        nearbyROIs.add(roi2);

        // costruisco un POI
        int poiId = 12;
        PointOfInterestInformation poiInfo =
                new PointOfInterestInformation("MyPoi", "description", "category");
        PointOfInterest poi = new PointOfInterestImp(poiId, poiInfo);
        poi.setBelongingROIs(nearbyROIs);

        List<PointOfInterest> nearbyPOIs = new LinkedList<>();
        nearbyPOIs.add(poi);

        roi1.setNearbyPOIs(nearbyPOIs);
        roi2.setNearbyPOIs(nearbyPOIs);

        int[] actualNearbyROIs = poiService.findAllRegionsWithPoi(poiId);
        for(int i:actualNearbyROIs) Assert.assertEquals(actualNearbyROIs[i],i);
    }
}
