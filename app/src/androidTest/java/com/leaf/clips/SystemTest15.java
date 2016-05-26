package com.leaf.clips;

import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;

import com.leaf.clips.presenter.HomeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class SystemTest15 {
    HomeActivity testActivity;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();
    }

    @Test
    public void shouldAccessLocalMaps() throws Exception {
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions.navigateTo(R.id.mapManager));
    }




}
