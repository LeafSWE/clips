package com.leaf.clips.presenter;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.BuildingMapImp;
import com.leaf.clips.view.LocalMapManagerViewImp;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.net.SocketException;
import java.util.Arrays;

import static org.mockito.Mockito.when;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 *
 * TU126
 * Viene verificato che sia possibile aggiornare o rimuovere una mappa già presente sul
 * dispostivo utilizzando la classe LocalMapActvitiy utilizzando LocalMapActivity.
 */

@RunWith(AndroidJUnit4.class)
public class LocalMapActivityTest {
    LocalMapActivity testActivity;
    LocalMapManagerViewImp testView;
    DatabaseService dbService;
    private final static int major = 666;
    BuildingMap map;

    @Rule
    public ActivityTestRule<LocalMapActivity> mActivityRule =
            new ActivityTestRule<>(LocalMapActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();

        testView = Mockito.mock(LocalMapManagerViewImp.class);
        dbService = Mockito.mock(DatabaseService.class);
        map = new BuildingMapImp(null, 1, 1, null, null, null, null);

        // I need to define the actions for updateBuildingMap and deleteBuildingMap
        Mockito.doThrow(new ArrayIndexOutOfBoundsException()).when(dbService).updateBuildingMap(major);
        Mockito.doThrow(new ArrayIndexOutOfBoundsException()).when(dbService).deleteBuilding(map);
        when(dbService.findBuildingByMajor(major)).thenReturn(map);

        Field field = testActivity.getClass().getDeclaredField("databaseService");
        Field field2 = testActivity.getClass().getDeclaredField("view");

        field.setAccessible(true);
        field2.setAccessible(true);

        field.set(testActivity, dbService);
        field2.set(testActivity, testView);
    }

    @Test
    public void canIUpdateAMap() throws Exception {
        try {
            testActivity.updateMap(major);
        } catch (Exception e) {
            Assert.assertEquals(e instanceof ArrayIndexOutOfBoundsException, true);
        }
    }

    @Test
    public void canIDeleteAMap() throws Exception {
        try {
            testActivity.deleteMap(major);
        } catch (Exception e) {
            Assert.assertEquals(e instanceof ArrayIndexOutOfBoundsException, true);
        }
    }
}
