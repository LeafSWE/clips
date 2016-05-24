package com.leaf.clips;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import com.leaf.clips.model.usersetting.InstructionPreference;
import com.leaf.clips.model.usersetting.PathPreference;
import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.model.usersetting.SettingImp;
import com.leaf.clips.presenter.PreferencesActivity;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntegrationTest4 extends InstrumentationTestCase{
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Rule
    public final ActivityTestRule<PreferencesActivity> preferencesActivityActivityTestRule =
            new ActivityTestRule<>(PreferencesActivity.class, true, true);

    @Test
    public void shouldSaveOnSharedPreferencesPathPreferencesElevator() {
        Espresso.onView(withText(R.string.path_preferences)).perform(click());
        Espresso.onView(withText(R.string.elevator_path_preferences)).perform(click());
        Setting setting = new SettingImp(context);
        Assert.assertEquals(PathPreference.ELEVATOR_PREFERENCE, setting.getPathPreference());
    }

    @Test
    public void shouldSaveOnSharedPreferencesPathPreferencesStair() {
        Espresso.onView(withText(R.string.path_preferences)).perform(click());
        Espresso.onView(withText(R.string.stair_path_preferences)).perform(click());
        Setting setting = new SettingImp(context);
        Assert.assertEquals(PathPreference.STAIR_PREFERENCE, setting.getPathPreference());
    }

    @Test
    public void shouldSaveOnSharedPreferencesPathPreferencesDefault() {
        Espresso.onView(withText(R.string.path_preferences)).perform(click());
        Espresso.onView(withText(R.string.default_path_preferences)).perform(click());
        Setting setting = new SettingImp(context);
        Assert.assertEquals(PathPreference.NO_PREFERENCE, setting.getPathPreference());
    }

    @Test
    public void shouldSaveOnSharedPreferencesInstructionPreferencesSonar() {
        Espresso.onView(withText(R.string.instruction_preferences)).perform(click());
        Espresso.onView(withText(R.string.sonar_instruction_prefereces)).perform(click());
        Setting setting = new SettingImp(context);
        Assert.assertEquals(InstructionPreference.SONAR, setting.getInstructionPreference());
    }
    @Test
    public void shouldSaveOnSharedPreferencesInstructionPreferencesTTS() {
        Espresso.onView(withText(R.string.instruction_preferences)).perform(click());
        Espresso.onView(withText(R.string.tts_instruction_preferences)).perform(click());
        Setting setting = new SettingImp(context);
        Assert.assertEquals(InstructionPreference.TEXT_TO_SPEECH, setting.getInstructionPreference());
    }

    @Test
    public void shouldSaveOnSharedPreferencesInstructionPreferencesDefault() {
        Espresso.onView(withText(R.string.instruction_preferences)).perform(click());
        Espresso.onView(withText(R.string.default_instruction_preferences)).perform(click());
        Setting setting = new SettingImp(context);
        Assert.assertEquals(InstructionPreference.NO_PREFERENCE, setting.getInstructionPreference());
    }
}
