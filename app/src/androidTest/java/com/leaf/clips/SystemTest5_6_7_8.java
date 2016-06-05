package com.leaf.clips;

import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import com.leaf.clips.model.AbsBeaconReceiverManager;
import com.leaf.clips.model.InformationManagerImp;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.beacon.MyBeaconImp;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.presenter.HomeActivity;

import junit.framework.Assert;

import org.altbeacon.beacon.Beacon;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class SystemTest5_6_7_8 {
    HomeActivity testActivity;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();
        new Thread(new Runnable() {
            @Override
            public void run() {

                Field field = null;
                try {
                    field = HomeActivity.class.getDeclaredField("informationManager");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                field.setAccessible(true);
                InformationManagerImp informationManager = null;
                try {
                    informationManager = (InformationManagerImp) field.get(testActivity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                try {
                    field = AbsBeaconReceiverManager.class.getDeclaredField("lastBeaconsSeen");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                field.setAccessible(true);
                PriorityQueue<MyBeacon> lastBeaconsSeen = null;
                try {
                    lastBeaconsSeen = (PriorityQueue<MyBeacon>) field.get(informationManager);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                LinkedList<Long> l = new LinkedList<Long>();
                l.add((long) 0);
                l.add((long) 0);
                l.add((long) 0);
                l.add((long) 0);
                l.add((long) 0);
                l.add((long) 0);
                l.add((long) 0);
                l.add((long) 0);
                MyBeacon b = new MyBeaconImp(new Beacon.Builder()
                        .setId1("19235dd2-574a-4702-a42e-caccac06e325")
                        .setId2("666").setId3("0").setDataFields(l).build());
                lastBeaconsSeen.add(b);

                try {
                    field = InformationManagerImp.class.getDeclaredField("map");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                field.setAccessible(true);
                BuildingMap map = null;
                try {
                    map = (BuildingMap) field.get(informationManager);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (!informationManager.getDatabaseService().isBuildingMapPresent(666)) {
                    try {
                        informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                map = informationManager.getDatabaseService().findBuildingByMajor(666);

            }}).run();

            Thread.sleep(8000);
    }

    @Test
    public void shouldDisplayBuildingName() throws Exception {
        Assert.assertTrue(!(((TextView) testActivity.findViewById(R.id.view_building_name)).getText()).equals(""));
    }

    @Test
    public void shouldDisplayDescription() throws Exception {
        Assert.assertTrue(!(((TextView) testActivity.findViewById(R.id.view_building_description)).getText()).equals(""));
    }

    @Test
    public void shouldDisplayOpeningHours() throws Exception {
        Assert.assertTrue(!(((TextView) testActivity.findViewById(R.id.view_building_opening_hours)).getText()).equals(""));
    }

    @Test
    public void shouldDisplayAddress() throws Exception {
        Assert.assertTrue(!(((TextView) testActivity.findViewById(R.id.view_address)).getText()).equals(""));
    }
}
