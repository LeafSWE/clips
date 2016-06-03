package com.leaf.clips.model;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.navigator.NavigationExceptions;
import com.leaf.clips.model.navigator.Navigator;
import com.leaf.clips.model.navigator.NoNavigationInformationException;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.AbsEnrichedEdge;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformationImp;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;
import com.leaf.clips.model.navigator.graph.vertex.VertexImp;
import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.model.usersetting.SettingImp;

import junit.framework.Assert;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * TU42 & TU44
 */
// TODO: 05/05/2016 associare ad un test? Unit o Integration
// TODO: 5/6/16 Controllare perché è stato dato un conflitto. Ho preso la version f/model 
@RunWith(AndroidJUnit4.class)
@SmallTest
public class NavigationManagerImpTest {

    private NavigationManagerImpExtended navmanager;
    private Context context;
    private MapGraph graph = Mockito.mock(MapGraph.class,Mockito.RETURNS_DEEP_STUBS);
    private Listener listener = Mockito.mock(Listener.class);
    private Navigator navigator = Mockito.mock(Navigator.class);

    private MyBeacon mockBeacon = Mockito.mock(MyBeacon.class);
    private RegionOfInterest mockRegion = Mockito.mock(RegionOfInterest.class);
    private PriorityQueue<MyBeacon> mockBeaconList = Mockito.mock(PriorityQueue.class);
    private ProcessedInformation mockProcessedInfo = Mockito.mock(ProcessedInformation.class);
    private PointOfInterest mockPOI = Mockito.mock(PointOfInterest.class);
    private NavigationListener mockNv = Mockito.mock(NavigationListener.class);

    @Before
    public void setUp() throws Exception{
        context = InstrumentationRegistry.getTargetContext();
        navmanager = new NavigationManagerImpExtended(graph, context);
        Field field1 = navmanager.getClass().getSuperclass().getDeclaredField("navigator");
        field1.setAccessible(true);
        field1.set(navmanager, navigator);
        Field field = navmanager.getClass().getSuperclass().getSuperclass().getDeclaredField("lastBeaconsSeen");
        field.setAccessible(true);
        field.set(navmanager, mockBeaconList);
    }

    @Test
    public void TestAddListener() {
        navmanager.addListener(listener);
        Collection<Listener> collection = navmanager.getAllListener();
        Assert.assertTrue(collection.contains(listener));
    }

    @Test
    public void TestRemoveListener() {
        Collection<Listener> collection = navmanager.getAllRawListener();
        collection.add(listener);
        Assert.assertTrue(collection.contains(listener));
        navmanager.removeListener(listener);
        Assert.assertTrue(!collection.contains(listener));
    }

    @Test
    public void TestStartNavigation() throws Exception {

        Collection<RegionOfInterest> regionOfInterests = new LinkedList<>();

        regionOfInterests.add(mockRegion);
        //Risposta lastBeaconSeen
        Mockito.when(mockBeaconList.peek()).thenReturn(mockBeacon);
        Mockito.when(graph.getGraph().vertexSet().iterator()).thenReturn(regionOfInterests.iterator());
        Mockito.when(mockRegion.contains(mockBeacon)).thenReturn(true);
        Mockito.doNothing().when(navigator).calculatePath(mockRegion, mockPOI);
        Mockito.when(navigator.toNextRegion(mockBeaconList)).thenReturn(mockProcessedInfo);
        Mockito.doNothing().when(mockNv).informationUpdate(mockProcessedInfo);

        ProcessedInformation processedInformationReceived = navmanager.startNavigation(mockPOI);
        Assert.assertEquals(mockProcessedInfo, processedInformationReceived);

    }

    @Test
    public void TestGetAllNavigationInstruction() throws Exception {
        List<ProcessedInformation> mockList = new LinkedList<>();
        mockList.add(mockProcessedInfo);
        ProcessedInformation mockProcessedInfo2 = Mockito.mock(ProcessedInformation.class);
        mockList.add(mockProcessedInfo2);
        Mockito.when(navigator.getAllInstructions()).thenReturn(mockList);
        List<ProcessedInformation> listReceived = navmanager.getAllNavigationInstruction();
        Assert.assertEquals(mockList.size(),listReceived.size());
        Assert.assertEquals(mockList, listReceived);
    }

    @Test
    public void TestGetNextInstruction() throws Exception {
        Mockito.when(navigator.toNextRegion(mockBeaconList)).thenReturn(mockProcessedInfo);
        ProcessedInformation processedInformationReceived = navmanager.getNextInstruction();
        Assert.assertEquals(mockProcessedInfo, processedInformationReceived);
    }

    @Test
    public void TestStopNavigation() throws Exception {
        navmanager.addListener(listener);
        Assert.assertTrue(navmanager.getAllListener().size()>0);
        navmanager.stopNavigation();
        Assert.assertEquals(navmanager.getAllListener().size(),0);
    }


    class NavigationManagerImpExtended extends NavigationManagerImp{

        public NavigationManagerImpExtended(MapGraph graph, Context context) {
            super(graph, context);
        }

        public Collection<Listener> getAllListener(){
            Collection<Listener> collection = new LinkedList<>();
            for(Listener listener : listeners){
                collection.add(listener);
            }
            return collection;
        }

        public Collection<Listener> getAllRawListener(){
            return listeners;
        }
    }

}