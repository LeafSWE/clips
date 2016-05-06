package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 */
import com.leaf.clips.BuildConfig;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;
/**
 * TU5 TU6
 */
@RunWith(MockitoJUnitRunner.class)
public class BuildingMapImpTest {

    private static final int FAKE_ID = 1;
    private static final int FAKE_VERSION = 1;
    private static final String FAKE_SIZE = "30MB";
    private static final String FAKE_POI_CATEGORY = "FakePoiCategory";
    private static final String FAKE_POI_NAME = "FakePoiName";

    private Collection<EnrichedEdge> fakeEnrichedEdgeCollection = new ArrayList<EnrichedEdge>();
    private Collection<PointOfInterest> fakePoiCollection = new ArrayList<PointOfInterest>();
    private Collection<RegionOfInterest> fakeRoiCollection = new ArrayList<RegionOfInterest>();

    private Collection<PointOfInterest> fakeAllNearbyPOIs = new ArrayList<PointOfInterest>();
    private Collection<PointOfInterest> fakeDifferentAllNearbyPOIs =
                                                            new ArrayList<PointOfInterest>();

    @Mock
    private EnrichedEdge mockEdge;

    @Mock
    private RegionOfInterest mockROI;

    @Mock
    private PointOfInterest mockPOI;

    @Mock
    private PointOfInterest mockPOI2;

    @Mock
    private PointOfInterest mockNearPOI;

    @Mock
    private BuildingInformation mockBuildingInformation;

    private BuildingMapImp buildingMapImp;

    @Mock
    private MyBeacon mockBeaconTrue;

    @Mock
    private MyBeacon mockBeaconFalse;

    @Before
    public void setUp() throws Exception {
        fakeEnrichedEdgeCollection.add(mockEdge);
        fakePoiCollection.add(mockPOI);
        fakePoiCollection.add(mockPOI2);
        fakeRoiCollection.add(mockROI);
        fakeAllNearbyPOIs.add(mockNearPOI);
        fakeDifferentAllNearbyPOIs.add(mockPOI);

        when(mockROI.getAllNearbyPOIs()).thenReturn(fakeAllNearbyPOIs);
        when(mockROI.contains(mockBeaconTrue)).thenReturn(true);
        when(mockROI.contains(mockBeaconFalse)).thenReturn(false);

        when(mockPOI.getCategory()).thenReturn(FAKE_POI_CATEGORY);
        when(mockPOI2.getCategory()).thenReturn(FAKE_POI_CATEGORY);

        when(mockPOI.getName()).thenReturn(FAKE_POI_NAME);
        when(mockPOI2.getName()).thenReturn(FAKE_POI_NAME);

        buildingMapImp = new BuildingMapImp(fakeEnrichedEdgeCollection, FAKE_ID, FAKE_VERSION,
                                            fakePoiCollection, fakeRoiCollection,
                                            mockBuildingInformation, FAKE_SIZE);
    }

    @Test
    public void testGetNearbyPOIs() throws Exception {
        Collection<PointOfInterest> result = buildingMapImp.getNearbyPOIs(mockBeaconTrue);

        if (BuildConfig.DEBUG && !(result.containsAll(fakeAllNearbyPOIs))) {
            fail("Returned collection should be equal to expected collection");
        }
        if (BuildConfig.DEBUG && result.containsAll(fakeDifferentAllNearbyPOIs)) {
            fail("Returned collection shouldn't be equal to expected collection");
        }

        Collection<PointOfInterest> nullResult = buildingMapImp.getNearbyPOIs(mockBeaconFalse);
        assertEquals("Result not NULL", null, nullResult);
    }

    @Test
    public void testGetAllPOIsCategories() throws Exception {
        Collection<String> result = buildingMapImp.getAllPOIsCategories();
        for (String categoryString : result) {
            assertEquals("Category not equal", FAKE_POI_CATEGORY, categoryString);
        }
    }

    @Test
    public void testSearchPOIByName() throws Exception {
        Collection<PointOfInterest> result = buildingMapImp.searchPOIByName("FakePoiName");
        assertEquals("Collection result size shouldn't be 2", 2, result.size());
    }
}