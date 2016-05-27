package com.leaf.clips;

import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;

import com.leaf.clips.model.InformationManager;
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
                if(!informationManager.getDatabaseService().isBuildingMapPresent(666))
                    try {
                        informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                try {
                    field = InformationManagerImp.class.getDeclaredField("lastBeaconsSeen");
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

                map = informationManager.getDatabaseService().findBuildingByMajor(666);

            }}).run();

        Thread.sleep(8000);
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
        // TODO: 27/05/16 quando implementata funzione mappe remote
    }

    //TS15.3
    @Test
    public void shouldSearchMaps() throws Exception {
        // TODO: 27/05/16 ricerca mappe verrà implementata?
    }

    //TS15.4
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
        Assert.assertFalse(informationManager.getDatabaseService().isBuildingMapPresent(666));
    }

    //TS15.5
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
                .onChildView(withId(R.id.removeLocalMap))
                .perform(click());
        //Assert.assertTrue(informationManager.getDatabaseService().isBuildingMapUpdated(666));
    }

    //TS15.6
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

    //TS15.7
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
                .onChildView(withId(R.id.textViewLocalMapAddres))
                .check(matches(not(withText(""))));
    }

    //TS15.8
    @Test
    public void shouldAccessLocalMapDescription() throws Exception {
        //non implementato
    }

    //TS15.9
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

    //TS15.10
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

    //TS15.11
    @Test
    public void shouldAccessRemoteMapName() throws Exception {
        // TODO: 27/05/16 quando implementata funzione mappe remote
    }

    //TS15.12
    @Test
    public void shouldAccessRemoteMapAddress() throws Exception {
        // TODO: 27/05/16 quando implementata funzione mappe remote
    }

    //TS15.13
    @Test
    public void shouldAccessRemoteMapDescription() throws Exception {
        // TODO: 27/05/16 quando implementata funzione mappe remote
    }

    //TS15.14
    @Test
    public void shouldAccessRemoteMapSize() throws Exception {
        // TODO: 27/05/16 quando implementata funzione mappe remote
    }

    //TS15.15
    @Test
    public void shouldAccessRemoteMapVersion() throws Exception {
        // TODO: 27/05/16 quando implementata funzione mappe remote
    }

    //TS15.16
    @Test
    public void shouldNotifyRemoteMapNotFound() throws Exception {
        // TODO: 27/05/16 verrà implementata?
    }
}
