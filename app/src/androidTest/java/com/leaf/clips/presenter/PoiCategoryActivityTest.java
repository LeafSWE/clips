package com.leaf.clips.presenter;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestInformation;
import com.leaf.clips.view.PoiCategoryViewImp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */
@RunWith(AndroidJUnit4.class)
public class PoiCategoryActivityTest {
    private PoiCategoryActivity testActivity;
    private InformationManager mockIM;
    private PoiCategoryViewImp testView;
    private List<PointOfInterest> mockPoiList;

    @Rule
    public ActivityTestRule<PoiCategoryActivity> mActivityRule = new ActivityTestRule<>(PoiCategoryActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();

        testView = Mockito.mock(PoiCategoryViewImp.class);
        mockIM = Mockito.mock(InformationManager.class);
        mockPoiList = new ArrayList<>();
        PointOfInterestInformation info = new PointOfInterestInformation("1C150","Descrizione POI","Aule");
        mockPoiList.add(new PointOfInterestImp(0, info));

        when(mockIM.getPOIsByCategory(anyString())).thenReturn(mockPoiList);

        Field field = testActivity.getClass().getDeclaredField("view");
        Field field2 = testActivity.getClass().getDeclaredField("informationManager");
        Field field3 = testActivity.getClass().getDeclaredField("poiList");

        field.setAccessible(true);
        field2.setAccessible(true);
        field3.setAccessible(true);

        field.set(testActivity,testView);
        field2.set(testActivity,mockIM);
        field3.set(testActivity,mockPoiList);
    }

    @Test
    public void testStartNavigation() throws Exception {
        Intents.init();
        testActivity.startNavigation(0);
        intended(hasComponent(NavigationActivity.class.getName()));

        Intents.release();
    }
}