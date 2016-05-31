package com.leaf.clips.presenter;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.leaf.clips.model.usersetting.Setting;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
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
 * TU121
 * Viene verificato che sia possibile gestire le opzioni sviluppatore utilizzando
 * la classe MainDeveloperPresenter
 */
@RunWith(AndroidJUnit4.class)
public class MainDeveloperPresenterTest {
    MainDeveloperPresenter testPresenter;
    Setting mockSetting;

    @Rule
    public ActivityTestRule<MainDeveloperPresenter> mActivityRule = new ActivityTestRule<>(MainDeveloperPresenter.class);

    @Before
    public void setUp() throws Exception {
        testPresenter = mActivityRule.getActivity();

        mockSetting = Mockito.mock(Setting.class);

        when(mockSetting.isDeveloper()).thenReturn(true);
        Field field = testPresenter.getClass().getDeclaredField("userSetting");

        field.setAccessible(true);

        field.set(testPresenter, mockSetting);
    }

    @Test
    public void canIStartDeveloperOptions() throws Exception {
        Intents.init();
        testPresenter.startDeveloperOptions();
        intended(hasComponent(MainDeveloperActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void canIStartDeveloperUnlocker() throws Exception {
        Intents.init();
        testPresenter.startDeveloperUnlocker();
        intended(hasComponent(DeveloperUnlockerActivity.class.getName()));

        Intents.release();
    }
}