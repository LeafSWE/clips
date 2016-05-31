package com.leaf.clips.model.dataaccess.service;

import com.google.gson.JsonObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.dataaccess.dao.BuildingContract;
import com.leaf.clips.model.dataaccess.dao.BuildingDao;
import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.dao.CategoryContract;
import com.leaf.clips.model.dataaccess.dao.CategoryDao;
import com.leaf.clips.model.dataaccess.dao.CategoryTable;
import com.leaf.clips.model.dataaccess.dao.EdgeContract;
import com.leaf.clips.model.dataaccess.dao.EdgeDao;
import com.leaf.clips.model.dataaccess.dao.EdgeTable;
import com.leaf.clips.model.dataaccess.dao.EdgeTypeContract;
import com.leaf.clips.model.dataaccess.dao.EdgeTypeDao;
import com.leaf.clips.model.dataaccess.dao.EdgeTypeTable;
import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.dao.PhotoContract;
import com.leaf.clips.model.dataaccess.dao.PhotoDao;
import com.leaf.clips.model.dataaccess.dao.PhotoTable;
import com.leaf.clips.model.dataaccess.dao.PointOfInterestContract;
import com.leaf.clips.model.dataaccess.dao.PointOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.PointOfInterestTable;
import com.leaf.clips.model.dataaccess.dao.RegionOfInterestContract;
import com.leaf.clips.model.dataaccess.dao.RegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RegionOfInterestTable;
import com.leaf.clips.model.dataaccess.dao.RemoteBuildingDao;
import com.leaf.clips.model.dataaccess.dao.RemoteCategoryDao;
import com.leaf.clips.model.dataaccess.dao.RemoteDaoFactory;
import com.leaf.clips.model.dataaccess.dao.RemoteEdgeDao;
import com.leaf.clips.model.dataaccess.dao.RemoteEdgeTypeDao;
import com.leaf.clips.model.dataaccess.dao.RemotePhotoDao;
import com.leaf.clips.model.dataaccess.dao.RemotePointOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RemoteRegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RemoteRoiPoiDao;
import com.leaf.clips.model.dataaccess.dao.RoiPoiContract;
import com.leaf.clips.model.dataaccess.dao.RoiPoiDao;
import com.leaf.clips.model.dataaccess.dao.RoiPoiTable;
import com.leaf.clips.model.dataaccess.dao.SQLiteBuildingDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteCategoryDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteDaoFactory;
import com.leaf.clips.model.dataaccess.dao.SQLiteEdgeDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteEdgeTypeDao;
import com.leaf.clips.model.dataaccess.dao.SQLitePhotoDao;
import com.leaf.clips.model.dataaccess.dao.SQLitePointOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRoiPoiDao;
import com.leaf.clips.model.navigator.BuildingMap;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 */

