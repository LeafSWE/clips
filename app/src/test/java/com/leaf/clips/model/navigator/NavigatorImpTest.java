package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 */

import com.leaf.clips.BuildConfig;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.compass.Compass;
import com.leaf.clips.model.navigator.algorithm.DijkstraPathFinder;
import com.leaf.clips.model.navigator.algorithm.PathFinder;
import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

/**
 * TU33, TU34, TU35
 */
@RunWith(MockitoJUnitRunner.class)
public class NavigatorImpTest {

    final static String FAKE_BASIC_INFO = "FakeBasicInfo";
    final static String FAKE_DETAILED_INFO = "FakeDetailedInfo";

    @Mock
    private PhotoInformation mockEdgePhotos;

    private NavigatorImp navigatorImp;

    @Mock
    private Compass mockCompass;

    @Mock
    private RegionOfInterest mockStartRoi;

    @Mock
    private PointOfInterest mockNullEndPoi;

    @Mock
    private RegionOfInterest mockEndRoi;

    @Mock
    private PriorityQueue<MyBeacon> mockVisibleBeacons;

    @Mock
    private MyBeacon mockNearBeacon;

    @Mock
    private MapGraph mockMapGraph;

    @Mock
    private DijkstraPathFinder mockDijkstraPathFinder;

    private List<EnrichedEdge> fakeShortestPath;

    @Mock
    private EnrichedEdge mockEnrichedEdge;

    @Mock
    private EnrichedEdge mockEnrichedEdge2;

    @Mock
    private PointOfInterest mockEndPoi;

    private List<RegionOfInterest> fakeBelongingRoi;

    private List<ProcessedInformation> fakeProcessedInformation;

    @Mock
    private RegionOfInterest mockRoiInPoi;

    @Mock
    private RegionOfInterest mockRoiWithBeacon;

    @Before
    public void setUp() throws Exception {
        navigatorImp = new NavigatorImp(mockCompass, mockDijkstraPathFinder);

        fakeShortestPath = new ArrayList<>();
        fakeShortestPath.add(mockEnrichedEdge);
        fakeShortestPath.add(mockEnrichedEdge2);

        fakeBelongingRoi = new ArrayList<>();
        fakeBelongingRoi.add(mockRoiInPoi);

        when(mockCompass.getLastCoordinate()).thenReturn(180f);
        when(mockDijkstraPathFinder.calculatePath(mockMapGraph, mockStartRoi, mockRoiInPoi))
                .thenReturn(fakeShortestPath);
        when(mockEndPoi.getAllBelongingROIs()).thenReturn(fakeBelongingRoi);

        for (EnrichedEdge mockEE : fakeShortestPath) {
            when(mockEE.getBasicInformation()).thenReturn(FAKE_BASIC_INFO);
            when(mockEE.getDetailedInformation()).thenReturn(FAKE_DETAILED_INFO);
            when(mockEE.getPhotoInformation()).thenReturn(mockEdgePhotos);
            when(mockEE.getEndPoint()).thenReturn(mockRoiWithBeacon);
        }

        when(mockVisibleBeacons.peek()).thenReturn(mockNearBeacon);
        when(mockRoiWithBeacon.contains(mockVisibleBeacons.peek())).thenReturn(true);

        fakeProcessedInformation = new ArrayList<>();
        fakeProcessedInformation.add(new ProcessedInformationImp(mockEnrichedEdge));
        fakeProcessedInformation.add(new ProcessedInformationImp(mockEnrichedEdge2));
    }

    @Test(expected = NoGraphSetException.class)
    public void testCalculatePathException() throws Exception {
        navigatorImp.calculatePath(mockStartRoi, mockNullEndPoi);
        fail("Test not throw NoGraphException");
    }

    /**
     * Non esegue test in NavigatorImp.isShortestPath()
     * @throws NavigationExceptions
     */
    @Test
    public void testCalculatePath() throws NavigationExceptions {
        navigatorImp.setGraph(mockMapGraph);
        navigatorImp.calculatePath(mockStartRoi, mockEndPoi);
        List<EnrichedEdge> resultFakeShortestPath = navigatorImp.getPath();
        if (BuildConfig.DEBUG && !resultFakeShortestPath.containsAll(fakeShortestPath)) {
            fail("Shortest path is not same of shortest path expected");
        }
    }

    @Test(expected = PathException.class)
    public void testSetGraph() throws Exception {
        navigatorImp.setGraph(mockMapGraph);
        navigatorImp.calculatePath(mockStartRoi, mockNullEndPoi);
    }

    @Test(expected = NoNavigationInformationException.class)
    public void testGetAllInstructionsException() throws Exception {
        navigatorImp.getAllInstructions();
        fail("Test not throw NoNavigationInformationException");
    }

    /**
     * N.B. il test utilizza la classe ProcessedInformation istanziandola
     * @throws NavigationExceptions
     */
    @Test
    public void testGetAllInstruction() throws NavigationExceptions{
        navigatorImp.setGraph(mockMapGraph);
        navigatorImp.calculatePath(mockStartRoi, mockEndPoi);
        List<ProcessedInformation> resultFakeProcessedInformation =
                navigatorImp.getAllInstructions();
        if ( resultFakeProcessedInformation.size() != fakeProcessedInformation.size()) {
            fail("List of ProcessedInformation don't have same size");
        }
        else {
            Iterator<ProcessedInformation> resultIterator = resultFakeProcessedInformation.iterator();
            Iterator<ProcessedInformation> compareIterator = fakeProcessedInformation.iterator();
            while(resultIterator.hasNext() && compareIterator.hasNext()) {
                ProcessedInformation resultEdge = resultIterator.next();
                ProcessedInformation compareEdge = compareIterator.next();
                if (!resultEdge.getDetailedInstruction().equals(compareEdge.getDetailedInstruction())) {
                    fail("Detailed processed information is different from expected");
                }
                if (!resultEdge.getProcessedBasicInstruction().equals(compareEdge.getProcessedBasicInstruction())) {
                    fail("Basic processed information is different from expected");
                }
                if (!resultEdge.getPhotoInstruction().equals(compareEdge.getPhotoInstruction())) {
                    fail("Photo processed information is different from expected");
                }
            }
        }
    }

    @Test
    public void testToNextRegion() throws NavigationExceptions {
        navigatorImp.setGraph(mockMapGraph);
        navigatorImp.calculatePath(mockStartRoi, mockEndPoi);
        ProcessedInformation resultProcessedInfo = navigatorImp.toNextRegion(mockVisibleBeacons);
        if (!resultProcessedInfo.getDetailedInstruction().equals(FAKE_DETAILED_INFO)) {
            fail("Result processed info not equal as expected");
        }
    }
}