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
import com.leaf.clips.model.navigator.NoNavigationInformationException;
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

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
// TODO: 05/05/2016 associare ad un test? Unit o Integration
// TODO: 5/6/16 Controllare perché è stato dato un conflitto. Ho preso la version f/model 
@RunWith(AndroidJUnit4.class)
@SmallTest
public class NavigationManagerImpTest {

    NavigationManager navigationManager;

    MapGraph map = new MapGraphMock();

    RegionOfInterest r1 = new RegionOfInterestMock(1);
    RegionOfInterest r2 = new RegionOfInterestMock(2);
    RegionOfInterest r3 = new RegionOfInterestMock(3);

    EnrichedEdge e1 = new EnrichedEdgeMock(r1,r2,1,0,1,new NavigationInformationImp(null, null, null));
    EnrichedEdge e2 = new EnrichedEdgeMock(r2,r3,1,0,1,new NavigationInformationImp(null, null, null));

    PointOfInterest endPOI = new PointOfInterestMock();

    @Before
    public void init(){

        List<RegionOfInterest> roiList = new LinkedList<>();
        roiList.add(r1);
        roiList.add(r2);
        roiList.add(r3);
        map.addAllRegions(roiList);

        List<EnrichedEdge> edgeList = new LinkedList<>();
        edgeList.add(e1);
        edgeList.add(e2);
        map.getGraph().addEdge(r1, r2, e1);
        map.getGraph().addEdge(r2, r3, e2);


        map.setSettingAllEdge(new SettingImp(InstrumentationRegistry.getTargetContext()));
        map.addAllEdges(edgeList);
        map.addAllRegions(roiList);

        List<RegionOfInterest> belong = new LinkedList<>();

        belong.add(r3);

        endPOI.setBelongingROIs(belong);

        navigationManager = new NavigationManagerImp(map, InstrumentationRegistry.getTargetContext());

        try {
            navigationManager.startNavigation(endPOI);
        } catch (NavigationExceptions navigationExceptions) {
            navigationExceptions.printStackTrace();
        }
    }

    @Test(expected = NavigationExceptions.class)
    public void shouldNotReturnNavigationInstructions() throws NavigationExceptions {
        navigationManager.getAllNavigationInstruction();
    }

    @Test(expected = NoNavigationInformationException.class)
    public void shouldNotReturnNextNavigationInstruction() throws NoNavigationInformationException {
        navigationManager.getNextInstruction();
    }



    class MapGraphMock extends MapGraph{
        /**
         * Rappresentazione a grafo dell'edificio
         */
        private SimpleDirectedWeightedGraph<RegionOfInterest, EnrichedEdge> graph;

        /**
         * Costruttore della classe
         */
        public MapGraphMock(){
            graph = new SimpleDirectedWeightedGraph<RegionOfInterest, EnrichedEdge>(EnrichedEdge.class);
        }


        /**
         * Metodo che permette di aggiungere più archi al grafo che rappresenta l'edificio
         * @param edges Archi da aggiungere al grafo che rappresenta l'edificio
         */
        @Override
        public void addAllEdges(Collection<EnrichedEdge> edges) {
            for(EnrichedEdge edge: edges){
                graph.addEdge(edge.getStarterPoint(),edge.getEndPoint(),edge);
            }
        }

        /**
         * Metodo che permette di aggiungere più RegionOfInterest al grafo che rappresenta l'edificio
         * @param regions Collezione di RegionOfInterest da aggiungere al grafo che rappresenta l'edificio
         */
        @Override
        public void addAllRegions(Collection<RegionOfInterest> regions) {
            for(RegionOfInterest regionOfInterest: regions){
                graph.addVertex(regionOfInterest);
            }
        }

        /**
         * Metodo che permette di aggiungere un arco al grafo che rappresenta l'edificio
         * @param edge Arco da aggiungere al grafo che rappresenta l'edificio
         */
        @Override
        public void addEdge(EnrichedEdge edge) {
            graph.addEdge(edge.getStarterPoint(),edge.getEndPoint(),edge);
        }

        /**
         * Metodo che permette di aggiungere una RegionOfInterest al grafo che rappresenta l'edificio
         * @param roi RegionOfInterest da aggiungere al grafo che rappresenta l'edificio
         */
        @Override
        public void addRegionOfInterest(RegionOfInterest roi) {
            graph.addVertex(roi);
        }

        /**
         * Metodo che permette di restituire il grafo che rappresenta la distribuzione
         * degli oggetti RegionOfInterest ed EnrichedEdge
         * @return SimpleDirectedWeightedGraph<RegionOfInterest,EnrichedEdge>
         */
        @Override
        public SimpleDirectedWeightedGraph<RegionOfInterest, EnrichedEdge> getGraph() {
            return graph;
        }

