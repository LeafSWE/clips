package com.leaf.clips.presenter;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.navigator.BuildingInformation;
import com.leaf.clips.model.navigator.BuildingMapImp;
import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.view.DeveloperUnlockerViewImp;
import com.leaf.clips.view.HomeViewImp;

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
 * TU122
 * Viene verificato che sia possibile gestire lo sblocco delle opzioni sviluppatore
 * utilizzando la classe DeveloperUnlockerActivity
 */
@RunWith(AndroidJUnit4.class)
public class DeveloperUnlockerActivityTest {
    DeveloperUnlockerActivity testActivity;
    DeveloperUnlockerViewImp testView;
    Setting mockSetting;
    String code;

    @Rule
    public ActivityTestRule<DeveloperUnlockerActivity> mActivityRule =
            new ActivityTestRule<>(DeveloperUnlockerActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();

        testView = Mockito.mock(DeveloperUnlockerViewImp.class);
        mockSetting = Mockito.mock(Setting.class);
        code = "miriade";

        when(mockSetting.unlockDeveloper(code)).thenReturn(true);

        Field field = testActivity.getClass().getDeclaredField("developerUnlockerView");
        Field field2 = testActivity.getClass().getDeclaredField("userSetting");

        field.setAccessible(true);
        field2.setAccessible(true);

        field.set(testActivity, testView);
        field2.set(testActivity, mockSetting);
    }

    @Test
    public void canIgetToNearbyPoiActivityTest() throws Exception {
        Intents.init();
        if(testActivity.unlockDeveloper(code)) {
            intended(hasComponent(MainDeveloperActivity.class.getName()));
            Intents.release();
        }
    }
}