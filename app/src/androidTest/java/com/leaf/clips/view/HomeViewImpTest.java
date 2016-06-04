package com.leaf.clips.view;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.leaf.clips.R;
import com.leaf.clips.model.dataaccess.dao.DaoFactoryHelper;
import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.dataaccess.service.ServiceHelper;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.presenter.HomeActivity;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 *
 * TU135
 * Viene verificato che vengano visualizzate le informazioni di un edificio.
 *
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeViewImpTest {

    HomeActivity testActivity;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void init() throws InterruptedException {
        testActivity = mActivityRule.getActivity();

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
    public void shouldShowBuildingInformation() throws InterruptedException {
        // Ottengo la mappa usata per il testing
        DatabaseService dbService = ServiceHelper.getService(DaoFactoryHelper.getInstance()
                        .getSQLiteDaoFactory(new MapsDbHelper(testActivity.getApplicationContext())
                                .getWritableDatabase()),
                DaoFactoryHelper.getInstance().getRemoteDaoFactory(), MapsDbHelper.REMOTE_DB_URL);
        BuildingMap map = dbService.findBuildingByMajor(666);
        // Controllo ciò che viene mostrato a video
        Thread.sleep(5000);
        onView(withId(R.id.view_building_name)).check(matches(withText(map.getName())));
        onView(withId(R.id.view_building_opening_hours))
                .check(matches(withText(map.getOpeningHours())));
        onView(withId(R.id.view_address)).check(matches(withText(map.getAddress())));
    }
}
