package com.leaf.clips;
/**
 * @author Federico Tavella
 * @version 0.02
 * @since 0.00
 */

import android.content.Intent;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.v4.content.LocalBroadcastManager;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.view.View;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.beacon.MyBeaconImp;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.MyApplication;
import com.leaf.clips.presenter.NavigationActivity;
import com.leaf.clips.presenter.NearbyPoiActivity;

import org.altbeacon.beacon.Beacon;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Integration Test 1
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntegrationTest1 extends InstrumentationTestCase {

    HomeActivity testActivity;
    InformationManager informationManager;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void init() throws NoSuchFieldException, IllegalAccessException {

        testActivity = mActivityRule.getActivity();
        testActivity = mActivityRule.getActivity();
        Field field = HomeActivity.class.getDeclaredField("informationManager");
        field.setAccessible(true);
        informationManager = (InformationManager) field.get(testActivity);


        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        if (Build.VERSION.SDK_INT >= 23) {

            UiObject locationPermissions = device.findObject(new UiSelector().text("Consenti"));
            if(locationPermissions.exists()){
                try{
                    locationPermissions.click();

                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        UiObject allowPermissions = device.findObject(new UiSelector().text("OK"));
        while (allowPermissions.exists()) {
            try {
                allowPermissions.click();
                UiObject activateLocation = device.findObject(new UiSelector().text("Non attiva"));
                if(activateLocation.exists() && !activateLocation.isChecked()){
                    activateLocation.click();
                    device.pressBack();
                }

            } catch (UiObjectNotFoundException e) {
                Log.e("TEST", "There is no permissions dialog to interact with ");
            }
        }

        if(Build.VERSION.SDK_INT >= 23){
            UiObject storagePermissions = device.findObject(new UiSelector().text("Consenti"));
            if(storagePermissions.exists()){
                try{
                    storagePermissions.click();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testWholeSystem() throws InterruptedException, UiObjectNotFoundException {

        PriorityQueue<MyBeacon> p = new PriorityQueue<>();

        List<Long> list = new LinkedList<>();
        list.add((long) 12);
        UUID uuid = UUID.fromString(MyApplication.getConfiguration().getApplicationUUID());

        p.add(new MyBeaconImp(new Beacon.Builder().setId1(uuid.toString()).setId2("666").setId3("0")
                .setDataFields(list).build()));

        Intent msg = new Intent("beaconsDetected");
        msg.putExtra("queueOfBeacons", p);
        LocalBroadcastManager.getInstance(InstrumentationRegistry.getTargetContext()).sendBroadcast(msg);

        Thread.sleep(5000);



        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject allowDownloadOfMap = device.findObject(new UiSelector().text("OK"));
        if(allowDownloadOfMap.exists())
            allowDownloadOfMap.click();

        //Thread.sleep(10000);

        UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));

        appViews1.scrollForward();
        appViews1.scrollTextIntoView("La struttura");

        UiObject category = device.findObject(new UiSelector().text("Entrate"));
        category.click();


        UiObject item = device.findObject(new UiSelector().text("Entrata torre D"));
        item.click();

        UiObject lastListItem = device.findObject(new UiSelector().text("Destinazione Raggiunta"));
        assertTrue(lastListItem.exists());
    }
}
