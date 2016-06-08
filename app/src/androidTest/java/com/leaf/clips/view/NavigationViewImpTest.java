package com.leaf.clips.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.model.navigator.ProcessedInformationImp;
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
import com.leaf.clips.presenter.NavigationActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 *
 * TU136
 * Viene verificato che sia possibile visualizzare le istruzioni di navigazione.
 *
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationViewImpTest {

    NavigationActivity testActivity;

    @Rule
    public ActivityTestRule<NavigationActivity> mActivityRule =
            new ActivityTestRule<>(NavigationActivity.class);

    @Before
    public void setUp() throws Exception {

        testActivity = mActivityRule.getActivity();
    }

    @Test
    public void shouldShowNavigationInformation() throws Exception {
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


        NavigationInformation navigationInformation =
                new NavigationInformationImp(basicInformation,detailedInformation,photoInformation);
        EnrichedEdge enrichedEdge = new StairEdge(starROI,endROI,15,15,15,navigationInformation);
        ProcessedInformation processedInformation = new ProcessedInformationImp(enrichedEdge);
        List<ProcessedInformation> processedInformations = new ArrayList<>();
        processedInformations.add(processedInformation);
        NavigationView mockNavigationView = Mockito.mock(NavigationView.class);

        when(mockNavigationInstruction.size()).thenReturn(0);


        Field fieldNavView = null;
        try {
            fieldNavView = testActivity.getClass().getDeclaredField("view");
            fieldNavView.setAccessible(true);
            fieldNavView.set(testActivity, mockNavigationView);
            Field fieldNavigationInstruction;
            fieldNavigationInstruction =
                    testActivity.getClass().getDeclaredField("navigationInstruction");
            fieldNavigationInstruction.setAccessible(true);
            fieldNavigationInstruction.set(testActivity, processedInformations);
        } catch (Exception e) {
            e.printStackTrace();
        }

        testActivity.informationUpdate(processedInformation);
        verify(mockNavigationView,times(1)).refreshInstructions(0);
    }
}
