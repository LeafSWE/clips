package com.leaf.clips.model.navigator.graph;

import android.graphics.Region;

import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterestImp;
import com.leaf.clips.model.navigator.graph.edge.DefaultEdge;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;

import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.builder.DirectedWeightedGraphBuilder;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */

public class MapGraphTest {

    ArrayList<EnrichedEdge> edges = new ArrayList<>();
    ArrayList<RegionOfInterest> regions = new ArrayList<>();
    MapGraph mapGraph = new MapGraph();
    @Before
    public void init(){
        for(int i=0; i<4; i++){
            regions.add( new RegionOfInterestImp(i, "uuid", 0, 0));
        }
        for(int i=0; i<3; i++) {
            edges.add(new DefaultEdge(regions.get(i), regions.get(i + 1), 0, 0, i, null));
        }
    }


    /*
     * TU30
     */
    @Test
    public void testAddAllEdges() throws Exception {
        mapGraph.addAllRegions(regions);
        mapGraph.addAllEdges(edges);
        for(EnrichedEdge edge: edges){
            assertTrue(mapGraph.getGraph().containsEdge(edge));
        }
    }

    /*
     * TU30
     */
    @Test
    public void testAddAllRegions() throws Exception {
        mapGraph.addAllRegions(regions);
        for(RegionOfInterest regionOfInterest: regions){
            assertTrue(mapGraph.getGraph().containsVertex(regionOfInterest));
        }
    }

    /*
     * TU30
     */
    @Test
    public void testAddEdge() throws Exception {
        mapGraph.addAllRegions(regions);
        mapGraph.addEdge(edges.get(0));
        assertTrue(mapGraph.getGraph().containsEdge(edges.get(0)));
    }

    @Test
    public void testAddRegionOfInterest() throws Exception {
        mapGraph.addRegionOfInterest(regions.get(0));
        assertTrue(mapGraph.getGraph().containsVertex(regions.get(0)));
    }

    /*
     * TU31
     */
    @Test
    public void testGetGraph() throws Exception {

        SimpleDirectedWeightedGraph<RegionOfInterest,EnrichedEdge> graph = new SimpleDirectedWeightedGraph<>(EnrichedEdge.class);
        for(RegionOfInterest regionOfInterest: regions){
            graph.addVertex(regionOfInterest);
        }
        for(EnrichedEdge edge: edges){
            graph.addEdge(edge.getStarterPoint(), edge.getEndPoint(), edge);
        }

        mapGraph.addAllRegions(regions);
        mapGraph.addAllEdges(edges);
        assertEquals(graph,mapGraph.getGraph());

    }
}