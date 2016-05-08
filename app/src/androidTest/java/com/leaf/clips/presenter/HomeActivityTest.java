package com.leaf.clips.presenter;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.leaf.clips.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void canIopenTheNavigationDrawer() throws Exception {
        onView(withId(R.id.drawer_layout_home)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout_home)).check(matches(isOpen()));
    }

    @Test
    public void canIclickTheExploreButton() throws Exception {
        onView(withId(R.id.fab_explore_button)).check(matches(ViewMatchers.isEnabled()));
    }

    @Test
    public void canIopenMainDeveloperPresenterFromNavigationDrawer() throws Exception {
        //TODO
    }

}