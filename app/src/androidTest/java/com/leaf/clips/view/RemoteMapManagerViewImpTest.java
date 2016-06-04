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
import android.view.View;

import com.leaf.clips.R;
import com.leaf.clips.presenter.HelpActivity;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.LocalMapActivity;
import com.leaf.clips.presenter.RemoteMapManagerActivity;

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
 * TU142
 * Viene verificato che siano visualizzate le impostazioni per gestire le mappe remote.
 *
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RemoteMapManagerViewImpTest {

    LocalMapActivity localMapActivity;

    @Rule
    public ActivityTestRule<LocalMapActivity> localMapARule =
            new ActivityTestRule<>(LocalMapActivity.class);

    @Before
    public void init() throws InterruptedException {
        localMapActivity = localMapARule.getActivity();
    }

    @Test
    public void shouldLaunchMapIntent() {
        Intents.init();
        localMapActivity.findViewById(R.id.fab_add_new_map).callOnClick();
        intended(hasComponent(RemoteMapManagerActivity.class.getName()));
        Intents.release();
    }
}
