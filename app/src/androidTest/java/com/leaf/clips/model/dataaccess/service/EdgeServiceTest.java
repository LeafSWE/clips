package com.leaf.clips.model.dataaccess.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.leaf.clips.model.dataaccess.dao.BuildingContract;
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
import java.util.LinkedList;
import java.util.List;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 */
public class EdgeServiceTest {

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

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MapsDbHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);
        database.execSQL("DROP TABLE IF EXISTS " +
                PhotoContract.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " +
                EdgeContract.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " +
                EdgeTypeContract.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " +
                RoiPoiContract.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " +
                PointOfInterestContract.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " +
                CategoryContract.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " +
                RegionOfInterestContract.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " +
                BuildingContract.TABLE_NAME);
        dbHelper.onCreate(database);
        //database.execSQL(DatabaseTest.SQL_DELETE_ENTRIES);
        //database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
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
        sqLitePhotoDao.insertPhoto(new PhotoTable(1, "myUrl1", 1));
        sqLitePhotoDao.insertPhoto(new PhotoTable(2, "myUrl2", 1));
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
    public void shouldDo() throws Exception {
        setUp();
    }

    @Test
    public void shouldDoAnotherThing() throws Exception {
        setUp();
    }
}
