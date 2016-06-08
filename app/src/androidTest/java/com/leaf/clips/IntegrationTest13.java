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

import java.io.IOException;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class IntegrationTest13 {

    private DatabaseService databaseService;
    int major;
    
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
        major = 666;
    }

    @Test
    public void shouldAccessCorrectlyToLocalAndRemoteDB() throws Exception {
        Assert.assertNotNull(databaseService.findAllBuildings());
        Assert.assertNotNull(databaseService.findAllRemoteBuildings());
        Assert.assertTrue(databaseService.isRemoteMapPresent(major));
        Assert.assertNotNull(databaseService.findRemoteBuildingByMajor(major));
        BuildingMap buildingMap = databaseService.findBuildingByMajor(major);
        Assert.assertNotNull(buildingMap);
        Assert.assertTrue(databaseService.isBuildingMapPresent(major));
        databaseService.deleteBuilding(buildingMap);
        Assert.assertFalse(databaseService.isBuildingMapPresent(major));
        databaseService.findRemoteBuildingByMajor(major);
        Assert.assertTrue(databaseService.isBuildingMapUpdated(major));
    }

    @Test
    public void shouldNotFindLocalMap(){
        Assert.assertEquals(false, databaseService.isBuildingMapPresent(major));
    }

    @Test
    public void shouldFoundRemoteMap(){
        try {
            Assert.assertEquals(true, databaseService.isRemoteMapPresent(major));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldRetrieveAndInsertMap(){

        try {
            databaseService.findRemoteBuildingByMajor(major);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(true, databaseService.isBuildingMapPresent(major));

    }

    @Test
    public void shouldRetrieveInsertAndDeleteMap(){

        try {
            databaseService.findRemoteBuildingByMajor(major);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(true, databaseService.isBuildingMapPresent(major));
        databaseService.deleteBuilding(databaseService.findBuildingByMajor(major));

        Assert.assertEquals(false, databaseService.isBuildingMapPresent(major));

    }
}