        /**
         * Metodo che permette di impostare le setting passate come parametro a tutti gli edge all'interno del graph
         * @param setting Impostazioni di preferenza dell'applicazione
         */
        @Override
        public void setSettingAllEdge(Setting setting) {
            Collection<EnrichedEdge> allEdges = graph.edgeSet();
            for (EnrichedEdge edge : allEdges) {
                edge.setUserPreference(setting);
            }
        }

    }

    private class RegionOfInterestMock extends VertexImp implements RegionOfInterest{

        public RegionOfInterestMock(int id){
            super(id);
        }

        @Override
        public boolean contains(MyBeacon beacon) {
            return false;
        }

        @Override
        public Collection<PointOfInterest> getAllNearbyPOIs() {
            return null;
        }

        @Override
        public int getFloor() {
            return 0;
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public int getMajor() {
            return 0;
        }

        @Override
        public int getMinor() {
            return 0;
        }

        @Override
        public String getUUID() {
            return null;
        }

        @Override
        public void setNearbyPOIs(Collection<PointOfInterest> pois) {

        }
    }

    private class PointOfInterestMock implements PointOfInterest{

        Collection<RegionOfInterest> rois = new LinkedList<>();

        @Override
        public Collection<RegionOfInterest> getAllBelongingROIs() {
            return rois;
        }

        @Override
        public String getCategory() {
            return null;
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public void setBelongingROIs(Collection<RegionOfInterest> rois) {
            rois.addAll(rois);
        }
    }

    private class EnrichedEdgeMock extends AbsEnrichedEdge{

        public EnrichedEdgeMock(RegionOfInterest start, RegionOfInterest end, double distance,
                                int coordinate,int id, NavigationInformation navigationInformation){
            super(start, end, distance, coordinate, id, navigationInformation);
        }

        @Override
        public String getBasicInformation() {
            return "";
        }

        @Override
        public String getDetailedInformation() {
            return "";
        }

        @Override
        public double getWeight() {
            return 1;
        }
    }

    private class NavigationInformationMock implements NavigationInformation{

        public NavigationInformationMock(){

        }

        @Override
        public String getBasicInformation() {
            return "";
        }

        @Override
        public String getDetailedInformation() {
            return "";
        }

        @Override
        public PhotoInformation getPhotoInformation() {
            List list = new LinkedList<>();
            list.add(new PhotoRefMock(1,URI.create("http://www.google.com")));
            return new PhotoInformationMock(list);
        }
    }

    private class PhotoInformationMock extends PhotoInformation {
        /**
         * Costruttore della classe PhotoInformation
         *
         * @param photoURLs A list of all the photosUrls of one Navigation Instruction
         */
        public PhotoInformationMock(Collection<PhotoRef> photoURLs) {
            super(photoURLs);
        }
    }

    private class PhotoRefMock extends PhotoRef{

        /**
         * Costruttore della classe PhotoRef
         *
         * @param id     Identificativo della fotografia
         * @param source URL di una fotografia
         */
        public PhotoRefMock(int id, URI source) {
            super(id, source);
        }
    }

    private NavigationManagerImpExtended navmanager;

    private Context context;

    private MapGraph graph;

    private InformationListener listener;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        graph = new MockMapGraph();
        navmanager = new NavigationManagerImpExtended(graph, context);
        listener = new MockInformationListener();
    }

    @Test
    public void TestAddListener() {
        setUp();
        navmanager.addListener(listener);
        Collection<InformationListener> collection = navmanager.getAllListener();
        Assert.assertTrue(collection.contains(listener));
    }

    @Test
    public void TestRemoveListener() {
        setUp();
        Collection<Listener> collection = navmanager.getAllRawListener();
        collection.add(listener);
        Assert.assertTrue(collection.contains(listener));
        navmanager.removeListener(listener);
        Assert.assertTrue(!collection.contains(listener));
    }

    class NavigationManagerImpExtended extends NavigationManagerImp{

        public NavigationManagerImpExtended(MapGraph graph, Context context) {
            super(graph, context);
        }

        public Collection<InformationListener> getAllListener(){
            Collection<InformationListener> collection = new LinkedList<>();
            for(Listener listener : listeners){
                collection.add((InformationListener)listener);
            }
            return collection;
        }

        public Collection<Listener> getAllRawListener(){
            return listeners;
        }
    }

    class MockMapGraph extends MapGraph {

    }

    class MockInformationListener implements InformationListener {

        @Override
        public void onDatabaseLoaded() {

        }

        @Override
        public boolean onLocalMapNotFound() {
            return false;
        }

        @Override
        public void onRemoteMapNotFound() {

        }

        @Override
        public void cannotRetrieveRemoteMapDetails() {

        }

        @Override
        public boolean noLastMapVersion() {
            return false;
        }
    }

}