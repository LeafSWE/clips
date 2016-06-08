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
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.HelpActivity;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.LogInformationActivity;
import com.leaf.clips.presenter.MainDeveloperActivity;
import com.leaf.clips.presenter.PreferencesActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 *
 * TU147
 * Viene verificato che vengano visualizzato il dettaglio di un log e che questo
 * possa essere eliminato.
 *
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LogInformationViewImpTest {

    MainDeveloperActivity testActivity;

    @Rule
    public ActivityTestRule<MainDeveloperActivity> mActivityRule =
            new ActivityTestRule<>(MainDeveloperActivity.class);

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
    public void shouldStartALogAndThenShowIt() {
        // You're supposed to run DeveloperUnlockerViewImpTest first
        Intents.init();
        testActivity.findViewById(R.id.start_log_button).callOnClick();
        onView(withId(R.id.fab_stop_logging)).perform(click());
        ListView log_list = (ListView) testActivity.findViewById(R.id.saved_log_list);
        log_list.getOnItemClickListener().onItemClick(null, log_list, 0, 0);
        intended(hasComponent(LogInformationActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void shouldLetYouDeleteThatLog(){
        // You're supposed to run DeveloperUnlockerViewImpTest first
        Intents.init();
        ListView log_list = (ListView) testActivity.findViewById(R.id.saved_log_list);
        log_list.getOnItemClickListener().onItemClick(null, log_list, 0, 0);
        onView(withId(R.id.fab_delete_log)).perform(click());
        onView(withText(R.string.delete_log_question)).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());
        intended(hasComponent(MainDeveloperActivity.class.getName()));
        Intents.release();
    }
}
