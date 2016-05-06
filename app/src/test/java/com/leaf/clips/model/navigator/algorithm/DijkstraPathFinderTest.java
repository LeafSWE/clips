package com.leaf.clips.model.navigator.algorithm;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 */

import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;

import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * TU32 TODO: test dipendente da un metodo statico di JGraphT, come fare il mock di tale metodo ???
 */
@RunWith(MockitoJUnitRunner.class)
public class DijkstraPathFinderTest {

    @Mock
    private List<EnrichedEdge> mockPath;

    @Mock
    private MapGraph graph;

    @Mock
    private RegionOfInterest startROI;

    @Mock
    private RegionOfInterest endROI;

    @Before
    public void setUp() throws Exception {
        //when(DijkstraShortestPath.findPathBetween(graph.getGraph(), startROI, endROI)).thenReturn(mockPath);
    }

    @Test
    public void testCalculatePath() throws Exception {
        /*DijkstraPathFinder dijkstraPathFinder = new DijkstraPathFinder();
        List<EnrichedEdge> path = dijkstraPathFinder.calculatePath(graph, startROI, endROI);
        assertEquals("Must be same path", path, mockPath);*/
    }
}