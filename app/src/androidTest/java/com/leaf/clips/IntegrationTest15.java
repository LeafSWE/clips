package com.leaf.clips;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.dataaccess.dao.DaoFactoryHelper;
import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.dataaccess.service.ServiceHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class IntegrationTest15 extends InstrumentationTestCase{


    DatabaseService dbService;
    int major = 666;


    @Before
    public void init(){
        dbService = ServiceHelper.getService(DaoFactoryHelper.getInstance()
                .getSQLiteDaoFactory(new MapsDbHelper(InstrumentationRegistry.getTargetContext())
                        .getWritableDatabase()), DaoFactoryHelper.getInstance()
                                    .getRemoteDaoFactory(), MapsDbHelper.REMOTE_DB_URL);

    }

    @Test void shouldNotFindLocalMap(){
        assertEquals(false, dbService.isBuildingMapPresent(major));
    }

    @Test
    public void shouldFoundRemoteMap(){
        try {
            assertEquals(true, dbService.isRemoteMapPresent(major));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldRetrieveAndInsertMap(){

        try {
            dbService.findRemoteBuildingByMajor(major);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(true, dbService.isBuildingMapPresent(major));

    }

    @Test
    public void shouldRetrieveInsertAndDeleteMap(){

        try {
            dbService.findRemoteBuildingByMajor(major);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(true, dbService.isBuildingMapPresent(major));
        dbService.deleteBuilding(dbService.findBuildingByMajor(major));

        assertEquals(false,dbService.isBuildingMapPresent(major));

    }



}
