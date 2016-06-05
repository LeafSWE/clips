package com.leaf.clips.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ListView;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.CompleteHomeFragment;
import com.leaf.clips.presenter.HomeActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.LinkedList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 *
 * TU135
 * Viene verificato che vengano visualizzate le informazioni di un edificio.
 *
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeViewImpTest {

    HomeActivity testActivity;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void init() throws Exception {
        testActivity = mActivityRule.getActivity();
        Thread.sleep(1000);
        try {
            onView(withText(R.string.ok)).perform(click());
            Thread.sleep(500);
            onView(withText(R.string.ok)).perform(click());
            Thread.sleep(500);
            onView(withText(R.string.ok)).perform(click());
        } catch (Exception e) {}
        CompleteHomeFragment completeHomeFragment = new CompleteHomeFragment();
        if(!testActivity.isFinishing()) {
            testActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.linear_layout_home, completeHomeFragment, "COMPLETE_FRAGMENT")
                    .addToBackStack("COMPLETE_FRAGMENT")
                    .commitAllowingStateLoss();
            testActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    testActivity.getSupportFragmentManager().executePendingTransactions();
                    Field field = null;
                    try {
                        field = HomeActivity.class.getDeclaredField("view");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    field.setAccessible(true);
                    HomeViewImp view = null;
                    try {
                        view = (HomeViewImp)field.get(testActivity);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    view.setBuildingName("BuildingName");
                    view.setBuildingAddress("BuildingAddress");
                    view.setBuildingOpeningHours("BuildingOpeningHours");
                    LinkedList<String> categories = new LinkedList<>();
                    categories.add("BuildingCategory");
                    view.setPoiCategoryListAdapter(categories);
                }
            });

        }
        Thread.sleep(5000);

    }

    @Test
    public void shouldShowBuildingInformation() throws Exception {
        String s =  ((TextView) testActivity.findViewById(R.id.view_building_name)).getText().toString();
        Assert.assertEquals("BuildingName", s);
        Assert.assertEquals("BuildingAddress", ((TextView) testActivity.findViewById(R.id.view_address)).getText());
        Assert.assertEquals("BuildingOpeningHours", ((TextView) testActivity.findViewById(R.id.view_building_opening_hours)).getText());
        Assert.assertEquals("BuildingCategory",
                (String) (((ListView) testActivity.findViewById(R.id.view_poi_category_list)).getAdapter().getItem(0)));
    }
}
