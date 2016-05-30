package com.leaf.clips;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.test.rule.ActivityTestRule;

import com.leaf.clips.model.InformationManagerImp;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.beacon.MyBeaconImp;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.presenter.HomeActivity;

import org.altbeacon.beacon.Beacon;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.PriorityQueue;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class SystemTest1 {

    HomeActivity testActivity;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Field field = null;
                    field = HomeActivity.class.getDeclaredField("informationManager");
                    field.setAccessible(true);
                    InformationManagerImp informationManager = null;

                    informationManager = (InformationManagerImp) field.get(testActivity);
                    if (!informationManager.getDatabaseService().isBuildingMapPresent(666))
                        informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
                    field = InformationManagerImp.class.getDeclaredField("lastBeaconsSeen");
                    field.setAccessible(true);
                    PriorityQueue<MyBeacon> lastBeaconsSeen = null;
                    lastBeaconsSeen = (PriorityQueue<MyBeacon>) field.get(informationManager);
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
                    field = InformationManagerImp.class.getDeclaredField("map");
                    field.setAccessible(true);
                    BuildingMap map = null;
                    map = (BuildingMap) field.get(informationManager);

                    map = informationManager.getDatabaseService().findBuildingByMajor(666);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }});
        t.start();
        Thread.sleep(5000);
        t.join();
    }

    //TS1.10
    @Test
    public void shouldAlertStartNavigationWithNoInternet() throws Exception{
        //Assert.assertTrue(false);
        onView(withId(R.id.view_poi_category_list)).perform(scrollTo());
        onData(anything())
                .inAdapterView(withId(R.id.view_poi_category_list))
                .atPosition(0)
                .perform(click());
        Context context = testActivity.getApplicationContext();

        /*final ConnectivityManager conman = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Method dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        dataMtd.setAccessible(false);
        dataMtd.invoke(conman, false);*/

        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);

        onData(anything())
                .inAdapterView(withId(R.id.category_list))
                .atPosition(0)
                .perform(click());

        Thread.sleep(1000);

        onView(withText(R.string.ok))
                .perform(click());

        //entro nella categoria aule
        //aula 1c150
        //verifico che sia presente l'alert

        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }
}
