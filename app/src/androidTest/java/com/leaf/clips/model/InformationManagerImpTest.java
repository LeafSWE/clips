package com.leaf.clips.model;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        // TODO: 04/05/2016 fare un mock di un database service
    }

    @Test(expected = NoBeaconSeenException.class)
    public void shouldNotReturnMap() throws NoBeaconSeenException {
        informationManager.getBuildingMap();
    }

    @Test(expected = NoBeaconSeenException.class)
    public void shouldNotReturnNearbyPOIs() throws NoBeaconSeenException {
        informationManager.getNearbyPOIs();
    }

}
