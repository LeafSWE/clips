package com.leaf.clips.model.navigator.graph.edge;

import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.beacon.MyBeaconImp;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterestImp;
import com.leaf.clips.model.navigator.graph.navigationinformation.BasicInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.DetailedInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformationImp;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;

import org.altbeacon.beacon.AltBeacon;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */

public class StairEdgeTest {
    private StairEdge stairEdge;

    int startId = 46;
    String startUuid = "DF7E1C79-43E9-44FF-886F-1D1F7DA6997A".toLowerCase();
    int startMajor = 2;
    int startMinor = 4;

    int endId = 99;
    String endUuid = "DF7E1C79-43E9-44FF-887F-1D1F7DA6997A".toLowerCase();
    int endMajor = 6;
    int endMinor = 10;

    double distance = 20;
    int edgeID = 1;
    int coordinate = 12;
    NavigationInformation navInfo;

    MyBeacon myStartBeacon;
    MyBeacon myEndBeacon;

    RegionOfInterest startROI;
    RegionOfInterest endROI;

    @Before
    public void init() {

        //Construct the Beacons for tests
        List<Long> data = new LinkedList<>();
        data.add((long) 88);
        myStartBeacon = new MyBeaconImp(new AltBeacon.Builder().setId1(startUuid)
                .setId2(((Integer) startMajor).toString()).setId3(((Integer) startMinor).toString()).setRssi(-55).setTxPower(-55).setBluetoothAddress("prova").setDataFields(data).setBeaconTypeCode(1).build());
        myEndBeacon = new MyBeaconImp(new AltBeacon.Builder().setId1(endUuid)
                .setId2(((Integer) endMajor).toString()).setId3(((Integer) endMinor).toString()).setRssi(-55).setTxPower(-55).setBluetoothAddress("prova").setDataFields(data).setBeaconTypeCode(1).build());

        this.startROI = new RegionOfInterestImp(this.startId, this.startUuid, this.startMajor, this.startMinor);
        this.endROI = new RegionOfInterestImp(this.endId, this.endUuid, this.endMajor, this.endMinor);

        //Create a basic info
        BasicInformation basicInformation = new BasicInformation("Basic Instruction");

        //Create a detailed info
        DetailedInformation detailedInformation = new DetailedInformation("Detailed Instruction");

        //Create a Collection of PhotoRef
        Collection<PhotoRef> photoRefs = new ArrayList<>();
        try {
            photoRefs.add(new PhotoRef(1,new URI("www.google.com")));
            photoRefs.add(new PhotoRef(2,new URI("www.apple.com")));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //Create a PhotoInformation
        PhotoInformation photoInformation = new PhotoInformation(photoRefs);

        this.navInfo = new NavigationInformationImp(basicInformation, detailedInformation, photoInformation);

        stairEdge = new StairEdge(startROI, endROI, distance, coordinate, edgeID, navInfo);
    }

    /*
     * TU18
     */
    @Test
    public void testGetBasicInformation() throws Exception {
        assertEquals("Basic Instruction", stairEdge.getBasicInformation());
    }

    /*
     * TU18
     */
    @Test
    public void testGetDetailedInformation() throws Exception {
        assertEquals("Detailed Instruction", stairEdge.getDetailedInformation());
    }

    /*
     * TU20
     */
    @Test
    public void testGetWeight() throws Exception {
        //TODO da implementare quando sarà deciso il peso
    }
}