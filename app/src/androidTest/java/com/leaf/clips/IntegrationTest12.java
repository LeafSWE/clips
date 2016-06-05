package com.leaf.clips;

import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterestImp;
import com.leaf.clips.model.navigator.graph.edge.DefaultEdge;
import com.leaf.clips.model.navigator.graph.edge.ElevatorEdge;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.navigator.graph.edge.StairEdge;
import com.leaf.clips.model.navigator.graph.navigationinformation.BasicInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.DetailedInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformationImp;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class IntegrationTest12 {
    DefaultEdge defaultEdge;
    ElevatorEdge elevatorEdge;
    EnrichedEdge enrichedEdge;
    StairEdge stairEdge;
    RegionOfInterest startROI;
    RegionOfInterest endROI;
    BasicInformation basicInformation;
    DetailedInformation detailedInformation;
    NavigationInformation navigationInformation;
    PhotoInformation photoInformation;
    PhotoRef photoRef;

    @Before
    public void init(){
        startROI = new RegionOfInterestImp(0,"19235dd2-574a-4702-a42e-caccac06e325",666,1001);
        endROI = new RegionOfInterestImp(1,"19235dd2-574a-4702-a42e-caccac06e325",666,1001);
        basicInformation = new BasicInformation("A");
        detailedInformation = new DetailedInformation("B");
        try {
            photoRef = new PhotoRef(0, new URI("http://www.no.com"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<PhotoRef> list = new ArrayList<>();
        list.add(photoRef);
        photoInformation = new PhotoInformation(list);
        navigationInformation = new NavigationInformationImp(basicInformation,detailedInformation,photoInformation);

    }

    @Test
    public void shouldAccessDefaultEdgeInfo(){
        enrichedEdge = new DefaultEdge(startROI,endROI,1,0,0,navigationInformation);
        Assert.assertEquals(enrichedEdge.getBasicInformation(), "A");
        Assert.assertEquals(enrichedEdge.getCoordinate(), 0);
        Assert.assertEquals(enrichedEdge.getDetailedInformation(), "B");
        Assert.assertEquals(enrichedEdge.getDistance(), 1.0);
        Assert.assertEquals(enrichedEdge.getPhotoInformation(), photoInformation);
        Assert.assertEquals(enrichedEdge.getId(), 0);
        Assert.assertEquals(enrichedEdge.getStarterPoint(), startROI);
        Assert.assertEquals(enrichedEdge.getEndPoint(), endROI);
    }

    @Test
    public void shouldAccessStairEdgeInfo(){
        enrichedEdge = new StairEdge(startROI,endROI,1,0,0,navigationInformation);

        //enrichedEdge = new DefaultEdge(startROI,endROI,1,0,0,navigationInformation);
        Assert.assertEquals(enrichedEdge.getBasicInformation(), "A");
        Assert.assertEquals(enrichedEdge.getCoordinate(), 0);
        Assert.assertEquals(enrichedEdge.getDetailedInformation(), "B");
        Assert.assertEquals(enrichedEdge.getDistance(), 1.0);
        Assert.assertEquals(enrichedEdge.getPhotoInformation(), photoInformation);
        Assert.assertEquals(enrichedEdge.getId(), 0);
        Assert.assertEquals(enrichedEdge.getStarterPoint(), startROI);
        Assert.assertEquals(enrichedEdge.getEndPoint(), endROI);

    }

    @Test
    public void shouldAccessElevatorEdgeInfo(){
        enrichedEdge = new ElevatorEdge(startROI,endROI,1,0,0,navigationInformation);

        //enrichedEdge = new DefaultEdge(startROI,endROI,1,0,0,navigationInformation);
        Assert.assertEquals(enrichedEdge.getBasicInformation(), "A");
        Assert.assertEquals(enrichedEdge.getCoordinate(), 0);
        Assert.assertEquals(enrichedEdge.getDetailedInformation(), "B");
        Assert.assertEquals(enrichedEdge.getDistance(), 1.0);
        Assert.assertEquals(enrichedEdge.getPhotoInformation(), photoInformation);
        Assert.assertEquals(enrichedEdge.getId(), 0);
        Assert.assertEquals(enrichedEdge.getStarterPoint(), startROI);
        Assert.assertEquals(enrichedEdge.getEndPoint(), endROI);
    }
}
