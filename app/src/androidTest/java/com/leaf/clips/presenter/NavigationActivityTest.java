package com.leaf.clips.presenter;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationListener;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.BuildingMapImp;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.model.navigator.ProcessedInformationImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestInformation;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterestImp;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.navigator.graph.edge.StairEdge;
import com.leaf.clips.model.navigator.graph.navigationinformation.BasicInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.DetailedInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformationImp;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;
import com.leaf.clips.view.NavigationViewImp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.mockito.Mockito.when;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

/**
 * Testi di unità 117 + 118
 */

public class NavigationActivityTest {
    private NavigationActivity navigationActivity;
    private NavigationViewImp mockNavigationViewImp;
    private NavigationManager mockNavigationManager;
    private InformationManager mockInformationManager;
    private List<ProcessedInformation> mockNavigationInstruction;
    private BuildingMap mockBuildingMap;

    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule = new ActivityTestRule<>(NavigationActivity.class);

    @Before
    public void setUp() throws Exception {

        navigationActivity = mActivityRule.getActivity();
        /*mockNavigationViewImp = Mockito.mock(NavigationViewImp.class);
        mockNavigationManager = Mockito.mock(NavigationManager.class);
        mockInformationManager = Mockito.mock(InformationManager.class);

        mockBuildingMap = Mockito.mock(BuildingMap.class);


        Field field = navigationActivity.getClass().getDeclaredField("infoManager");
        Field field2 = navigationActivity.getClass().getDeclaredField("navigationManager");
        Field field3 = navigationActivity.getClass().getDeclaredField("view");


        Field field5 = navigationActivity.getClass().getDeclaredField("poiId");


        field.setAccessible(true);
        field2.setAccessible(true);
        field3.setAccessible(true);

        field5.setAccessible(true);

        field.set(navigationActivity, mockInformationManager);
        field2.set(navigationActivity, mockNavigationManager);
        field3.set(navigationActivity, mockNavigationViewImp);

        field5.set(navigationActivity, 1);
        */
    }

    @Test
    public void isAnErrorPath() {
        mockInformationManager = Mockito.mock(InformationManager.class);


    }

    @Test
    public void isInformationUpdate() {
        // TODO: 5/16/16
    }

    @Test
    public void isShownDetailedInformation () throws Exception {
        mockNavigationInstruction = Mockito.mock(List.class);

        RegionOfInterest starROI = new RegionOfInterestImp(15, "uuid", 666, 15);
        RegionOfInterest endROI = new RegionOfInterestImp(15, "uuid", 666, 16);

        BasicInformation basicInformation = new BasicInformation("Basic");
        DetailedInformation detailedInformation = new DetailedInformation("Detailed");

        PhotoRef photoRef = new PhotoRef(0, new URI("www.google.com"));
        Collection<PhotoRef> photoRefs = new ArrayList<>();
        photoRefs.add(photoRef);
        PhotoInformation photoInformation = new PhotoInformation(photoRefs);


        NavigationInformation navigationInformation = new NavigationInformationImp(basicInformation,detailedInformation,photoInformation);
        EnrichedEdge enrichedEdge = new StairEdge(starROI,endROI,15,15,15,navigationInformation);
        ProcessedInformation processedInformation = new ProcessedInformationImp(enrichedEdge);
        List<ProcessedInformation> processedInformations = new ArrayList<>();
        processedInformations.add(processedInformation);

        when(mockNavigationInstruction.get(new Integer(0))).thenReturn(processedInformation);
        Field field;
        field = navigationActivity.getClass().getDeclaredField("navigationInstruction");
        field.setAccessible(true);
        field.set(navigationActivity, processedInformations);

        Intents.init();
        navigationActivity.showDetailedInformation(0);
        intended(hasComponent(DetailedInformationActivity.class.getName()));
        Intents.release();
    }
}