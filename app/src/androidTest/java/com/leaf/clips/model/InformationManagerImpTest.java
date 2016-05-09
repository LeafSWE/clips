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

import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.navigator.BuildingMap;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Unit test 172
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class InformationManagerImpTest {
    private InformationManager informationManager;

    @Before
    public void init(){
        informationManager = new InformationManagerImp(null, InstrumentationRegistry.getTargetContext());
        //no mock is needed
    }

    @Test(expected = NoBeaconSeenException.class)
    public void shouldNotReturnMap() throws NoBeaconSeenException {
        informationManager.getBuildingMap();
    }

    @Test(expected = NoBeaconSeenException.class)
    public void shouldNotReturnNearbyPOIs() throws NoBeaconSeenException {
        informationManager.getNearbyPOIs();
    }

    private InformationManagerImpExtended infomanager;

    private Context context;

    private DatabaseService databaseService;

    private InformationListener listener;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        databaseService = new MockDatabaseService();
        infomanager = new InformationManagerImpExtended(databaseService, context);
        listener = new MockInformationListener();
    }

    @Test
    public void TestAddListener() {
        setUp();
        infomanager.addListener(listener);
        Collection<InformationListener> collection = infomanager.getAllListener();
        Assert.assertTrue(collection.contains(listener));
    }

    @Test
    public void TestRemoveListener() {
        setUp();
        Collection<Listener> collection = infomanager.getAllRawListener();
        collection.add(listener);
        Assert.assertTrue(collection.contains(listener));
        infomanager.removeListener(listener);
        Assert.assertTrue(!collection.contains(listener));
    }

    class InformationManagerImpExtended extends InformationManagerImp{

        public InformationManagerImpExtended(DatabaseService dbService, Context context) {
            super(dbService, context);
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

    class MockDatabaseService implements DatabaseService {

        @Override
        public void deleteBuilding(BuildingMap buildingMap) {

        }

        @Override
        public Collection<BuildingTable> findAllBuildings() {
            return null;
        }

        @Override
        public Collection<BuildingTable> findAllRemoteBuildings() throws IOException {
            return null;
        }

        @Override
        public BuildingMap findBuildingByMajor(int major) {
            return null;
        }

        @Override
        public BuildingMap findRemoteBuildingByMajor(int major) throws IOException {
            return null;
        }

        @Override
        public boolean isBuildingMapPresent(int major) {
            return false;
        }

        @Override
        public boolean isRemoteMapPresent(int major) throws IOException {
            return false;
        }

        @Override
        public boolean isBuildingMapUpdated(int major) throws IOException {
            return false;
        }

        @Override
        public void updateBuildingMap(int major) throws IOException {

        }
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
