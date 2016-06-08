package com.leaf.clips;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;

import com.leaf.clips.model.dataaccess.dao.BuildingDao;
import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.dao.CategoryDao;
import com.leaf.clips.model.dataaccess.dao.CategoryTable;
import com.leaf.clips.model.dataaccess.dao.DaoFactoryHelper;
import com.leaf.clips.model.dataaccess.dao.EdgeDao;
import com.leaf.clips.model.dataaccess.dao.EdgeTable;
import com.leaf.clips.model.dataaccess.dao.EdgeTypeDao;
import com.leaf.clips.model.dataaccess.dao.EdgeTypeTable;
import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.dao.PhotoDao;
import com.leaf.clips.model.dataaccess.dao.PhotoTable;
import com.leaf.clips.model.dataaccess.dao.PointOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.PointOfInterestTable;
import com.leaf.clips.model.dataaccess.dao.RegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RegionOfInterestTable;
import com.leaf.clips.model.dataaccess.dao.RemoteDaoFactory;
import com.leaf.clips.model.dataaccess.dao.RoiPoiDao;
import com.leaf.clips.model.dataaccess.dao.RoiPoiTable;
import com.leaf.clips.model.dataaccess.dao.SQLiteDaoFactory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class IntegrationTest15{

    DaoFactoryHelper daoFactoryHelper;
    SQLiteDatabase db;
    JsonObject jsonMap;


    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        SQLiteOpenHelper sqLiteOpenHelper = new MapsDbHelper(context);
        db = sqLiteOpenHelper.getWritableDatabase();
        daoFactoryHelper = DaoFactoryHelper.getInstance();
        jsonMap = new JsonParser().parse("{\n" +
                "  \"building\" : {\n" +
                "    \"id\" : 1024,\n" +
                "    \"uuid\" : \"19235dd2-574a-4702-a42e-caccac06e325\",\n" +
                "    \"major\" : 1024,\n" +
                "    \"name\" : \"Torre Archimede\",\n" +
                "    \"description\" : \"Il Dipartimento di Matematica si propone come il riferimento fondamentale dell'Ateneo per le competenze matematiche e informatiche, sia sul piano della ricerca che in quello della didattica.\",\n" +
                "    \"openingHours\" : \"07.30 - 19.00. Dal lunedì al venerdì.\",\n" +
                "    \"address\" : \"Via Trieste 63 - 35121 Padova\",\n" +
                "    \"mapVersion\" : \"1\",\n" +
                "    \"mapSize\" : \"26 KB\"\n" +
                "  },\n" +
                "  \"rois\" : [ {\n" +
                "    \"id\" : 1100,\n" +
                "    \"uuid\" : \"19235dd2-574a-4702-a42e-caccac06e325\",\n" +
                "    \"major\" : 1024,\n" +
                "    \"minor\" : 0\n" +
                "  }, {\n" +
                "    \"id\" : 1101,\n" +
                "    \"uuid\" : \"19235dd2-574a-4702-a42e-caccac06e325\",\n" +
                "    \"major\" : 1024,\n" +
                "    \"minor\" : 1\n" +
                "  } ],\n" +
                "  \"categories\" : [ {\n" +
                "    \"id\" : 1024,\n" +
                "    \"description\" : \"Entrate\"\n" +
                "  } ],\n" +
                "  \"pois\" : [ {\n" +
                "    \"id\" : 1100,\n" +
                "    \"name\" : \"Entrata torre B\",\n" +
                "    \"description\" : \"\",\n" +
                "    \"categoryId\" : 1024\n" +
                "  }, {\n" +
                "    \"id\" : 1101,\n" +
                "    \"name\" : \"Entrata torre A\",\n" +
                "    \"description\" : \"\",\n" +
                "    \"categoryId\" : 1024\n" +
                "  } ],\n" +
                "  \"roipois\" : [ {\n" +
                "    \"poiid\" : 1100,\n" +
                "    \"roiid\" : 1100\n" +
                "  }, {\n" +
                "    \"poiid\" : 1101,\n" +
                "    \"roiid\" : 1101\n" +
                "  } ],\n" +
                "  \"edgeTypes\" : [ {\n" +
                "    \"id\" : 1024,\n" +
                "    \"typeName\" : \"default\"\n" +
                "  } ],\n" +
                "  \"edges\" : [ {\n" +
                "    \"id\" : 1100,\n" +
                "    \"startROI\" : 1101,\n" +
                "    \"endROI\" : 1100,\n" +
                "    \"distance\" : 20,\n" +
                "    \"coordinate\" : \"125\",\n" +
                "    \"typeId\" : 1024,\n" +
                "    \"action\" : \"Raggiungi l'entrata della torre A\",\n" +
                "    \"longDescription\" : \"Tieni l'ascensore alla tua destra, apri la porta ed avanza verso l'esterno. Alla tua destra dovresti vedere via Trieste, mentre alla tua sinistra dovresti vedere la ringhiera che dà su un chiostro interrato. Raggiungi la porta della torre A di fronte a te.\"\n" +
                "  }, {\n" +
                "    \"id\" : 1101,\n" +
                "    \"startROI\" : 1100,\n" +
                "    \"endROI\" : 1101,\n" +
                "    \"distance\" : 20,\n" +
                "    \"coordinate\" : \"280\",\n" +
                "    \"typeId\" : 1024,\n" +
                "    \"action\" : \"Raggiungi l'entrata della torre B\",\n" +
                "    \"longDescription\" : \"Tieni l'ascensore alla tua sinistra, apri la porta ed avanza verso l'esterno. Alla tua sinistra dovresti vedere via Trieste, alla tua destra una ringhiera che dà su di un chiostro interrato. Raggiungi la porta della torre B di fronte a te.\"\n" +
                "  } ],\n" +
                "  \"photos\" : [ {\n" +
                "    \"id\" : 1100,\n" +
                "    \"edgeId\" : 1100,\n" +
                "    \"url\" : \"http://bucketclips01.s3.amazonaws.com/images/152149.jpg\"\n" +
                "  }, {\n" +
                "    \"id\" : 1101,\n" +
                "    \"edgeId\" : 1101,\n" +
                "    \"url\" : \"http://bucketclips01.s3.amazonaws.com/images/160225.jpg\"\n" +
                "  }, {\n" +
                "    \"id\" : 1102,\n" +
                "    \"edgeId\" : 1100,\n" +
                "    \"url\" : \"http://bucketclips01.s3.amazonaws.com/images/152332.jpg\"\n" +
                "  } ]\n" +
                "}").getAsJsonObject();
    }

    @Test
    public void shouldPerformCRUDOperations() throws Exception {
        RemoteDaoFactory remoteDaoFactory = daoFactoryHelper.getRemoteDaoFactory();
        SQLiteDaoFactory sqLiteDaoFactory = daoFactoryHelper.getSQLiteDaoFactory(db);

        JsonObject jsonBuilding = jsonMap.get("building").getAsJsonObject();
        BuildingTable buildingTable = remoteDaoFactory.getBuildingDao().fromJSONToTable(jsonBuilding);
        Assert.assertNotNull(buildingTable);
        Assert.assertEquals(1024, buildingTable.getId());

        JsonArray rois = jsonMap.get("rois").getAsJsonArray();
        RegionOfInterestTable regionOfInterestTable0 = remoteDaoFactory
                .getRegionOfInterestDao().fromJSONToTable(rois.get(0).getAsJsonObject());
        RegionOfInterestTable regionOfInterestTable1 = remoteDaoFactory
                .getRegionOfInterestDao().fromJSONToTable(rois.get(1).getAsJsonObject());
        Assert.assertNotNull(regionOfInterestTable0);
        Assert.assertNotNull(regionOfInterestTable1);
        Assert.assertEquals(1100, regionOfInterestTable0.getId());
        Assert.assertEquals(1101, regionOfInterestTable1.getId());

        JsonArray categories = jsonMap.get("categories").getAsJsonArray();
        CategoryTable categoryTable = remoteDaoFactory
                .getCategoryDao().fromJSONToTable(categories.get(0).getAsJsonObject());
        Assert.assertNotNull(categoryTable);
        Assert.assertEquals(1024, categoryTable.getId());

        JsonArray pois = jsonMap.get("pois").getAsJsonArray();
        PointOfInterestTable pointOfInterestTable0 = remoteDaoFactory
                .getPointOfInterestDao().fromJSONToTable(pois.get(0).getAsJsonObject());
        PointOfInterestTable pointOfInterestTable1 = remoteDaoFactory
                .getPointOfInterestDao().fromJSONToTable(pois.get(1).getAsJsonObject());
        Assert.assertNotNull(pointOfInterestTable0);
        Assert.assertNotNull(pointOfInterestTable1);
        Assert.assertEquals(1100, pointOfInterestTable0.getId());
        Assert.assertEquals(1101, pointOfInterestTable1.getId());

        JsonArray roipois = jsonMap.get("roipois").getAsJsonArray();
        RoiPoiTable roiPoiTable0 = remoteDaoFactory
                .getRoiPoiDao().fromJSONToTable(roipois.get(0).getAsJsonObject());
        RoiPoiTable roiPoiTable1 = remoteDaoFactory
                .getRoiPoiDao().fromJSONToTable(roipois.get(1).getAsJsonObject());
        Assert.assertNotNull(roiPoiTable0);
        Assert.assertNotNull(roiPoiTable1);
        Assert.assertEquals(1100, roiPoiTable0.getPoiID());
        Assert.assertEquals(1100, roiPoiTable0.getRoiID());
        Assert.assertEquals(1101, roiPoiTable1.getPoiID());
        Assert.assertEquals(1101, roiPoiTable1.getRoiID());

        JsonArray edgeTypes = jsonMap.get("edgeTypes").getAsJsonArray();
        EdgeTypeTable edgeTypeTable = remoteDaoFactory
                .getEdgeTypeDao().fromJSONToTable(edgeTypes.get(0).getAsJsonObject());
        Assert.assertNotNull(edgeTypeTable);
        Assert.assertEquals(1024, edgeTypeTable.getId());

        JsonArray edges = jsonMap.get("edges").getAsJsonArray();
        EdgeTable edgeTable0 = remoteDaoFactory
                .getEdgeDao().fromJSONToTable(edges.get(0).getAsJsonObject());
        EdgeTable edgeTable1 = remoteDaoFactory
                .getEdgeDao().fromJSONToTable(edges.get(1).getAsJsonObject());
        Assert.assertNotNull(edgeTable0);
        Assert.assertNotNull(edgeTable1);
        Assert.assertEquals(1100, edgeTable0.getId());
        Assert.assertEquals(1101, edgeTable1.getId());

        JsonArray photos = jsonMap.get("photos").getAsJsonArray();
        PhotoTable photoTable0 = remoteDaoFactory
                .getPhotoDao().fromJSONToTable(photos.get(0).getAsJsonObject());
        PhotoTable photoTable1 = remoteDaoFactory
                .getPhotoDao().fromJSONToTable(photos.get(1).getAsJsonObject());
        PhotoTable photoTable2 = remoteDaoFactory
                .getPhotoDao().fromJSONToTable(photos.get(2).getAsJsonObject());
        Assert.assertNotNull(photoTable0);
        Assert.assertNotNull(photoTable1);
        Assert.assertNotNull(photoTable2);
        Assert.assertEquals(1100, photoTable0.getId());
        Assert.assertEquals(1101, photoTable1.getId());
        Assert.assertEquals(1102, photoTable2.getId());



        CategoryDao categoryDao = sqLiteDaoFactory.getCategoryDao();
        categoryDao.insertCategory(categoryTable);
        Assert.assertEquals(1024, categoryDao.findCategory(categoryTable.getId()).getId());

        EdgeTypeDao edgeTypeDao = sqLiteDaoFactory.getEdgeTypeDao();
        edgeTypeDao.insertEdgeType(edgeTypeTable);
        Assert.assertEquals(1024, edgeTypeDao.findEdgeType(edgeTypeTable.getId()).getId());

        BuildingDao buildingDao = sqLiteDaoFactory.getBuildingDao();
        buildingDao.insertBuilding(buildingTable);
        Assert.assertTrue(buildingDao.isBuildingMapPresent(buildingTable.getMajor()));
        Assert.assertEquals(buildingTable.getMajor(), buildingDao.findBuildingById(1024).getMajor());

        RegionOfInterestDao regionOfInterestDao = sqLiteDaoFactory.getRegionOfInterestDao();
        regionOfInterestDao.insertRegionOfInterest(regionOfInterestTable0);
        regionOfInterestDao.insertRegionOfInterest(regionOfInterestTable1);
        Assert.assertEquals(2, regionOfInterestDao.findAllRegionsWithMajor(regionOfInterestTable0.getMajor()).size());

        EdgeDao edgeDao = sqLiteDaoFactory.getEdgeDao();
        edgeDao.insertEdge(edgeTable0);
        edgeDao.insertEdge(edgeTable1);
        Assert.assertEquals(2, edgeDao.findAllEdgesOfBuilding(buildingTable.getMajor()).size());

        PointOfInterestDao pointOfInterestDao = sqLiteDaoFactory.getPointOfInterestDao();
        pointOfInterestDao.insertPointOfInterest(pointOfInterestTable0);
        pointOfInterestDao.insertPointOfInterest(pointOfInterestTable1);
        Assert.assertNotNull(pointOfInterestDao.findPointOfInterest(pointOfInterestTable0.getId()));

        RoiPoiDao roiPoiDao = sqLiteDaoFactory.getRoiPoiDao();
        roiPoiDao.insertRoiPoi(roiPoiTable0);
        roiPoiDao.insertRoiPoi(roiPoiTable1);
        Assert.assertEquals(1, roiPoiDao.findAllPointsWithRoi(regionOfInterestTable0.getId()).length);
        Assert.assertEquals(1, roiPoiDao.findAllPointsWithRoi(regionOfInterestTable1.getId()).length);

        PhotoDao photoDao = sqLiteDaoFactory.getPhotoDao();
        photoDao.insertPhoto(photoTable0);
        photoDao.insertPhoto(photoTable1);
        photoDao.insertPhoto(photoTable2);
        Assert.assertEquals(2, photoDao.findAllPhotosOfEdge(edgeTable0.getId()).size());
        Assert.assertEquals(1, photoDao.findAllPhotosOfEdge(edgeTable1.getId()).size());

        photoDao.deletePhoto(photoTable0.getId());
        photoDao.deletePhoto(photoTable1.getId());
        photoDao.deletePhoto(photoTable2.getId());
        Assert.assertEquals(0, photoDao.findAllPhotosOfEdge(edgeTable0.getId()).size());
        Assert.assertEquals(0, photoDao.findAllPhotosOfEdge(edgeTable1.getId()).size());

        roiPoiDao.deleteRoiPoisWhereRoi(regionOfInterestTable0.getId());
        roiPoiDao.deleteRoiPoisWhereRoi(regionOfInterestTable1.getId());
        Assert.assertEquals(0, roiPoiDao.findAllPointsWithRoi(regionOfInterestTable0.getId()).length);
        Assert.assertEquals(0, roiPoiDao.findAllPointsWithRoi(regionOfInterestTable1.getId()).length);

        pointOfInterestDao.deletePointOfInterest(pointOfInterestTable0.getId());
        pointOfInterestDao.deletePointOfInterest(pointOfInterestTable1.getId());
        Assert.assertEquals(0, pointOfInterestDao.findAllPointsWithMajor(buildingTable.getMajor()).size());

        edgeDao.deleteEdge(edgeTable0.getId());
        edgeDao.deleteEdge(edgeTable1.getId());
        Assert.assertEquals(0, edgeDao.findAllEdgesOfBuilding(buildingTable.getMajor()).size());

        regionOfInterestDao.deleteRegionOfInterest(regionOfInterestTable0.getId());
        regionOfInterestDao.deleteRegionOfInterest(regionOfInterestTable1.getId());
        Assert.assertEquals(0, regionOfInterestDao.findAllRegionsWithMajor(regionOfInterestTable0.getMajor()).size());

        buildingDao.deleteBuilding(buildingTable.getId());
        Assert.assertFalse(buildingDao.isBuildingMapPresent(buildingTable.getMajor()));

        edgeTypeDao.deleteEdgeType(edgeTypeTable.getId());
        Assert.assertEquals(null, edgeTypeDao.findEdgeType(edgeTypeTable.getId()));

        categoryDao.deleteCategory(categoryTable.getId());
        Assert.assertEquals(null, categoryDao.findCategory(categoryTable.getId()));
    }



}
