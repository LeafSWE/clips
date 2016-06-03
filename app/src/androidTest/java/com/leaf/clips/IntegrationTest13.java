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
public class IntegrationTest13 {

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
    public void shouldAccessCorrectlyToLocalAndRemoteDB() throws Exception {
        Assert.assertNotNull(databaseService.findAllBuildings());
        Assert.assertNotNull(databaseService.findAllRemoteBuildings());
        Assert.assertTrue(databaseService.isRemoteMapPresent(666));
        Assert.assertNotNull(databaseService.findRemoteBuildingByMajor(666));
        BuildingMap buildingMap = databaseService.findBuildingByMajor(666);
        Assert.assertNotNull(buildingMap);
        Assert.assertTrue(databaseService.isBuildingMapPresent(666));
        databaseService.deleteBuilding(buildingMap);
        Assert.assertFalse(databaseService.isBuildingMapPresent(666));
        Assert.assertFalse(databaseService.isBuildingMapUpdated(666));
        databaseService.findRemoteBuildingByMajor(666);
        Assert.assertTrue(databaseService.isBuildingMapUpdated(666));
    }
}
