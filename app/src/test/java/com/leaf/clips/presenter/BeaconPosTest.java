package com.leaf.clips.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */
@RunWith(JUnit4.class)
public class BeaconPosTest {
    private BeaconPos beaconPos;

    @Before
    public void setUp(){
        beaconPos = new BeaconPos(new float[]{0.15f,0.14f,0.0f},1001);
        assertNotNull(beaconPos);
    }

    @Test
    public void testGetPosition() throws Exception {
        float[] pos = beaconPos.getPosition();
        assertEquals(pos[0],0.15f,0.01f);
        assertEquals(pos[1],0.14f,0.01f);
        assertEquals(pos[2],0.0f,0.01f);

    }

    @Test
    public void testSetPosition() throws Exception {
        float[] newPosition = new float[]{0.1f,0.2f,0.5f};
        beaconPos.setPosition(newPosition);
        float[] pos = beaconPos.getPosition();
        assertEquals(pos[0],0.1f,0.01f);
        assertEquals(pos[1],0.2f,0.01f);
        assertEquals(pos[2], 0.5f, 0.01f);
    }

    @Test
    public void testGetMinor() throws Exception {
        int minor = beaconPos.getMinor();
        assertEquals(minor,1001);
    }

    @Test
    public void testSetMinor() throws Exception {
        beaconPos.setMinor(1003);
        int minor = beaconPos.getMinor();
        assertEquals(minor,1003);
    }
}