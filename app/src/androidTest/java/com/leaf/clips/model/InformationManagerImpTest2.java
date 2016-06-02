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
import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import static junit.framework.Assert.*;

/**
 * TU41
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class InformationManagerImpTest2 {
    private InformationManager informationManager;
    private InformationManager infoManager;
    private DatabaseService mockDatabaseService = Mockito.mock(DatabaseService.class);
    private BuildingMap mockBuildingMap = Mockito.mock(BuildingMap.class);
    private Collection<PointOfInterest> mockPOIList = new LinkedList<>();
    private MyBeacon mockBeacon = Mockito.mock(MyBeacon.class);
    private PriorityQueue<MyBeacon> mockBeaconList = Mockito.mock(PriorityQueue.class);

    @Before
    public void init() throws Exception{
        informationManager = new InformationManagerImp(mockDatabaseService, InstrumentationRegistry.getTargetContext());
        infoManager = new InformationManagerImp(mockDatabaseService, InstrumentationRegistry.getTargetContext());
        Field field = informationManager.getClass().getDeclaredField("map");
        field.setAccessible(true);
        field.set(informationManager, mockBuildingMap);
        Field field1 = informationManager.getClass().getSuperclass().getDeclaredField("lastBeaconsSeen");
        field1.setAccessible(true);
        field1.set(informationManager, mockBeaconList);
        Mockito.when(mockBeaconList.peek()).thenReturn(mockBeacon);
        PointOfInterest poi1 = Mockito.mock(PointOfInterest.class);
        PointOfInterest poi2 = Mockito.mock(PointOfInterest.class);
        PointOfInterest poi3 = Mockito.mock(PointOfInterest.class);
        mockPOIList.add(poi1);
        mockPOIList.add(poi2);
        mockPOIList.add(poi3);
        Mockito.when(mockBuildingMap.getNearbyPOIs(mockBeacon)).thenReturn(mockPOIList);
        Mockito.when(mockBeaconList.isEmpty()).thenReturn(false);
    }

    @Test(expected = NoBeaconSeenException.class)
    public void shouldNotReturnMap() throws NoBeaconSeenException {
        infoManager.getBuildingMap();
    }

    @Test(expected = NoBeaconSeenException.class)
    public void shouldNotReturnNearbyPOIs() throws Exception {
        Field field1 = infoManager.getClass().getSuperclass().getDeclaredField("lastBeaconsSeen");
        field1.setAccessible(true);
        field1.set(infoManager, mockBeaconList);
        Mockito.when(mockBeaconList.isEmpty()).thenReturn(true);
        infoManager.getNearbyPOIs();
    }

    @Test
    public void testGetBuildingMap() throws Exception {
        BuildingMap buildingMap =informationManager.getBuildingMap();
        assertEquals(mockBuildingMap, buildingMap);
    }

    @Test
    public void testGetDatabaseService() throws Exception {
        DatabaseService databaseService =informationManager.getDatabaseService();
        assertEquals(mockDatabaseService, databaseService);
    }

    @Test
    public void testGetNearbyPOIs() throws Exception {
        Collection<PointOfInterest> poisReceived = informationManager.getNearbyPOIs();
        assertEquals(mockPOIList.size(),poisReceived.size());
        assertEquals(mockPOIList,poisReceived);
    }

}
