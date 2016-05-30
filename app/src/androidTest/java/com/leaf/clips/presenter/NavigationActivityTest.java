package com.leaf.clips.presenter;

import android.support.test.rule.ActivityTestRule;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationListener;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.BuildingMapImp;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestInformation;
import com.leaf.clips.view.NavigationViewImp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public class NavigationActivityTest {
    private NavigationActivity navigationActivity;
    private NavigationViewImp mockNavigationViewImp;
    private NavigationManager mockNavigationManager;
    private InformationManager mockInformationManager;
    private List<ProcessedInformation> processedInformationList;
    private BuildingMap mockBuildingMap;

    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule = new ActivityTestRule<>(NavigationActivity.class);

    @Before
    public void setUp() throws Exception {

        navigationActivity = mActivityRule.getActivity();
        mockNavigationViewImp = Mockito.mock(NavigationViewImp.class);
        mockNavigationManager = Mockito.mock(NavigationManager.class);
        mockInformationManager = Mockito.mock(InformationManager.class);

        mockBuildingMap = Mockito.mock(BuildingMap.class);


        List<PointOfInterest> listPois = new LinkedList<>();
        listPois.add(new PointOfInterestImp(1, new PointOfInterestInformation("1c150", "aula didattica", "aula")));

        when(mockBuildingMap)
        when(mockInformationManager.getBuildingMap()).thenReturn();

        Field field = navigationActivity.getClass().getDeclaredField("infoManager");
        Field field2 = navigationActivity.getClass().getDeclaredField("navigationManager");
        Field field3 = navigationActivity.getClass().getDeclaredField("view");
        Field field4 = navigationActivity.getClass().getDeclaredField("navigationInstruction");

        Field field5 = navigationActivity.getClass().getDeclaredField("poiId");


        field.setAccessible(true);
        field2.setAccessible(true);
        field3.setAccessible(true);
        field4.setAccessible(true);
        field5.setAccessible(true);

        field.set(navigationActivity, mockInformationManager);
        field2.set(navigationActivity, mockNavigationManager);
        field3.set(navigationActivity, mockNavigationViewImp);
        field4.set(navigationActivity, listPois);
        field5.set(navigationActivity, 1);



    }

    @Test
    public void isAnErrorPath() {
        // TODO: 5/16/16
    }

    @Test
    public void isInformationUpdate() {
        // TODO: 5/16/16
    }

    @Test
    public void isShownDetailedInformation () {

    }
}