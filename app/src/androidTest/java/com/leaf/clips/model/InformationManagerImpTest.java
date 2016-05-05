package com.leaf.clips.model;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.navigator.BuildingMap;


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
    void init(){
        informationManager = new InformationManagerImp(null, InstrumentationRegistry.getTargetContext());
        //no mock is needed
    }

    @Test(expected = NoBeaconSeenException.class)
    void shouldNotReturnMap() throws NoBeaconSeenException {
        informationManager.getBuildingMap();
    }

    @Test(expected = NoBeaconSeenException.class)
    void shouldNotReturnNearbyPOIs() throws NoBeaconSeenException {
        informationManager.getNearbyPOIs();
    }
}
