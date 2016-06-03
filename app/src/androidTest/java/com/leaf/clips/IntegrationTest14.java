package com.leaf.clips;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;

import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.dao.RemoteDaoFactory;
import com.leaf.clips.model.dataaccess.dao.SQLiteDaoFactory;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.dataaccess.service.ServiceHelper;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.presenter.MyApplication;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class IntegrationTest14 {

    private DatabaseService databaseService;

    @Before
    public void SetUp() throws Exception {
        ServiceHelper helper = new ServiceHelper();
        Context context = InstrumentationRegistry.getTargetContext();
        SQLiteOpenHelper sqLiteOpenHelper = new MapsDbHelper(context);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        SQLiteDaoFactory sqLiteDaoFactory = new SQLiteDaoFactory(db);
        RemoteDaoFactory remoteDaoFactory = new RemoteDaoFactory();
        String URL = MyApplication.getConfiguration().getRemoteDBPath();
        databaseService = helper.getService(sqLiteDaoFactory, remoteDaoFactory, URL);
    }

    @Test
    public void shouldCreateObjectFromDB() {
        int major = databaseService.findAllBuildings().iterator().next().getMajor();
        BuildingMap buildingMap = databaseService.findBuildingByMajor(major);
        Assert.assertNotNull(buildingMap.getId());
        Assert.assertNotNull(buildingMap.getName());
        Assert.assertNotNull(buildingMap.getSize());
        Assert.assertNotNull(buildingMap.getAddress());
        Assert.assertNotNull(buildingMap.getVersion());
        Assert.assertNotNull(buildingMap.getOpeningHours());
        Assert.assertNotNull(buildingMap.getAllPOIs());
        Assert.assertNotNull(buildingMap.getAllPOIs().iterator().next());
        Assert.assertNotNull(buildingMap.getAllPOIs().iterator().next().getId());
        Assert.assertNotNull(buildingMap.getAllPOIs().iterator().next().getName());
        Assert.assertNotNull(buildingMap.getAllPOIs().iterator().next().getCategory());
        Assert.assertNotNull(buildingMap.getAllPOIs().iterator().next().getDescription());
        Assert.assertNotNull(buildingMap.getAllPOIs().iterator().next().getAllBelongingROIs());
        Assert.assertNotNull(buildingMap.getAllPOIs().iterator().next().getAllBelongingROIs().iterator().next());
        Assert.assertNotNull(buildingMap.getAllBuildingInformation());
        Assert.assertNotNull(buildingMap.getAllBuildingInformation().toString());
        Assert.assertNotNull(buildingMap.getAllEdges());
        Assert.assertNotNull(buildingMap.getAllEdges().iterator().next());
        Assert.assertNotNull(buildingMap.getAllEdges().iterator().next().getEndPoint());
        Assert.assertNotNull(buildingMap.getAllEdges().iterator().next().getDistance());
        Assert.assertNotNull(buildingMap.getAllEdges().iterator().next().getCoordinate());
        Assert.assertNotNull(buildingMap.getAllEdges().iterator().next().getStarterPoint());
        Assert.assertNotNull(buildingMap.getAllEdges().iterator().next().getBasicInformation());
        Assert.assertNotNull(buildingMap.getAllEdges().iterator().next().getPhotoInformation());
        Assert.assertNotNull(buildingMap.getAllEdges().iterator().next().getDetailedInformation());
        Assert.assertNotNull(buildingMap.getAllEdges().iterator().next().getPhotoInformation().getPhotoInformation().iterator().next());
        Assert.assertNotNull(buildingMap.getAllPOIsCategories());
        Assert.assertNotNull(buildingMap.getAllPOIsCategories().iterator().next());
        Assert.assertNotNull(buildingMap.getAllPOIsCategories().iterator().next());
        Assert.assertNotNull(buildingMap.getAllROIs());
        Assert.assertNotNull(buildingMap.getAllROIs().iterator().next());
        Assert.assertNotNull(buildingMap.getAllROIs().iterator().next().getId());
        Assert.assertNotNull(buildingMap.getAllROIs().iterator().next().getUUID());
        Assert.assertNotNull(buildingMap.getAllROIs().iterator().next().getFloor());
        Assert.assertNotNull(buildingMap.getAllROIs().iterator().next().getMajor());
        Assert.assertNotNull(buildingMap.getAllROIs().iterator().next().getMinor());
        Assert.assertNotNull(buildingMap.getAllROIs().iterator().next().getAllNearbyPOIs());
        Assert.assertNotNull(buildingMap.getAllROIs().iterator().next().getAllNearbyPOIs().iterator().next());
    }
}