/**
 * TU57 & TU58 & TU59 & TU60 & TU156
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class BuildingServiceTest {

    public class DatabaseTest implements BaseColumns {

        public static final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " +
                CategoryContract.TABLE_NAME + " (" +
                CategoryContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CategoryContract.COLUMN_DESCRIPTION + " TEXT" +
                " )";
        public static final String SQL_CREATE_POI_TABLE =
                "CREATE TABLE " + PointOfInterestContract.TABLE_NAME + " (" +
                        PointOfInterestContract.COLUMN_ID + " INTEGER PRIMARY KEY," +
                        PointOfInterestContract.COLUMN_NAME + " TEXT, "+
                        PointOfInterestContract.COLUMN_DESCRIPTION + " TEXT, "+
                        PointOfInterestContract.COLUMN_CATEGORYID + " INTEGER, "+
                        "FOREIGN KEY ("+PointOfInterestContract.COLUMN_CATEGORYID+
                        ") REFERENCES "+CategoryContract.TABLE_NAME+
                        "("+CategoryContract.COLUMN_ID+"))";
        public static final String SQL_CREATE_PHOTO_TABLE = "CREATE TABLE " +
                PhotoContract.TABLE_NAME + " (" +
                PhotoContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PhotoContract.COLUMN_EDGEID + " INTEGER, " +
                PhotoContract.COLUMN_URL + " TEXT" +
                " )";
        public static final String SQL_CREATE_EDGETYPE_TABLE = "CREATE TABLE " +
                EdgeTypeContract.TABLE_NAME + " (" +
                EdgeTypeContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                EdgeTypeContract.COLUMN_TYPENAME+ " TEXT" +
                " )";
        public static final String SQL_CREATE_EDGE_TABLE = "CREATE TABLE " +
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
                RegionOfInterestContract.COLUMN_ID + " INTEGER PRIMARY KEY," +
                RegionOfInterestContract.COLUMN_MAJOR + " INTEGER, " +
                RegionOfInterestContract.COLUMN_MINOR + " INTEGER, " +
                RegionOfInterestContract.COLUMN_UUID + " TEXT " +
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
        public static final String SQL_DELETE_EDGE_TABLE = "DROP TABLE IF EXISTS " +
                EdgeContract.TABLE_NAME;
        public static final String SQL_DELETE_ROI_TABLE = "DROP TABLE IF EXISTS " +
                RegionOfInterestContract.TABLE_NAME;
        public static final String SQL_DELETE_PHOTO_TABLE = "DROP TABLE IF EXISTS " +
                PhotoContract.TABLE_NAME;
        public static final String SQL_DELETE_EDGETYPE_TABLE = "DROP TABLE IF EXISTS " +
                EdgeTypeContract.TABLE_NAME;
        public static final String SQL_DELETE_POI_TABLE = "DROP TABLE IF EXISTS " +
                PointOfInterestContract.TABLE_NAME;
        public static final String SQL_DELETE_ROIPOI_TABLE = "DROP TABLE IF EXISTS " +
                RoiPoiContract.TABLE_NAME;
        public static final String SQL_DELETE_CATEGORY_TABLE = "DROP TABLE IF EXISTS " +
                CategoryContract.TABLE_NAME;
    }

    private Context context;
    private MapsDbHelper dbHelper;
    private SQLiteDatabase database;
    private String dbURL;
    private SQLiteDaoFactory sqLiteDaoFactory;
    private RemoteDaoFactory remoteDaoFactory;
    private String[] columns;

    private EdgeService edgeService;
    private PhotoService photoService;
    private BuildingService buildingService;
    private RegionOfInterestService roiService;
    private PointOfInterestService poiService;

    private SQLiteEdgeDao sqLiteEdgeDao;
    private RemoteEdgeDao remoteEdgeDao;
    private SQLiteEdgeTypeDao sqLiteEdgeTypeDao;
    private RemoteEdgeTypeDao remoteEdgeTypeDao;
    private SQLitePhotoDao sqLitePhotoDao;
    private RemotePhotoDao remotePhotoDao;
    private SQLiteRegionOfInterestDao sqLiteRoiDao;
    private RemoteRegionOfInterestDao remoteRoiDao;
    private SQLiteRoiPoiDao sqLiteRoiPoiDao;
    private RemoteRoiPoiDao remoteRoiPoiDao;
    private SQLitePointOfInterestDao sqLitePoiDao;
    private RemotePointOfInterestDao remotePoiDao;
    private SQLiteBuildingDao sqLiteBuildingDao;
    private RemoteBuildingDao remoteBuildingDao;
    private SQLiteCategoryDao sqLiteCategoryDao;
    private RemoteCategoryDao remoteCategoryDao;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MapsDbHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);

        // DELETE
        database.execSQL(DatabaseTest.SQL_DELETE_PHOTO_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_EDGE_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_EDGETYPE_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_ROIPOI_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_POI_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_CATEGORY_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_ROI_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_ENTRIES);

        // CREATE
        database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        database.execSQL(DatabaseTest.SQL_CREATE_ROI_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_CATEGORY_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_POI_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_ROIPOITABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_EDGETYPE_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_EDGE_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_PHOTO_TABLE);

        dbURL = dbHelper.getRemoteDatabaseURL();
        Assert.assertNotNull(dbURL);
        sqLiteDaoFactory = new SQLiteDaoFactory(database);
        Assert.assertNotNull(sqLiteDaoFactory);
        remoteDaoFactory = new RemoteDaoFactory();
        Assert.assertNotNull(remoteDaoFactory);

        sqLiteRoiDao = (SQLiteRegionOfInterestDao)sqLiteDaoFactory.getRegionOfInterestDao();
        Assert.assertNotNull(sqLiteRoiDao);
        remoteRoiDao = remoteDaoFactory.getRegionOfInterestDao();
        Assert.assertNotNull(remoteRoiDao);
        sqLiteRoiPoiDao = (SQLiteRoiPoiDao)sqLiteDaoFactory.getRoiPoiDao();
        Assert.assertNotNull(sqLiteRoiPoiDao);
        remoteRoiPoiDao = remoteDaoFactory.getRoiPoiDao();
        Assert.assertNotNull(remoteRoiPoiDao);
        roiService = new RegionOfInterestService(sqLiteRoiDao, remoteRoiDao,
                sqLiteRoiPoiDao, remoteRoiPoiDao);
        Assert.assertNotNull(roiService);

        sqLitePhotoDao = (SQLitePhotoDao)sqLiteDaoFactory.getPhotoDao();
        Assert.assertNotNull(sqLitePhotoDao);
        remotePhotoDao = remoteDaoFactory.getPhotoDao();
        Assert.assertNotNull(remotePhotoDao);
        photoService = new PhotoService(sqLitePhotoDao, remotePhotoDao);
        Assert.assertNotNull(photoService);

        sqLiteEdgeDao = (SQLiteEdgeDao)sqLiteDaoFactory.getEdgeDao();
        Assert.assertNotNull(sqLiteEdgeDao);
        remoteEdgeDao = remoteDaoFactory.getEdgeDao();
        Assert.assertNotNull(remoteEdgeDao);
        sqLiteEdgeTypeDao = (SQLiteEdgeTypeDao)sqLiteDaoFactory.getEdgeTypeDao();
        Assert.assertNotNull(sqLiteEdgeTypeDao);
        remoteEdgeTypeDao = remoteDaoFactory.getEdgeTypeDao();
        Assert.assertNotNull(remoteEdgeTypeDao);
        edgeService = new EdgeService(sqLiteEdgeDao, remoteEdgeDao, sqLiteEdgeTypeDao,
                remoteEdgeTypeDao, photoService, roiService);
        Assert.assertNotNull(edgeService);

        sqLitePoiDao = (SQLitePointOfInterestDao) sqLiteDaoFactory.getPointOfInterestDao();
        Assert.assertNotNull(sqLitePoiDao);
        remotePoiDao = remoteDaoFactory.getPointOfInterestDao();
        Assert.assertNotNull(remotePoiDao);
        sqLiteCategoryDao = (SQLiteCategoryDao) sqLiteDaoFactory.getCategoryDao();
        Assert.assertNotNull(sqLiteCategoryDao);
        remoteCategoryDao = remoteDaoFactory.getCategoryDao();
        Assert.assertNotNull(remoteCategoryDao);
        poiService = new PointOfInterestService(sqLitePoiDao, remotePoiDao, sqLiteRoiPoiDao,
                remoteRoiPoiDao, sqLiteCategoryDao, remoteCategoryDao);

        sqLiteBuildingDao = (SQLiteBuildingDao)sqLiteDaoFactory.getBuildingDao();
        Assert.assertNotNull(sqLiteBuildingDao);
        remoteBuildingDao = remoteDaoFactory.getBuildingDao();
        Assert.assertNotNull(remoteBuildingDao);
        buildingService = new BuildingService(dbURL, sqLiteBuildingDao, remoteBuildingDao,
                roiService, poiService, edgeService);

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

    private void insertBuilding(int id, int major, int mapVersion, int roi1_id, int roi2_id, int poi_id, int cat_id, int edgeType_id, int edge_id, int photo_id) {
        sqLiteBuildingDao.insertBuilding(new BuildingTable(id, "UUID", major, "name", "d", "o", "a", mapVersion, "s"));
        sqLiteRoiDao.insertRegionOfInterest(new RegionOfInterestTable(roi1_id, "UUID", major, 1));
        sqLiteRoiDao.insertRegionOfInterest(new RegionOfInterestTable(roi2_id, "UUID", major, 2));
        sqLiteCategoryDao.insertCategory(new CategoryTable(cat_id, "Aule"));
        sqLitePoiDao.insertPointOfInterest(new PointOfInterestTable("d", poi_id, "n", cat_id));
        sqLiteRoiPoiDao.insertRoiPoi(new RoiPoiTable(roi1_id, poi_id));
        sqLiteRoiPoiDao.insertRoiPoi(new RoiPoiTable(roi2_id, poi_id));
        sqLiteEdgeTypeDao.insertEdgeType(new EdgeTypeTable(edgeType_id, "A"));
        sqLiteEdgeDao.insertEdge(new EdgeTable(edge_id, roi1_id, roi2_id, 4.0, "180", edgeType_id, "a", "d"));
        sqLitePhotoDao.insertPhoto(new PhotoTable(photo_id, "url", edge_id));
    }

    /**
     * Viene testato che sia possibile eliminare una BuildingMap dal database locale,
     * recuperarne una o tutte quelle presenti nel database locale.
     */

    @Test
    public void shouldDeleteABuilding() throws Exception {
        setUp();
        insertBuilding(1, 666, 1, 2, 3, 4, 5, 6, 7, 8);
        // controllo che sia stata inserita correttamente
        BuildingMap map = buildingService.findBuildingByMajor(666);

        buildingService.deleteBuilding(map);

        Cursor cursor = database.query(true, BuildingContract.TABLE_NAME, columns,
                BuildingContract.COLUMN_ID + "=\"1\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
    }

    @Test
    public void shouldRetrieveABuilding() throws Exception {
        setUp();
        int id = 1;
        int major = 666;
        insertBuilding(id, major, 1, 2, 3, 4, 5, 6, 7, 8);
        BuildingMap map = buildingService.findBuildingByMajor(major);
        Assert.assertEquals(map.getId(), id);
    }

    @Test
    public void shouldRetrieveAllBuildings() throws Exception {
        setUp();
        int id1 = 1;
        int major1 = 222;
        int id2 = 2;
        int major2 = 666;
        insertBuilding(id1, major1, 1, 2, 3, 4, 5, 6, 7, 8);
        insertBuilding(id2, major2, 1, 9, 10, 11, 12, 13, 14, 15);

        Collection<BuildingTable> maps = buildingService.findAllBuildings();
        Assert.assertEquals(maps.size(), 2);
    }

    /**
     * Viene testato che, dato un oggetto JsonObject che possiede gli stessi valori di un oggetto
     * BuildingTable, sia possibile costruire un oggetto BuildingTable e inserirlo nel database locale.
     */
    @Test
    public void shouldCreateABuildingTableAndInsertItInTheDB() throws Exception {
        setUp();
        int id = 1;
        int major = 666;
        JsonObject js = new JsonObject();
        js.addProperty("id", id);
        js.addProperty("uuid", "UUID");
        js.addProperty("major", major);
        js.addProperty("name", "Name");
        js.addProperty("description", "Description");
        js.addProperty("openingHours", "08:00-18:00");
        js.addProperty("address", "Address");
        js.addProperty("mapVersion", 1);
        js.addProperty("mapSize", "42 MB");
        buildingService.convertAndInsert(js);

        BuildingTable table = sqLiteBuildingDao.findBuildingById(id);
        Assert.assertEquals(table.getMajor(), major);
    }

    /**
     * Viene testato che, dato il major di un edificio, sia possibile verificare la
     * presenza della BuildingMap nel database locale, verificare se è aggiornata
     * all'ultima versione disponibile e aggiornarla.
     */
    @Test
    public void shouldVerifyMapPresence() throws Exception {
        setUp();
        Assert.assertEquals(false, buildingService.isBuildingMapPresent(222));
        int major = 666;
        insertBuilding(1, major, 1, 2, 3, 4, 5, 6, 7, 8);
        Assert.assertEquals(true, buildingService.isBuildingMapPresent(major));
    }

    @Test
    public void shouldVerifyIfMapIsUpdated() throws Exception {
        setUp();
        int major = 666;
        insertBuilding(1, major, 1, 2, 3, 4, 5, 6, 7, 8);
        Assert.assertEquals(true, buildingService.isBuildingMapPresent(major));

        Assert.assertEquals(true, buildingService.isBuildingMapUpdated(major));
    }

    @Test
    public void shouldUpdateMap() throws Exception {
        setUp();
        int major = 666;
        int oldMapVersion = 0;
        int newMapVersion = 1;
        insertBuilding(1, major, oldMapVersion, 2, 3, 4, 5, 6, 7, 8);

        buildingService.updateBuildingMap(major);

        BuildingMap map = buildingService.findBuildingByMajor(major);
        Assert.assertEquals(newMapVersion, map.getVersion());
    }

    /**
     * Viene testato che sia possibile verificare la presenza sul database remoto
     * della mappa di un edificio
     */
    @Test
    public void shouldVerifyRemoteMapPresence() throws Exception {
        setUp();
        Assert.assertEquals(true, buildingService.isRemoteMapPresent(666));
        Assert.assertEquals(false, buildingService.isRemoteMapPresent(222));
    }


    /**
     * Viene testato che sia possibile recuperare una BuildingMap dal database remoto o
     * le informazioni di tutte quelle presenti nel database remoto.
     */
    @Test
    public void shouldRetrieveARemoteMap() throws Exception {
        setUp();
        int major = 666;
        BuildingMap downloadedMap = buildingService.findRemoteBuildingByMajor(major);
        Assert.assertNotNull(downloadedMap);
        Assert.assertEquals(downloadedMap.getName(), "Torre Archimede");
    }

    @Test
    public void shouldRetrieveAllRemoteBuildingsInformation() throws Exception {
        setUp();
        Collection<BuildingTable> tables = buildingService.findAllRemoteBuildings();
        Assert.assertEquals(tables.size(), 1);
    }
}
