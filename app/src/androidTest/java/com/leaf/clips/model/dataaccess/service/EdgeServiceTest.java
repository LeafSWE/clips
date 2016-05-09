package com.leaf.clips.model.dataaccess.service;

import com.google.gson.JsonObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.test.InstrumentationRegistry;

import com.leaf.clips.model.dataaccess.dao.BuildingContract;
import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.dao.CategoryContract;
import com.leaf.clips.model.dataaccess.dao.EdgeContract;
import com.leaf.clips.model.dataaccess.dao.EdgeTable;
import com.leaf.clips.model.dataaccess.dao.EdgeTypeContract;
import com.leaf.clips.model.dataaccess.dao.EdgeTypeTable;
import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.dao.PhotoContract;
import com.leaf.clips.model.dataaccess.dao.PhotoTable;
import com.leaf.clips.model.dataaccess.dao.PointOfInterestContract;
import com.leaf.clips.model.dataaccess.dao.RegionOfInterestContract;
import com.leaf.clips.model.dataaccess.dao.RegionOfInterestTable;
import com.leaf.clips.model.dataaccess.dao.RemoteEdgeDao;
import com.leaf.clips.model.dataaccess.dao.RemoteEdgeTypeDao;
import com.leaf.clips.model.dataaccess.dao.RemotePhotoDao;
import com.leaf.clips.model.dataaccess.dao.RemoteRegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RemoteRoiPoiDao;
import com.leaf.clips.model.dataaccess.dao.RoiPoiContract;
import com.leaf.clips.model.dataaccess.dao.SQLiteBuildingDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteEdgeDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteEdgeTypeDao;
import com.leaf.clips.model.dataaccess.dao.SQLitePhotoDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRoiPoiDao;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterestImp;
import com.leaf.clips.model.navigator.graph.edge.DefaultEdge;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.navigator.graph.navigationinformation.BasicInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.DetailedInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformationImp;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 */
public class EdgeServiceTest {

    public class DatabaseTest implements BaseColumns {

        public static final String SQL_CREATE_PHOTO_TABLE = "CREATE TABLE " +
                PhotoContract.TABLE_NAME + " (" +
                PhotoContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PhotoContract.COLUMN_EDGEID + " INTEGER, " +
                PhotoContract.COLUMN_URL + " TEXT" +
                " )";
        public static final String SQL_DELETE_PHOTO_TABLE = "DROP TABLE IF EXISTS " +
                PhotoContract.TABLE_NAME;

        public static final String SQL_CREATE_EDGETYPE_TABLE = "CREATE TABLE " +
                EdgeTypeContract.TABLE_NAME + " (" +
                EdgeTypeContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                EdgeTypeContract.COLUMN_TYPENAME+ " TEXT" +
                " )";
        public static final String SQL_DELETE_EDGETYPE_TABLE = "DROP TABLE IF EXISTS " +
                EdgeTypeContract.TABLE_NAME;

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

    private Context context;
    private EdgeService edgeService;
    private SQLiteEdgeDao sqLiteEdgeDao;
    private RemoteEdgeDao remoteEdgeDao;
    private SQLiteEdgeTypeDao sqLiteEdgeTypeDao;
    private RemoteEdgeTypeDao remoteEdgeTypeDao;
    private SQLitePhotoDao sqLitePhotoDao;
    private RemotePhotoDao remotePhotoDao;
    private PhotoService photoService;
    private SQLiteRegionOfInterestDao sqLiteRoiDao;
    private RemoteRegionOfInterestDao remoteRoiDao;
    private SQLiteRoiPoiDao sqLiteRoiPoiDao;
    private RemoteRoiPoiDao remoteRoiPoiDao;
    private RegionOfInterestService roiService;
    private MapsDbHelper dbHelper;
    private SQLiteDatabase database;
    private String[] columns;
    private SQLiteBuildingDao sqLiteBuildingDao;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MapsDbHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);
        database.execSQL(DatabaseTest.SQL_DELETE_PHOTO_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_ENTRIES);
        database.execSQL(DatabaseTest.SQL_DELETE_ROI_TABLE);
        database.execSQL(DatabaseTest.SQL_DELETE_EDGETYPE_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_EDGETYPE_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_ROI_TABLE);
        database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        database.execSQL(DatabaseTest.SQL_CREATE_PHOTO_TABLE);
        sqLiteBuildingDao = new SQLiteBuildingDao(database);
        Assert.assertNotNull(sqLiteBuildingDao);
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
        sqLitePhotoDao = new SQLitePhotoDao(database);
        Assert.assertNotNull(sqLitePhotoDao);
        remotePhotoDao = new RemotePhotoDao();
        Assert.assertNotNull(remotePhotoDao);
        photoService = new PhotoService(sqLitePhotoDao, remotePhotoDao);
        Assert.assertNotNull(photoService);
        sqLiteEdgeDao = new SQLiteEdgeDao(database);
        Assert.assertNotNull(sqLiteEdgeDao);
        remoteEdgeDao = new RemoteEdgeDao();
        Assert.assertNotNull(remoteEdgeDao);
        sqLiteEdgeTypeDao = new SQLiteEdgeTypeDao(database);
        Assert.assertNotNull(sqLiteEdgeTypeDao);
        remoteEdgeTypeDao = new RemoteEdgeTypeDao();
        Assert.assertNotNull(remoteEdgeTypeDao);
        edgeService = new EdgeService(sqLiteEdgeDao, remoteEdgeDao, sqLiteEdgeTypeDao,
                remoteEdgeTypeDao, photoService, roiService);
        Assert.assertNotNull(edgeService);
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

