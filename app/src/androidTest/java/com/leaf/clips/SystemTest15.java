package com.leaf.clips;

import android.content.Intent;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.content.LocalBroadcastManager;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.beacon.MyBeaconImp;
import com.leaf.clips.model.dataaccess.dao.BuildingTable;
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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class SystemTest15 {
    HomeActivity testActivity;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();
        Thread t = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Field field = null;
                        try {
                            field = HomeActivity.class.getDeclaredField("informationManager");
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        field.setAccessible(true);
                        InformationManager informationManager = null;
                        try {
                            informationManager = (InformationManager) field.get(testActivity);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        if(!informationManager.getDatabaseService().isBuildingMapPresent(666))
                            try {
                                informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        try {
                            Thread.sleep(1*1000);
                        } catch (InterruptedException e) {
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
                        PriorityQueue<MyBeacon> p = new PriorityQueue<>();
                        p.add(b);
                        Intent msg = new Intent("beaconsDetected");
                        msg.putExtra("queueOfBeacons", p);
                        for(int i = 0; i < 3; i++) {
                            LocalBroadcastManager.getInstance(testActivity).sendBroadcast(msg);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });
        t.start();
        Thread.sleep(5000);
        t.join();
        try {
            onView(withText(R.string.ok)).perform(click());
        } catch (Exception e){}
    }

    //TS15.1
    @Test
    public void shouldAccessLocalMaps() throws Exception {
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
    }

    //TS15.2
    @Test
    public void shouldInstallNewMap() throws Exception {
        Field field = HomeActivity.class.getDeclaredField("informationManager");
        field.setAccessible(true);
        InformationManager informationManager = (InformationManager)field.get(testActivity);
        BuildingTable firstRemoteBuilding = informationManager.getDatabaseService()
                                                .findAllRemoteBuildings()
                                                .iterator()
                                                .next();
        int major = firstRemoteBuilding.getMajor();
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onView(withId(R.id.fab_add_new_map)).perform(click());
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewRemoteMaps))
                .atPosition(0)
                .onChildView(withId(R.id.download_remote_map))
                .perform(click());
        Assert.assertTrue(informationManager.getDatabaseService().isBuildingMapPresent(major));
    }

    //TS15.3
    @Test
    public void shouldRemoveMaps() throws Exception {
        Field field = HomeActivity.class.getDeclaredField("informationManager");
        field.setAccessible(true);
        InformationManager informationManager = (InformationManager)field.get(testActivity);
        if(!informationManager.getDatabaseService().isBuildingMapPresent(666))
            informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewLocalMaps))
                .atPosition(0)
                .onChildView(withId(R.id.removeLocalMap))
                .perform(click());
        onView(withText(R.string.ok)).perform(click());
        Assert.assertFalse(informationManager.getDatabaseService().isBuildingMapPresent(666));
    }

    //TS15.4
    @Test
    public void shouldUpdateMaps() throws Exception {
        Field field = HomeActivity.class.getDeclaredField("informationManager");
        field.setAccessible(true);
        InformationManager informationManager = (InformationManager)field.get(testActivity);
        if(!informationManager.getDatabaseService().isBuildingMapPresent(666))
            informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewLocalMaps))
                .atPosition(0)
                .onChildView(withId(R.id.updateLocalMap))
                .perform(click());
        onView(withText(R.string.ok)).perform(click());
        Assert.assertTrue(informationManager.getDatabaseService().isBuildingMapUpdated(666));
    }

    //TS15.5
    @Test
    public void shouldAccessLocalMapName() throws Exception {
        Field field = HomeActivity.class.getDeclaredField("informationManager");
        field.setAccessible(true);
        InformationManager informationManager = (InformationManager)field.get(testActivity);
        if(!informationManager.getDatabaseService().isBuildingMapPresent(666))
            informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewLocalMaps))
                .atPosition(0)
                .onChildView(withId(R.id.textViewLocalMapName))
                .check(matches(not(withText(""))));
    }

    //TS15.6
    @Test
    public void shouldAccessLocalMapAddress() throws Exception {
        Field field = HomeActivity.class.getDeclaredField("informationManager");
        field.setAccessible(true);
        InformationManager informationManager = (InformationManager)field.get(testActivity);
        if(!informationManager.getDatabaseService().isBuildingMapPresent(666))
            informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewLocalMaps))
                .atPosition(0)
                .onChildView(withId(R.id.textViewLocalMapDescription))
                .check(matches(not(withText(""))));
    }

    //TS15.7
    @Test
    public void shouldAccessLocalMapDescription() throws Exception {
        Field field = HomeActivity.class.getDeclaredField("informationManager");
        field.setAccessible(true);
        InformationManager informationManager = (InformationManager)field.get(testActivity);
        if(!informationManager.getDatabaseService().isBuildingMapPresent(666))
            informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewLocalMaps))
                .atPosition(0)
                .onChildView(withId(R.id.textViewLocalMapAddres))
                .check(matches(not(withText(""))));
    }

    //TS15.8
    @Test
    public void shouldAccessLocalMapSize() throws Exception {
        Field field = HomeActivity.class.getDeclaredField("informationManager");
        field.setAccessible(true);
        InformationManager informationManager = (InformationManager)field.get(testActivity);
        if(!informationManager.getDatabaseService().isBuildingMapPresent(666))
            informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewLocalMaps))
                .atPosition(0)
                .onChildView(withId(R.id.textViewLocalMapSize))
                .check(matches(not(withText(""))));
    }

    //TS15.9
    @Test
    public void shouldAccessLocalMapVersion() throws Exception {
        Field field = HomeActivity.class.getDeclaredField("informationManager");
        field.setAccessible(true);
        InformationManager informationManager = (InformationManager)field.get(testActivity);
        if(!informationManager.getDatabaseService().isBuildingMapPresent(666))
            informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewLocalMaps))
                .atPosition(0)
                .onChildView(withId(R.id.textViewLocalMapVersion))
                .check(matches(not(withText(""))));
    }

    //TS15.10
    @Test
    public void shouldAccessRemoteMapName() throws Exception {
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onView(withId(R.id.fab_add_new_map)).perform(click());
        Thread.sleep(2000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewRemoteMaps))
                .atPosition(0)
                .onChildView(withId(R.id.textViewRemoteMapName))
                .check(matches(not(withText(""))));
    }

    //TS15.11
    @Test
    public void shouldAccessRemoteMapAddress() throws Exception {
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onView(withId(R.id.fab_add_new_map)).perform(click());
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewRemoteMaps))
                .atPosition(0)
                .onChildView(withId(R.id.textViewRemoteMapAddress))
                .check(matches(not(withText(""))));
    }

    //TS15.12
    @Test
    public void shouldAccessRemoteMapDescription() throws Exception {
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onView(withId(R.id.fab_add_new_map)).perform(click());
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewRemoteMaps))
                .atPosition(0)
                .onChildView(withId(R.id.textViewRemoteMapDescription))
                .check(matches(not(withText(""))));
    }

    //TS15.13
    @Test
    public void shouldAccessRemoteMapSize() throws Exception {
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onView(withId(R.id.fab_add_new_map)).perform(click());
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewRemoteMaps))
                .atPosition(0)
                .onChildView(withId(R.id.textViewRemoteMapSize))
                .check(matches(not(withText(""))));
    }

    //TS15.14
    @Test
    public void shouldAccessRemoteMapVersion() throws Exception {
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
        Thread.sleep(1000);
        onView(withId(R.id.fab_add_new_map)).perform(click());
        Thread.sleep(1000);
        onData(anything())
                .inAdapterView(withId(R.id.listViewRemoteMaps))
                .atPosition(0)
                .onChildView(withId(R.id.textViewRemoteMapVersion))
                .check(matches(not(withText(""))));
    }

}
