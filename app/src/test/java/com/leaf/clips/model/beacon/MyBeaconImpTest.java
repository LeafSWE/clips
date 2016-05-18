package com.leaf.clips.model.beacon;
/**
 * @author Federico Tavella
 * @version 1.00
 * @since 0.00
 */

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Assert;

import org.altbeacon.beacon.AltBeacon;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.LinkedList;
import java.util.List;

/**
 * Unit Test 36
 */
@RunWith(JUnit4.class)
@SmallTest
public class MyBeaconImpTest {

    private MyBeacon beacon;

    @Before
    public void init() {
        List<Long> data = new LinkedList<>();
        data.add((long) 88);
        beacon = new MyBeaconImp(new AltBeacon.Builder().setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997A")
                .setId2("1").setId3("1").setRssi(-55).setTxPower(-55).setBluetoothAddress("prova").setDataFields(data).setBeaconTypeCode(1).build());//TO-DO
    }

    @Test
    public void shouldAccessDataFields() {

        Assert.assertNotNull(beacon.getBatteryLevel());
        Assert.assertNotNull(beacon.getUUID());
        Assert.assertNotNull(beacon.getMajor());
        Assert.assertNotNull(beacon.getMinor());
        Assert.assertNotNull(beacon.getBeaconTypeCode());
        Assert.assertNotNull(beacon.getDistance());
        Assert.assertNotNull(beacon.getRssi());
        Assert.assertNotNull(beacon.getTxPower());
    }

}