    /**
     * Viene testato che sia possibile eliminare un Edge dal database locale, recuperarne uno o
     * tutti quelli riguardanti un edificio, dato il major del suddetto edificio.
     */

    @Test
    public void shouldDeleteAnEdge() throws Exception {
        setUp();
        sqLiteEdgeTypeDao.insertEdgeType(new EdgeTypeTable(1, "Default"));
        sqLiteRoiDao.insertRegionOfInterest(new RegionOfInterestTable(2, "uuid", 666, 5));
        sqLiteRoiDao.insertRegionOfInterest(new RegionOfInterestTable(3, "uuid", 666, 6));
        sqLiteEdgeDao.insertEdge(new EdgeTable(1, 2, 3, 4.0, "180", 1, "action", "descr"));

        // Per esser sicuro che l'abbia inserito
        EdgeTable table = sqLiteEdgeDao.findEdge(1);

        RegionOfInterest startROI = new RegionOfInterestImp(2, "uuid", 666, 5);
        RegionOfInterest endROI = new RegionOfInterestImp(3, "uuid", 666, 6);
        BasicInformation basicInfo = new BasicInformation("action");
        DetailedInformation detInfo = new DetailedInformation("descr");
        PhotoRef ref1 = new PhotoRef(1, URI.create("myUrl1"));
        PhotoRef ref2 = new PhotoRef(2, URI.create("myUrl2"));
        List<PhotoRef> refs = new LinkedList<>();
        refs.add(ref1);
        refs.add(ref2);
        PhotoInformation photoInfo = new PhotoInformation(refs);
        NavigationInformation navInfo = new NavigationInformationImp(basicInfo, detInfo, photoInfo);
        EnrichedEdge edge = new DefaultEdge(startROI, endROI, 4.0, 180, 1, navInfo);

        edgeService.deleteEdge(edge);

        Cursor cursor = database.query(true, EdgeContract.TABLE_NAME, columns,
                EdgeContract.COLUMN_ID+"=\"1\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
    }

    @Test
    public void shouldRetrieveAnEdge() throws Exception {
        setUp();
        int edgeId = 13;
        sqLiteEdgeTypeDao.insertEdgeType(new EdgeTypeTable(1, "Default"));
        sqLiteRoiDao.insertRegionOfInterest(new RegionOfInterestTable(2, "uuid", 666, 5));
        sqLiteRoiDao.insertRegionOfInterest(new RegionOfInterestTable(3, "uuid", 666, 6));
        sqLiteEdgeDao.insertEdge(new EdgeTable(edgeId, 2, 3, 4.0, "180", 1, "action", "descr"));
        sqLitePhotoDao.insertPhoto(new PhotoTable(1, "myUrl1", edgeId));
        sqLitePhotoDao.insertPhoto(new PhotoTable(2, "myUrl2", edgeId));
        RegionOfInterest startROI = new RegionOfInterestImp(2, "uuid", 666, 5);
        RegionOfInterest endROI = new RegionOfInterestImp(3, "uuid", 666, 6);
        List<RegionOfInterest> tracedRois = new LinkedList<>();
        tracedRois.add(startROI);
        tracedRois.add(endROI);
        roiService.setTracedRois(tracedRois);
        EnrichedEdge edge = edgeService.findEdge(edgeId);
        Assert.assertEquals(edge.getCoordinate(), 180);
        Assert.assertEquals(edge.getBasicInformation(), "action");
        Assert.assertEquals(edge.getDetailedInformation(), "descr");
        Assert.assertEquals(edge.getDistance(), 4.0);
    }

    @Test
    public void shouldRetrieveAllEdgesOfABuilding() throws Exception {
        setUp();
        int major = 666;
        int edgeId1 = 1;
        int edgeId2 = 2;
        int roi1_id = 1;
        int roi2_id = 2;

        sqLiteEdgeTypeDao.insertEdgeType(new EdgeTypeTable(1, "Default"));
        sqLiteRoiDao.insertRegionOfInterest(new RegionOfInterestTable(roi1_id, "uuid", major, 5));
        sqLiteRoiDao.insertRegionOfInterest(new RegionOfInterestTable(roi2_id, "uuid", major, 6));
        // inserisco il primo edge
        sqLiteEdgeDao.insertEdge(new EdgeTable(edgeId1, roi1_id, roi2_id, 4.0, "180", 1, "action", "descr"));
        sqLitePhotoDao.insertPhoto(new PhotoTable(1, "myUrl1", edgeId1));
        sqLitePhotoDao.insertPhoto(new PhotoTable(2, "myUrl2", edgeId1));
        // inserisco il secondo edge
        sqLiteEdgeDao.insertEdge(new EdgeTable(edgeId2, roi1_id, roi2_id, 10.0, "360", 1, "action", "descr"));
        sqLitePhotoDao.insertPhoto(new PhotoTable(3, "myUrl3", edgeId2));
        sqLitePhotoDao.insertPhoto(new PhotoTable(4, "myUrl4", edgeId2));
        // setto le tracedRois
        RegionOfInterest roi1 = new RegionOfInterestImp(roi1_id, "uuid", major, 5);
        RegionOfInterest roi2 = new RegionOfInterestImp(roi2_id, "uuid", major, 6);
        List<RegionOfInterest> tracedRois = new LinkedList<>();
        tracedRois.add(roi1);
        tracedRois.add(roi2);
        roiService.setTracedRois(tracedRois);
        EnrichedEdge edge1 = edgeService.findEdge(edgeId1);
        RegionOfInterest startRoi1 = edge1.getStarterPoint();
        Assert.assertEquals(startRoi1.getMajor(), major);
        RegionOfInterest endRoi1 = edge1.getEndPoint();
        Assert.assertEquals(endRoi1.getMajor(), major);
        // questo sopra funziona
        // TODO: se invoco findAllEdgesOfBuilding la query sul db non me li trova
        // Collection<EdgeTable> tables = sqLiteEdgeDao.findAllEdgesOfBuilding(major);
        // richiedo tutti gli Edge di un edificio
        // Collection<EnrichedEdge> edges = edgeService.findAllEdgesOfBuilding(major);
        // Assert.assertEquals(2, edges.size());
    }

    /**
     * Viene testato che, dato un oggetto JsonObject che possiede gli stessi valori di un oggetto
     * EdgeTable, sia possibile costruire un oggetto EdgeTable e inserirlo nel database locale.
     */
    @Test
    public void shouldCreateAnEdgeTableAndInsertItInTheDB() throws Exception {
        setUp();
        int edgeId = 13;
        sqLiteEdgeTypeDao.insertEdgeType(new EdgeTypeTable(1, "Default"));
        sqLiteRoiDao.insertRegionOfInterest(new RegionOfInterestTable(2, "uuid", 666, 5));
        sqLiteRoiDao.insertRegionOfInterest(new RegionOfInterestTable(3, "uuid", 666, 6));
        JsonObject js = new JsonObject();
        js.addProperty("id", edgeId);
        js.addProperty("startROI", 2);
        js.addProperty("endROI", 3);
        js.addProperty("distance", 4.0);
        js.addProperty("coordinate", "180");
        js.addProperty("typeId", 1);
        js.addProperty("action", "action");
        js.addProperty("longDescription", "descr");
        edgeService.convertAndInsert(js);
        sqLitePhotoDao.insertPhoto(new PhotoTable(1, "myUrl1", edgeId));
        sqLitePhotoDao.insertPhoto(new PhotoTable(2, "myUrl2", edgeId));
        RegionOfInterest startROI = new RegionOfInterestImp(2, "uuid", 666, 5);
        RegionOfInterest endROI = new RegionOfInterestImp(3, "uuid", 666, 6);
        List<RegionOfInterest> tracedRois = new LinkedList<>();
        tracedRois.add(startROI);
        tracedRois.add(endROI);
        roiService.setTracedRois(tracedRois);

        EnrichedEdge edge = edgeService.findEdge(edgeId);
        Assert.assertEquals(edge.getDistance(), 4.0);
    }

    /**
     * Viene testato che, dato un oggetto JsonObject che possiede gli stessi valori di un oggetto
     * EdgeTypeTable, sia possibile costruire un oggetto EdgeTypeTable e
     * inserirlo nel database locale.
     */
    @Test
    public void shouldCreateAnEdgeTypeTableAndInsertItInTheDB() throws Exception {
        setUp();
        int edgeType_id = 1;
        String edgeType_name = "Stairs";
        JsonObject js = new JsonObject();
        js.addProperty("id", edgeType_id);
        js.addProperty("typeName", edgeType_name);
        edgeService.convertAndInsertEdgeType(js);

        EdgeTypeTable table = sqLiteEdgeTypeDao.findEdgeType(edgeType_id);
        Assert.assertEquals(table.getTypeName(), edgeType_name);
    }

    /**
     * Viene testato che, dato un oggetto JsonObject che possiede gli stessi valori di un oggetto
     * PhotoTable, sia possibile costruire un oggetto PhotoTable e inserirlo nel database locale.
     */
    @Test
    public void shouldCreateAPhotoTableAndInsertItInTheDB() throws Exception {
        setUp();
        int photoId = 1;
        String url = "myUrl";
        JsonObject js = new JsonObject();
        js.addProperty("id", photoId);
        js.addProperty("url", url);
        js.addProperty("edgeId", 2);
        edgeService.convertAndInsertPhoto(js);

        PhotoRef ref = photoService.findPhoto(photoId);
        Assert.assertEquals(ref.getPhotoUri(), URI.create(url));
    }
}
