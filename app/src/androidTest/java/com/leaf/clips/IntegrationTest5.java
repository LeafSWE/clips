package com.leaf.clips;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import android.content.Intent;
import android.hardware.SensorManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.NavigationListener;
import com.leaf.clips.model.compass.Compass;
import com.leaf.clips.model.compass.CompassListener;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.presenter.NavigationActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import javax.inject.Inject;

/**
 * Integration Test 5
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class IntegrationTest5 extends InstrumentationTestCase{

    NavigationActivity navigationActivity;
    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule =
            new ActivityTestRule<>(NavigationActivity.class);
    Compass compass;

    @Before
    public void init() throws NoSuchFieldException, IllegalAccessException {

        navigationActivity = mActivityRule.getActivity();
        Field field = NavigationActivity.class.getDeclaredField("compass");
        field.setAccessible(true);
        compass = (Compass) field.get(navigationActivity);
        Intent i = new Intent(InstrumentationRegistry.getTargetContext(), NavigationActivity.class);
        mActivityRule.launchActivity(i);
    }

    @Test
    public void shouldAccessToLastCoordinate(){
        Assert.assertNotNull(compass.getLastCoordinate());
    }

}
