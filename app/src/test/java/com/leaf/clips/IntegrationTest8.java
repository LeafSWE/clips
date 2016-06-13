package com.leaf.clips;

import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterestImp;
import com.leaf.clips.model.navigator.graph.edge.DefaultEdge;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
@RunWith(JUnit4.class)
public class IntegrationTest8 {
    @Test
    public void shouldRetrieveInformationFromTheGraph() {
        RegionOfInterest a = new RegionOfInterestImp(1, "uuid", 666, 1);
        RegionOfInterest b = new RegionOfInterestImp(2, "uuid", 666, 2);
        RegionOfInterest c = new RegionOfInterestImp(3, "uuid", 666, 3);
        RegionOfInterest d = new RegionOfInterestImp(4, "uuid", 666, 4);
        RegionOfInterest e = new RegionOfInterestImp(5, "uuid", 666, 5);
        RegionOfInterest f = new RegionOfInterestImp(6, "uuid", 666, 6);
        Collection<RegionOfInterest> regions = new HashSet<>();
        regions.add(a);
        regions.add(b);
        regions.add(c);
        regions.add(d);
        regions.add(e);
        regions.add(f);
        EnrichedEdge ab = new DefaultEdge(a, b, 7, 0, 1, null);
        EnrichedEdge ac = new DefaultEdge(a, c, 1, 0, 2, null);
        EnrichedEdge cb = new DefaultEdge(c, b, 5, 0, 3, null);
        EnrichedEdge bd = new DefaultEdge(b, d, 4, 0, 4, null);
        EnrichedEdge ed = new DefaultEdge(e, d, 5, 0, 5, null);
        EnrichedEdge eb = new DefaultEdge(e, b, 2, 0, 6, null);
        EnrichedEdge bf = new DefaultEdge(b, f, 1, 0, 7, null);
        EnrichedEdge ce = new DefaultEdge(c, e, 2, 0, 8, null);
        EnrichedEdge cf = new DefaultEdge(c, f, 7, 0, 9, null);
        EnrichedEdge fe = new DefaultEdge(f, e, 2, 0, 10, null);
        Collection<EnrichedEdge> edges = new HashSet<>();
        edges.add(ab);
        edges.add(ac);
        edges.add(cb);
        edges.add(bd);
        edges.add(ed);
        edges.add(eb);
        edges.add(bf);
        edges.add(ce);
        edges.add(cf);
        MapGraph graph = new MapGraph();
        graph.addAllRegions(regions);
        graph.addAllEdges(edges);
        graph.addRegionOfInterest(e);
        graph.addRegionOfInterest(f);
        graph.addEdge(fe);
        Assert.assertTrue(graph.getGraph().containsEdge(fe));
        Assert.assertTrue(graph.getGraph().getEdge(a, b).getId()==1);
    }

}
