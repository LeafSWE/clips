package com.leaf.clips;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.LocalBroadcastManager;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.NoBeaconSeenException;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.beacon.MyBeaconImp;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.MyApplication;
import com.leaf.clips.presenter.NavigationActivity;

import junit.framework.Assert;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Identifier;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;

/**
 * Integration Test 2 (needs WiFi)
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntegrationTest2 extends InstrumentationTestCase{

    InformationManager informationManager;
    NavigationManager navigationManager;

    NavigationActivity navigationActivity;

    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule =
            new ActivityTestRule<>(NavigationActivity.class);

    @Before
    public void init() throws NoSuchFieldException, IllegalAccessException {
        navigationActivity = mActivityRule.getActivity();
        Field field = NavigationActivity.class.getDeclaredField("informationManager");
        field.setAccessible(true);
        informationManager = (InformationManager) field.get(navigationActivity);
        field = NavigationActivity.class.getDeclaredField("navigationManager");
        field.setAccessible(true);
        navigationManager = (NavigationManager) field.get(navigationActivity);

    }


    @Test
    public void shouldBeAbleToRetrieveRemoteMap() throws InterruptedException {

        PriorityQueue<MyBeacon> p = new PriorityQueue<>();

        List<Long> list = new LinkedList<>();
        list.add((long) 12);
        UUID uuid = UUID.fromString(MyApplication.getConfiguration().getApplicationUUID());

        p.add(new MyBeaconImp(new Beacon.Builder().setId1(uuid.toString()).setId2("666").setId3("0").setDataFields(list).build()));

        Intent msg = new Intent("beaconsDetected");
        msg.putExtra("queueOfBeacons", p);
        LocalBroadcastManager.getInstance(InstrumentationRegistry.getTargetContext()).sendBroadcast(msg);

        Thread.sleep(1000);
        assertNotNull(informationManager.getAllVisibleBeacons().peek());
        WifiManager wifiManager = (WifiManager) InstrumentationRegistry.getTargetContext()
                .getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        assertTrue(wifiManager.isWifiEnabled());

        informationManager.downloadMapOfVisibleBeacons(true);

        Thread.sleep(1000);

        try {
            assertNotNull(informationManager.getBuildingMap());
        } catch (NoBeaconSeenException e) {
            e.printStackTrace();
        }

        Collection<String> categories = informationManager.getAllCategories();
        for(String category : categories){
            assertNotNull(category);
        }

        wifiManager.setWifiEnabled(false);
    }

    @Test (expected = NoBeaconSeenException.class)
    public void shouldNotHaveMap() throws NoBeaconSeenException {
        informationManager.getBuildingMap();
    }

    @Test
    public void shouldRetrieveAndDeleteMap() throws IOException, InterruptedException, NoBeaconSeenException {
        PriorityQueue<MyBeacon> p = new PriorityQueue<>();

        List<Long> list = new LinkedList<>();
        list.add((long) 12);
        UUID uuid = UUID.fromString(MyApplication.getConfiguration().getApplicationUUID());

        p.add(new MyBeaconImp(new Beacon.Builder().setId1(uuid.toString()).setId2("666").setId3("0").setDataFields(list).build()));

        Intent msg = new Intent("beaconsDetected");
        msg.putExtra("queueOfBeacons", p);
        LocalBroadcastManager.getInstance(InstrumentationRegistry.getTargetContext()).sendBroadcast(msg);

        Thread.sleep(1000);
        assertNotNull(informationManager.getAllVisibleBeacons().peek());
        WifiManager wifiManager = (WifiManager) InstrumentationRegistry.getTargetContext()
                .getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        assertTrue(wifiManager.isWifiEnabled());

        informationManager.downloadMapOfVisibleBeacons(false);

        Thread.sleep(1000);

        informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
        assertTrue(informationManager.getDatabaseService().isBuildingMapPresent(666));
        informationManager.getDatabaseService().deleteBuilding(informationManager.getBuildingMap());
        assertFalse(informationManager.getDatabaseService().isBuildingMapPresent(666));
    }

}
