package com.leaf.clips;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.model.usersetting.SettingImp;
import com.leaf.clips.presenter.DeveloperUnlockerActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
@RunWith(AndroidJUnit4.class)
public class SystemTest16 {
    DeveloperUnlockerActivity testActivity;
    Setting setting;
    final String DEVELOPER_CODE = "developerKey";
    final String USER_PREFERENCES = "userKey";
    SharedPreferences sharedPreferences;

    @Rule
    public ActivityTestRule<DeveloperUnlockerActivity> mActivityRule =
            new ActivityTestRule<>(DeveloperUnlockerActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();
        sharedPreferences = testActivity.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(DEVELOPER_CODE);
        editor.commit();
        setting = new SettingImp(testActivity);
    }

    @Test
    public void shouldInsertDeveloperCode() throws Exception {
        onView(withId(R.id.developer_code)).perform(click(), typeText("miriade"));
        Assert.assertTrue(!(((EditText) testActivity.findViewById(R.id.developer_code)).getText()).equals(""));
    }

    @Test
    public void shouldUnlockDeveloperCode() throws Exception {
        onView(withId(R.id.developer_code)).perform(click(), typeText("miriade"));
        onView(withId(R.id.developer_login_button)).perform(click());
        Assert.assertTrue(setting.isDeveloper());
    }
}