package com.leaf.clips.presenter;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.navigator.BuildingInformation;
import com.leaf.clips.model.navigator.BuildingMapImp;
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
 * TU105 & TU106 & TU107 & TU108 & TU109 & TU111
 */

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {
    HomeActivity testActivity;
    HomeViewImp testView;
    InformationManager mockIM;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();

        testView = Mockito.mock(HomeViewImp.class);
        mockIM = Mockito.mock(InformationManager.class);
        BuildingInformation bi = new BuildingInformation("Torre","Descrizione Torre","Apertura","Indirizzo");
        when(mockIM.getBuildingMap()).thenReturn(new BuildingMapImp(null, 0, 0, null, null, bi, "0MB"));
        Field field = testActivity.getClass().getDeclaredField("view");
        Field field2 = testActivity.getClass().getDeclaredField("informationManager");

        field.setAccessible(true);
        field2.setAccessible(true);

        field.set(testActivity, testView);
        field2.set(testActivity, mockIM);
    }

    @Test
    public void canIgetToNearbyPoiActivityTest() throws Exception {
        Intents.init();
        testActivity.showExplorer();
        intended(hasComponent(NearbyPoiActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void isBuildingAddressBeingUpdated() throws Exception{

        testActivity.updateBuildingAddress();

        Mockito.verify(testView).setBuildingAddress(Matchers.anyString());
    }

    @Test
    public void isBuildingNameBeingUpdated() throws Exception{

        testActivity.updateBuildingName();

        Mockito.verify(testView).setBuildingName(Matchers.anyString());
    }

    @Test
    public void isBuildingOpenHoursBeingUpdated() throws Exception{

        testActivity.updateBuildingOpeningHours();

        Mockito.verify(testView).setBuildingOpeningHours(Matchers.anyString());
    }

    @Test
    public void arePoiCategoriesBeingUpdated() throws Exception{

        testActivity.updatePoiCategoryList();

        Mockito.verify(testView).setPoiCategoryListAdapter(Matchers.anyListOf(String.class));
    }

    @Test
    public void arePoiCategoriesBeingShowed() throws Exception{
        Intents.init();
        testActivity.showPoisCategory("Aule");
        intended(hasComponent(PoiCategoryActivity.class.getName()));
        Intents.release();
    }
}