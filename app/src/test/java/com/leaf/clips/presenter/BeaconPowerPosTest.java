package com.leaf.clips.presenter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */
public class BeaconPowerPosTest {
    private BeaconPowerPos beaconPos;

    @Before
    public void setUp(){
        beaconPos = new BeaconPowerPos(new float[]{0.15f,0.14f,0.0f},1001);
        assertNotNull(beaconPos);
    }

    @Test
    public void testGetRssi() throws Exception {
        int rssi = beaconPos.getRssi();
        assertEquals(rssi,0);

    }

    @Test
    public void testSetPosition() throws Exception {
        int rssi = -56;
        beaconPos.setRssi(rssi);
        assertEquals(rssi,beaconPos.getRssi());
    }
}