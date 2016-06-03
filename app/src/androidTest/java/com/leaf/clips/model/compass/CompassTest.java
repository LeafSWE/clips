package com.leaf.clips.model.compass;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Eduard on 28/04/2016.
 */

/**
 * TU101 & TU102 & TU103
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
        Thread.sleep(1000);
        float grade = compass.getLastCoordinate();
        assertNotEquals(0, grade, 0.001);
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

    @Test
    public void testGetLastCoordinate() throws Exception {
        Thread.sleep(1000);
        float oldGrade = compass.getLastCoordinate();
        Thread.sleep(1000);
        float newGrade = compass.getLastCoordinate();
        Log.d("GRADE", "oldGrade=" + String.valueOf(oldGrade) + " newGrade=" + String.valueOf(newGrade));
        assertNotEquals(oldGrade, newGrade, 0.00001);
    }
}