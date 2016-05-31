package com.leaf.clips.presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.leaf.clips.R;
import com.leaf.clips.model.usersetting.PathPreference;
import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.model.usersetting.SettingImp;
import com.leaf.clips.view.PreferencesViewImp;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.lang.reflect.Field;


/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 *
 * TU124
 * Viene verificato che sia possibile salvare le preferenze utente utilizzando
 * la classe PreferencesActivity.
 */

@RunWith(AndroidJUnit4.class)
public class PreferencesActivityTest {
    PreferencesActivity testActivity;
    PreferencesViewImp testView;
    Setting mockSetting;
    final String USER_PREFERENCES = "userKey";
    SharedPreferences sharedPreferences;

    @Rule
    public ActivityTestRule<PreferencesActivity> mActivityRule =
            new ActivityTestRule<>(PreferencesActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();

        testView = Mockito.mock(PreferencesViewImp.class);
        mockSetting = new SettingImp(testActivity);

        //when(mockSetting.getPathPreference()).thenReturn();

        Field field = testActivity.getClass().getDeclaredField("view");
        Field field2 = testActivity.getClass().getDeclaredField("setting");

        field.setAccessible(true);
        field2.setAccessible(true);

        field.set(testActivity, testView);
        field2.set(testActivity, mockSetting);
    }

    @Test
    public void canISaveMyUserPreferences() throws Exception {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(testActivity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String elevatorPathPref = testActivity.getResources().getString(R.string.elevator_path_preferences);
        editor.putString("path_pref", elevatorPathPref);
        editor.commit();
        testActivity.savePreferences();
        PathPreference preference = mockSetting.getPathPreference();
        Assert.assertEquals(preference.toString(), "ELEVATOR_PREFERENCE");
    }
}