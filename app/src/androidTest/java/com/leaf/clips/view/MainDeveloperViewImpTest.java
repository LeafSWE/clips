package com.leaf.clips.view;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.leaf.clips.R;
import com.leaf.clips.presenter.HelpActivity;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.MainDeveloperActivity;
import com.leaf.clips.presenter.PreferencesActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 *
 * TU145
 * Viene verificato che vengano visualizzati i log salvati.
 *
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainDeveloperViewImpTest {

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
    public void shouldShowYourSavedLogs(){
        // You're supposed to run DeveloperUnlockerViewImpTest first
        Intents.init();
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.nav_developer));
        intended(hasComponent(MainDeveloperActivity.class.getName()));
        Intents.release();
    }
}
