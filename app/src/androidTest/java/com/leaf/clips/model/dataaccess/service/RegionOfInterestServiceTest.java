package com.leaf.clips.model.dataaccess.service;

import com.google.gson.JsonObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.dao.RegionOfInterestContract;
import com.leaf.clips.model.dataaccess.dao.RemoteRegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RemoteRoiPoiDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRoiPoiDao;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestInformation;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterestImp;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;

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
public class RegionOfInterestServiceTest {

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

    private Context context;
    private MapsDbHelper dbHelper;
    private SQLiteDatabase database;
    private SQLiteRegionOfInterestDao sqLiteRoiDao;
    private RemoteRegionOfInterestDao remoteRoiDao;
    private SQLiteRoiPoiDao sqLiteRoiPoiDao;
    private RemoteRoiPoiDao remoteRoiPoiDao;
    private RegionOfInterestService roiService;
    private String[] columns;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MapsDbHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);
        database.execSQL(DatabaseTest.SQL_DELETE_ENTRIES);
        database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        sqLiteRoiDao = new SQLiteRegionOfInterestDao(database);
        Assert.assertNotNull(sqLiteRoiDao);
        remoteRoiDao = new RemoteRegionOfInterestDao();
        Assert.assertNotNull(remoteRoiDao);
        sqLiteRoiPoiDao = new SQLiteRoiPoiDao(database);
        Assert.assertNotNull(sqLiteRoiPoiDao);
        remoteRoiPoiDao = new RemoteRoiPoiDao();
        Assert.assertNotNull(remoteRoiPoiDao);
        roiService = new RegionOfInterestService(sqLiteRoiDao, remoteRoiDao,
                sqLiteRoiPoiDao, remoteRoiPoiDao);
        Assert.assertNotNull(roiService);
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

    /**
     * Viene testato che sia possibile eliminare una RegionOfInterest dal database locale,
     * recuperarne una o tutte quelle riguardanti un edificio, dato il major del suddetto edificio.
     */

    @Test
    public void shouldDeleteARegionOfInterest() throws Exception {
        setUp();
        int roiId = 1;
        databaseInsert(valuesWithId(roiId));
        roiService.deleteRegionOfInterest(roiId);

        Assert.assertEquals(0, database.query(true, RegionOfInterestContract.TABLE_NAME, columns,
                RegionOfInterestContract.COLUMN_ID + "=1", null, null, null, null,
                null).getCount());
    }

    @Test
    public void shouldRetrieveARegionOfInterest() throws Exception {
        setUp();
        int roiId = 1;
        int major = 666;
        databaseInsert(valuesWithIdAndMajor(roiId, major));

        RegionOfInterest roi = roiService.findRegionOfInterest(roiId);
        Assert.assertEquals(major, roi.getMajor());
    }

    @Test
    public void shouldRetrieveAllROIsOfABuilding() throws Exception {
        setUp();
        int major = 666;
        databaseInsert(valuesWithIdAndMajor(1, major));
        databaseInsert(valuesWithIdAndMajor(2, major));
        databaseInsert(valuesWithIdAndMajor(3, major));
        databaseInsert(valuesWithIdAndMajor(4, major));

        Collection<RegionOfInterest> rois = roiService.findAllRegionsWithMajor(major);
        for(RegionOfInterest roi : rois) Assert.assertEquals(major, roi.getMajor());
    }

    /**
     * Viene testato che, dato un oggetto JsonObject che possiede gli stessi valori di un oggetto
     * RegionOfInterestTable, sia possibile costruire un oggetto RegionOfInterestTable e
     * inserirlo nel database locale.
     */
    @Test
    public void shouldCreateRegionOfInterestTableAndInsertItInTheDB() throws Exception {
        setUp();
        JsonObject js = new JsonObject();
        js.addProperty("id", 1);
        js.addProperty("uuid", "UUID");
        js.addProperty("major", 666);
        js.addProperty("minor", 2);
        roiService.convertAndInsert(js);

        RegionOfInterest roi = roiService.findRegionOfInterest(1);
        Assert.assertNotNull(roi);
    }

    /**
     * Viene testato che sia possibile recuperare le RegionOfInterest che sono già state
     * associate ai PointOfInterest vicini
     */
    @Test
    public void shouldGetTracedROIs() throws Exception {
        setUp();
        int major = 666;
        databaseInsert(valuesWithIdAndMajor(1, major));
        databaseInsert(valuesWithIdAndMajor(2, major));
        // let's pretend these ROIs are traced for testing purpose
        Collection<RegionOfInterest> rois = roiService.findAllRegionsWithMajor(major);
        roiService.setTracedRois(rois);

        Collection<RegionOfInterest> tracedRois = roiService.getTracedRois();
        Assert.assertEquals(rois, tracedRois);
    }

    /**
     * Viene testato che sia possibile impostare le RegionOfInterest che sono già
     * state associate i PointOfInterest vicini
     */
    @Test
    public void shouldSetTracedROIs() throws Exception {
        setUp();
        int major = 666;
        databaseInsert(valuesWithIdAndMajor(1, major));
        databaseInsert(valuesWithIdAndMajor(2, major));
        // let's pretend these ROIs are traced for testing purpose
        Collection<RegionOfInterest> rois = roiService.findAllRegionsWithMajor(major);

        roiService.setTracedRois(rois);
        // let's retrieve and check if they're really what we set
        Collection<RegionOfInterest> tracedRois = roiService.getTracedRois();
        Assert.assertEquals(rois, tracedRois);
    }

    /**
     * Viene testato che sia possibile recuperare gli identificativi di tutti i
     * PointOfInterest associati ad una specifica RegionOfInterest
     */
    @Test
    public void shouldRetrieveAllAssociatedPOIsIDs() throws Exception {
        setUp();
        PointOfInterestInformation poiInfo1 =
                new PointOfInterestInformation("poi1", "poi1_description", "category");
        PointOfInterest poi1 = new PointOfInterestImp(1, poiInfo1);
        PointOfInterestInformation poiInfo2 =
                new PointOfInterestInformation("poi2", "poi2_description", "category");
        PointOfInterest poi2 = new PointOfInterestImp(2, poiInfo2);
        List<PointOfInterest> nearbyPOIs = new LinkedList<>();
        nearbyPOIs.add(poi1);
        nearbyPOIs.add(poi2);

        int roiId = 12;
        RegionOfInterest roi1 = new RegionOfInterestImp(roiId, "UUID", 666, 2);
        roi1.setNearbyPOIs(nearbyPOIs);

        List<RegionOfInterest> nearbyROIs = new LinkedList<>();
        nearbyROIs.add(roi1);

        poi1.setBelongingROIs(nearbyROIs);
        poi2.setBelongingROIs(nearbyROIs);

        int[] actualNearbyPOIs = roiService.findAllPointsWithRoi(roiId);
        for(int i:actualNearbyPOIs) Assert.assertEquals(actualNearbyPOIs[i],i);
    }
}
