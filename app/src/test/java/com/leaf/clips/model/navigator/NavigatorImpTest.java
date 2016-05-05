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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

/**
 * TU33, TU34, TU35
 * TODO: TU35 e parte di TU34 non è coperta da test
 */
@RunWith(MockitoJUnitRunner.class)
public class NavigatorImpTest {

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

    @Mock
    private RegionOfInterest mockRoiInPoi;

    @Before
    public void setUp() throws Exception {
        navigatorImp = new NavigatorImp(mockCompass, mockDijkstraPathFinder);

        fakeShortestPath = new ArrayList<>();
        fakeShortestPath.add(mockEnrichedEdge);
        fakeShortestPath.add(mockEnrichedEdge2);

        fakeBelongingRoi = new ArrayList<RegionOfInterest>();
        fakeBelongingRoi.add(mockRoiInPoi);

        when(mockCompass.getLastCoordinate()).thenReturn(new Float(180));
        when(mockDijkstraPathFinder.calculatePath(mockMapGraph, mockStartRoi, mockRoiInPoi))
                .thenReturn(fakeShortestPath);
        when(mockEndPoi.getAllBelongingROIs()).thenReturn(fakeBelongingRoi);

    }

    @Test(expected = NoGraphSetException.class)
    public void testCalculatePathException() throws Exception {
        navigatorImp.calculatePath(mockStartRoi, mockNullEndPoi);
        fail("Should throw NoGraphException");
    }

    /**
     * Non esegue test in NavigatorImp.isShortestPath()
     * @throws Exception
     */
    @Test
    public void testCalculatePath() throws Exception {
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
        fail("Should throw NoNavigationInformationException");
        //TODO: come testare path che è istanziato all'interno della classe
    }

    @Test
    public void testToNextRegion() throws Exception {
        //TODO: come lo verifico senza utilizzare dati?
        // CalculatePath di JGraphT ha bisogno di dati
        /*navigatorImp.setGraph(mockMapGraph);
        navigatorImp.calculatePath(mockStartRoi, mockEndRoi);
        navigatorImp.toNextRegion(mockVisibleBeacons);*/
    }
}