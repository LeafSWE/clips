package com.leaf.clips.model.compass;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Eduard on 28/04/2016.
 */
@RunWith(AndroidJUnit4.class)
public class CompassTest {

    private Compass compass;
    private SensorManager sensorManager;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getContext();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        compass = new Compass(sensorManager);
        compass.registerListener();
    }

    @Test
    public void testRegisterListener() throws Exception {
        //TODO: test impredicibile
        //Thread.sleep(100);
        //float grade = compass.getLastCoordinate();
        //assertEquals(0, grade, 0.001);
    }

    @Test
    public void testUnregisterListener() throws Exception {
        Thread.sleep(100);
        float oldGrade = compass.getLastCoordinate();
        compass.unregisterListener();
        Thread.sleep(100);
        float newGrade = compass.getLastCoordinate();
        assertEquals(oldGrade, newGrade, 0.001);
    }
}