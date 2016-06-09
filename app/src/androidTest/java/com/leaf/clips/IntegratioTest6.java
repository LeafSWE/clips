package com.leaf.clips;
/**
 * @author Federico Tavella
 * @version 0.00
 * @since 0.00
 */

import android.os.Parcel;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.beacon.MyBeaconImp;
import com.leaf.clips.model.compass.Compass;
import com.leaf.clips.model.navigator.BuildingInformation;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.BuildingMapImp;
import com.leaf.clips.model.navigator.NavigationExceptions;
import com.leaf.clips.model.navigator.Navigator;
import com.leaf.clips.model.navigator.NavigatorImp;
import com.leaf.clips.model.navigator.PathException;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestInformation;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterestImp;
import com.leaf.clips.model.navigator.graph.edge.DefaultEdge;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.navigator.graph.navigationinformation.BasicInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.DetailedInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformationImp;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;
import com.leaf.clips.model.usersetting.PathPreference;
import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.model.usersetting.SettingImp;

import junit.framework.Assert;

import org.altbeacon.beacon.AltBeacon;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import javax.inject.Inject;

/**
 * Class Description
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntegratioTest6 extends InstrumentationTestCase{
    BuildingMap buildingMap;
    Navigator navigator;
    RegionOfInterest a;
    PointOfInterest u;
    Setting setting;

    @Inject
    Compass compass;

    @Before
    public void init(){

        setting = new SettingImp(InstrumentationRegistry.getTargetContext());
        PointOfInterest z = new PointOfInterestImp(0,new PointOfInterestInformation("z","z","z"));
        PointOfInterest y = new PointOfInterestImp(1,new PointOfInterestInformation("y","y","y"));
        PointOfInterest x = new PointOfInterestImp(2,new PointOfInterestInformation("x","x","x"));
        PointOfInterest w = new PointOfInterestImp(3,new PointOfInterestInformation("w","w","w"));
        PointOfInterest v = new PointOfInterestImp(4,new PointOfInterestInformation("v","v","v"));
        u = new PointOfInterestImp(5,new PointOfInterestInformation("u","u","u"));
        Collection<PointOfInterest> points = new HashSet<>();
        points.add(z);
        points.add(y);
        points.add(x);
        points.add(w);
        points.add(v);
        points.add(u);
        a = new RegionOfInterestImp(1, "uuid", 666, 1);
        RegionOfInterest b = new RegionOfInterestImp(2, "DF7E1C79-43E9-44FF-886F-1D1F7DA6997A", 666, 2);
        RegionOfInterest c = new RegionOfInterestImp(3, "DF7E1C79-43E9-44FF-886F-1D1F7DA6997A", 666, 3);
        RegionOfInterest d = new RegionOfInterestImp(4, "DF7E1C79-43E9-44FF-886F-1D1F7DA6997A", 666, 4);
        RegionOfInterest e = new RegionOfInterestImp(5, "DF7E1C79-43E9-44FF-886F-1D1F7DA6997A", 666, 5);
        RegionOfInterest f = new RegionOfInterestImp(6, "DF7E1C79-43E9-44FF-886F-1D1F7DA6997A", 666, 6);
        LinkedList<PointOfInterest> list1 = new LinkedList<>();
        list1.add(z);
        a.setNearbyPOIs(list1);
        LinkedList<PointOfInterest> list2 = new LinkedList<>();
        list2.add(y);
        b.setNearbyPOIs(list2);
        LinkedList<PointOfInterest> list3 = new LinkedList<>();
        list3.add(x);
        c.setNearbyPOIs(list3);
        LinkedList<PointOfInterest> list4 = new LinkedList<>();
        list4.add(w);
        d.setNearbyPOIs(list4);
        LinkedList<PointOfInterest> list5 = new LinkedList<>();
        list5.add(v);
        e.setNearbyPOIs(list5);
        LinkedList<PointOfInterest> list6 = new LinkedList<>();
        list6.add(u);
        f.setNearbyPOIs(list6);

        LinkedList<RegionOfInterest> list7 = new LinkedList<>();
        list7.add(a);
        z.setBelongingROIs(list7);
        LinkedList<RegionOfInterest> list8 = new LinkedList<>();
        list8.add(b);
        y.setBelongingROIs(list8);
        LinkedList<RegionOfInterest> list9 = new LinkedList<>();
        list9.add(c);
        x.setBelongingROIs(list9);
        LinkedList<RegionOfInterest> list10 = new LinkedList<>();
        list10.add(d);
        w.setBelongingROIs(list10);
        LinkedList<RegionOfInterest> list11 = new LinkedList<>();
        list11.add(e);
        v.setBelongingROIs(list11);
        LinkedList<RegionOfInterest> list12 = new LinkedList<>();
        list12.add(f);
        u.setBelongingROIs(list12);


        Collection<RegionOfInterest> regions = new HashSet<>();
        regions.add(a);
        regions.add(b);
        regions.add(c);
        regions.add(d);
        regions.add(e);
        regions.add(f);
        BasicInformation basicInformation = new BasicInformation("BASIC");
        DetailedInformation detailedInformation = new DetailedInformation("DETAILED");
        Collection<PhotoRef> photoRefs = new LinkedList<>();
        try {
            photoRefs.add(new PhotoRef(0,new URI("http://www.no.com")));
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        PhotoInformation photoInformation = new PhotoInformation(photoRefs);
        EnrichedEdge ab = new DefaultEdge(a, b, 7, 0, 1, new NavigationInformationImp(
                basicInformation,detailedInformation,photoInformation));
        EnrichedEdge ac = new DefaultEdge(a, c, 1, 0, 2, new NavigationInformationImp(
                basicInformation,detailedInformation,photoInformation));
        EnrichedEdge cb = new DefaultEdge(c, b, 5, 0, 3, new NavigationInformationImp(
                basicInformation,detailedInformation,photoInformation));
        EnrichedEdge bd = new DefaultEdge(b, d, 4, 0, 4, new NavigationInformationImp(
                basicInformation,detailedInformation,photoInformation));
        EnrichedEdge ed = new DefaultEdge(e, d, 5, 0, 5, new NavigationInformationImp(
                basicInformation,detailedInformation,photoInformation));
        EnrichedEdge eb = new DefaultEdge(e, b, 2, 0, 6, new NavigationInformationImp(
                basicInformation,detailedInformation,photoInformation));
        EnrichedEdge bf = new DefaultEdge(b, f, 1, 0, 7, new NavigationInformationImp(
                basicInformation,detailedInformation,photoInformation));
        EnrichedEdge ce = new DefaultEdge(c, e, 2, 0, 8, new NavigationInformationImp(
                basicInformation,detailedInformation,photoInformation));
        EnrichedEdge cf = new DefaultEdge(c, f, 7, 0, 9, new NavigationInformationImp(
                basicInformation,detailedInformation,photoInformation));
        EnrichedEdge fe = new DefaultEdge(f, e, 2, 0, 10, new NavigationInformationImp(
                basicInformation,detailedInformation,photoInformation));
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

        buildingMap = new BuildingMapImp(edges,0,1,points,regions,new BuildingInformation("name","descr","H","address"),"20");
        navigator = new NavigatorImp(compass, setting);
        navigator.setGraph(graph);
    }

    @Test
    public void shouldCalculatePath(){
        try {
            navigator.calculatePath(a,u);
        } catch (NavigationExceptions navigationExceptions) {
            navigationExceptions.printStackTrace();
        }
    }

    @Test (expected = PathException.class)
    public void shouldNotAccessInfo() throws NavigationExceptions {
        navigator.calculatePath(a, u);
        PriorityQueue<MyBeacon> beacons = new PriorityQueue<>();
        List<Long> list = new LinkedList<>();
        list.add((long) 88);

        beacons.add(new MyBeaconImp(new AltBeacon.Builder()
                 .setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997A".toLowerCase()).setId2("666").
                 setId3(Integer.toString(112)).setDataFields(list).build()));
        navigator.toNextRegion(beacons);
    }

    @Test
    public void shouldAccessAllInformations() throws NavigationExceptions {
        navigator.calculatePath(a,u);
        List<ProcessedInformation> info = navigator.getAllInstructions();
        for(ProcessedInformation information : info){
            Assert.assertEquals(information.getDetailedInstruction(),"DETAILED");
            Assert.assertEquals(information.getPhotoInstruction().getPhotoInformation().iterator()
                    .next().getPhotoUri().toString(),"http://www.no.com");
        }
    }
}
