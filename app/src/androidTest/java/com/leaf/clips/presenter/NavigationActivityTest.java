package com.leaf.clips.presenter;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationListener;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.NoBeaconSeenException;
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
import com.leaf.clips.view.NavigationView;
import com.leaf.clips.view.NavigationViewImp;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule = new ActivityTestRule<>(NavigationActivity.class);

    @Before
    public void setUp() throws Exception {

        navigationActivity = mActivityRule.getActivity();
    }

    @Test
    public void isAnErrorPath() {
        InformationManager mockInformationManager;
        List<ProcessedInformation> mockNavigationInstruction;
        mockInformationManager = Mockito.mock(InformationManager.class);
        mockNavigationInstruction = Mockito.mock(List.class);

        Field field = null;
        try {
            field = NavigationActivity.class.getDeclaredField("informationManager");
            InformationManager informationManager = (InformationManager)field.get(navigationActivity);
            if(!informationManager.getDatabaseService().isBuildingMapPresent(666))
                informationManager.getDatabaseService().findRemoteBuildingByMajor(666);
            BuildingMap buildingMap = informationManager.getBuildingMap();
            when(mockInformationManager.getBuildingMap()).thenReturn(buildingMap);

            NavigationView mockNavigationView = Mockito.mock(NavigationView.class);

            Field fieldNavView = navigationActivity.getClass().getDeclaredField("view");
            fieldNavView.setAccessible(true);
            fieldNavView.set(navigationActivity, mockNavigationView);

            navigationActivity.pathError();
            verify(mockNavigationView, times(1)).setInstructionAdapter(mockNavigationInstruction);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoBeaconSeenException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void isInformationUpdate() {

        List<ProcessedInformation> mockNavigationInstruction;
        mockNavigationInstruction = Mockito.mock(List.class);

        RegionOfInterest starROI = new RegionOfInterestImp(15, "uuid", 666, 15);
        RegionOfInterest endROI = new RegionOfInterestImp(15, "uuid", 666, 16);

        BasicInformation basicInformation = new BasicInformation("Basic");
        DetailedInformation detailedInformation = new DetailedInformation("Detailed");

        PhotoRef photoRef = null;
        try {
            photoRef = new PhotoRef(0, new URI("www.google.com"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Collection<PhotoRef> photoRefs = new ArrayList<>();
        photoRefs.add(photoRef);
        PhotoInformation photoInformation = new PhotoInformation(photoRefs);


        NavigationInformation navigationInformation = new NavigationInformationImp(basicInformation,detailedInformation,photoInformation);
        EnrichedEdge enrichedEdge = new StairEdge(starROI,endROI,15,15,15,navigationInformation);
        ProcessedInformation processedInformation = new ProcessedInformationImp(enrichedEdge);
        List<ProcessedInformation> processedInformations = new ArrayList<>();
        processedInformations.add(processedInformation);
        NavigationView mockNavigationView = Mockito.mock(NavigationView.class);

        when(mockNavigationInstruction.size()).thenReturn(0);


        Field fieldNavView = null;
        try {
            fieldNavView = navigationActivity.getClass().getDeclaredField("view");
            fieldNavView.setAccessible(true);
            fieldNavView.set(navigationActivity, mockNavigationView);
            Field fieldNavigationInstruction;
            fieldNavigationInstruction = navigationActivity.getClass().getDeclaredField("navigationInstruction");
            fieldNavigationInstruction.setAccessible(true);
            fieldNavigationInstruction.set(navigationActivity, processedInformations);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        navigationActivity.informationUpdate(processedInformation);
        verify(mockNavigationView,times(1)).refreshInstructions(0);
    }

    @Test
    public void isShownDetailedInformation () throws Exception {
        List<ProcessedInformation> mockNavigationInstruction;
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