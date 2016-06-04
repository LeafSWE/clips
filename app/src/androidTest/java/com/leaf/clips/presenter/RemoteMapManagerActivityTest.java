package com.leaf.clips.presenter;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.notification.StatusBarNotification;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.leaf.clips.model.dataaccess.dao.DaoFactoryHelper;
import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.service.BuildingServiceTest;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.dataaccess.service.ServiceHelper;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.BuildingMapImp;
import com.leaf.clips.view.LocalMapManagerViewImp;
import com.leaf.clips.view.RemoteMapManagerView;
import com.leaf.clips.view.RemoteMapManagerViewImp;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.mockito.Mockito.when;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 *
 * TU125
 * Viene verificato che sia possibile effettuare il download di una mappa utilizzando
 * la classe Remote MapManager.
 */

@RunWith(AndroidJUnit4.class)
public class RemoteMapManagerActivityTest {
    RemoteMapManagerActivity testActivity;
    RemoteMapManagerViewImp testView;
    DatabaseService dbService;
    public final static int major = 666;
    BuildingMap map;
    public boolean downloaded = false;

    @Rule
    public ActivityTestRule<RemoteMapManagerActivity> mActivityRule =
            new ActivityTestRule<>(RemoteMapManagerActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();

        testView = Mockito.mock(RemoteMapManagerViewImp.class);
        dbService = ServiceHelper.getService(DaoFactoryHelper.getInstance()
                .getSQLiteDaoFactory(new MapsDbHelper(testActivity.getApplicationContext())
                        .getWritableDatabase()),
                DaoFactoryHelper.getInstance().getRemoteDaoFactory(), MapsDbHelper.REMOTE_DB_URL);

        Field field = testActivity.getClass().getDeclaredField("databaseService");
        Field field2 = testActivity.getClass().getDeclaredField("view");

        field.setAccessible(true);
        field2.setAccessible(true);

        field.set(testActivity, dbService);
        field2.set(testActivity, testView);
    }

    @Test
    public void canIDownloadAMap() throws Exception {
        testActivity.downloadMap(major);
        Assert.assertEquals(dbService.isBuildingMapPresent(major), true);
    }
}
