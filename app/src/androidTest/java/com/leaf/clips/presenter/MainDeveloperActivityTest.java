package com.leaf.clips.presenter;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.navigator.BuildingInformation;
import com.leaf.clips.model.navigator.BuildingMapImp;
import com.leaf.clips.view.HomeViewImp;
import com.leaf.clips.view.MainDeveloperView;
import com.leaf.clips.view.MainDeveloperViewImp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.mockito.Mockito.when;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 *
 * TU123
 * Viene verificato che sia possibile avviare un nuovo log e accedere ai log salvati sul
 * dispositivo utilizzando la classe MainDeveloperActivity
 */

@RunWith(AndroidJUnit4.class)
public class MainDeveloperActivityTest {
    MainDeveloperActivity testActivity;
    MainDeveloperViewImp testView;
    InformationManager mockIM;

    @Rule
    public ActivityTestRule<MainDeveloperActivity> mActivityRule = new ActivityTestRule<>(MainDeveloperActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();

        testView = Mockito.mock(MainDeveloperViewImp.class);
        mockIM = Mockito.mock(InformationManager.class);

        Field field = testActivity.getClass().getDeclaredField("mainDeveloperView");
        Field field2 = testActivity.getClass().getDeclaredField("infoManager");

        field.setAccessible(true);
        field2.setAccessible(true);

        field.set(testActivity, testView);
        field2.set(testActivity, mockIM);
    }

    @Test
    public void canIShowDetailedLog() throws Exception {
        Intents.init();
        testActivity.showDetailedLog(0);
        intended(hasComponent(LogInformationActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void canIStartNewLog() throws Exception {
        Intents.init();
        testActivity.startNewLog();
        intended(hasComponent(LoggingActivity.class.getName()));

        Intents.release();
    }
}