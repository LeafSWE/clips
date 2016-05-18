package com.leaf.clips.model.beacon;
/**
 * @author Federico Tavella
 * @version 1.00
 * @since 0.00
 */

import android.test.suitebuilder.annotation.SmallTest;

import org.altbeacon.beacon.AltBeacon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Unit Test 37 & 38
 */
@RunWith(JUnit4.class)
@SmallTest
public class LoggerImpTest {

    private Logger log;

    @Before
    public void init() {
        log = new LoggerImp();
    }

    /**
     * Unit Test 37
     */
    @Test
    public void shouldAddTextToLog(){
        MyBeacon beacon;
        List<Long> data = new LinkedList<>();
        data.add((long) 88);
        PriorityQueue<MyBeacon> beacons = new PriorityQueue<>();
        for(int i=0; i<10; i++){
            beacon = new MyBeaconImp(new AltBeacon.Builder()
                    .setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997A").setId2("1").setId3("1")
                    .setRssi(-55).setTxPower(-55).setBluetoothAddress("prova").setDataFields(data)
                    .setBeaconTypeCode(1).build());
            beacons.add(beacon);
        }

        log.add(beacons);

        String logInfo = log.getData().toString();
        for(MyBeacon b : beacons){
            Assert.assertTrue(logInfo.contains(b.toString()));
        }
    }

    /**
     * Unit Test 38
     */

    @Test
    public void shouldSaveLogAndContainSavedInformation(){
       /* MyBeacon beacon;
        List<Long> data = new LinkedList<>();
        data.add((long) 88);
        PriorityQueue<MyBeacon> beacons = new PriorityQueue<>();
        for(int i=0; i<2; i++){
            beacon = new MyBeaconImp(new AltBeacon.Builder()
                    .setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997A").setId2("1").setId3("1")
                    .setRssi(-55).setTxPower(-55).setBluetoothAddress("prova").setDataFields(data)
                    .setBeaconTypeCode(1).build());
            beacons.add(beacon);
        }

        log.add(beacons);
        String logInfo = log.getData().toString();
        String name = "beacon";
        log.save(name);
        String savedLog = log.open(name).trim();
        Assert.assertEquals("Log saved correctly",logInfo,savedLog);*/
    }
}
