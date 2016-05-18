package com.leaf.clips.model.navigator.graph.area;

import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.beacon.MyBeaconImp;

import junit.framework.Assert;

import org.altbeacon.beacon.AltBeacon;
import org.altbeacon.beacon.Beacon;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

public class RegionOfInterestImpTest {

    int id = 46;
    String uuid = "DF7E1C79-43E9-44FF-886F-1D1F7DA6997A".toLowerCase();
    int major = 1;
    int minor = 1;

    MyBeacon myBeaconContained;
    MyBeacon myBeaconNotContained;

    RegionOfInterestImp regionOfInterestImp;

    @Before
    public void init() {

        //Construct the Beacons for tests
        List<Long> data = new LinkedList<>();
        data.add((long) 88);
        myBeaconContained= new MyBeaconImp(new AltBeacon.Builder().setId1(uuid)
                .setId2(((Integer)major).toString()).setId3(((Integer)major).toString()).setRssi(-55).setTxPower(-55).setBluetoothAddress("prova").setDataFields(data).setBeaconTypeCode(1).build());

        this.myBeaconNotContained = new MyBeaconImp(new AltBeacon.Builder().setId1("DF7F1C79-43E9-44FF-886F-1D1F7DA6997A".toLowerCase())
                .setId2("1").setId3("1").setRssi(-55).setTxPower(-55).setBluetoothAddress("prova").setDataFields(data).setBeaconTypeCode(1).build());;



        this.regionOfInterestImp = new RegionOfInterestImp(this.id, this.uuid, this.major, this.minor);
    }

    /*
     * TU28
     */
    @Test
    public void testContains() throws Exception {
        Assert.assertTrue(regionOfInterestImp.contains(myBeaconContained));
        Assert.assertFalse(regionOfInterestImp.contains(myBeaconNotContained));
    }

    /*
     * TU29
     */
    @Test
    public void testGetAllNearbyPOIs() throws Exception {
        PointOfInterestInformation poiInfo = new PointOfInterestInformation("name", "description", "category");
        PointOfInterest poi = new PointOfInterestImp(99, poiInfo);

        Collection<PointOfInterest> collection = new ArrayList<>();
        collection.add(poi);
        regionOfInterestImp.setNearbyPOIs(collection);

        Assert.assertEquals(regionOfInterestImp.getAllNearbyPOIs().iterator().next(), poi);
    }

    /*
     * TU27
     */
    @Test
    public void testGetFloor() throws Exception {
        int floor = this.minor / 1000;
        Assert.assertEquals(floor, regionOfInterestImp.getFloor());
    }

    /*
     * TU26
     */
    @Test
    public void testGetMajor() throws Exception {
        Assert.assertEquals(this.major, regionOfInterestImp.getMajor());
    }

    /*
     * TU26
     */
    @Test
    public void testGetMinor() throws Exception {
        Assert.assertEquals(this.minor, regionOfInterestImp.getMinor());
    }

    /*
     * TU26
     */
    @Test
    public void testGetUUID() throws Exception {
        Assert.assertEquals(this.uuid, regionOfInterestImp.getUUID());
    }

    /*
     * TU29
     */
    @Test
    public void testSetNearbyPOIs() throws Exception {
        PointOfInterestInformation poiInfo = new PointOfInterestInformation("name", "description", "category");
        PointOfInterest poi = new PointOfInterestImp(99, poiInfo);

        Collection<PointOfInterest> collection = new ArrayList<>();
        collection.add(poi);
        regionOfInterestImp.setNearbyPOIs(collection);

        Assert.assertEquals(regionOfInterestImp.getAllNearbyPOIs().iterator().next(), poi);
    }
}