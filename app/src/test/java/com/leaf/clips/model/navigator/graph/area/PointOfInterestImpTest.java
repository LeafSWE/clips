package com.leaf.clips.model.navigator.graph.area;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

public class PointOfInterestImpTest {

    PointOfInterestImp pointOfInterestImp;
    PointOfInterestInformation pointOfInterestInformation;
    int id = 99;

    @Before
    public void init() {
        pointOfInterestInformation = new PointOfInterestInformation("name", "description", "category");
        this.pointOfInterestImp = new PointOfInterestImp(this.id, pointOfInterestInformation);
    }
    /*
     * TU25
     */
    @Test
    public void testGetAllBelongingROIs() throws Exception {
        //Questo test potrebbe non essere efficace

        Collection<RegionOfInterest> belonginROIs = new ArrayList<>();
        RegionOfInterest roi = new RegionOfInterestImp(15, "DF7E1C79-43E9-44FF-886F-1D1F7DA6997A", 1, 1);
        belonginROIs.add(roi);
        pointOfInterestImp.setBelongingROIs(belonginROIs);

        for(RegionOfInterest ROI : pointOfInterestImp.getAllBelongingROIs()){
            Assert.assertNotNull(ROI);
        }
    }

    /*
     * TU24
     */
    @Test
    public void testGetCategory() throws Exception {
        Assert.assertEquals("category", pointOfInterestImp.getCategory());
    }

    /*
     * TU24
     */
    @Test
    public void testGetDescription() throws Exception {
        Assert.assertEquals("description", pointOfInterestImp.getDescription());
    }

    /*
     * TU24
     */
    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals(this.id, pointOfInterestImp.getId());
    }
    /*
     * TU24
     */
    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals("name", pointOfInterestImp.getName());
    }

    /*
     * TU25
     */
    @Test
    public void testSetBelongingROIs() throws Exception {

        int id = 46;
        String uuid = "xyz";
        int major = 29;
        int minor = 10;

        RegionOfInterest roi = new RegionOfInterestImp(id, uuid, major, minor);
        Collection<RegionOfInterest> collection = new ArrayList<>();
        collection.add(roi);

        this.pointOfInterestImp.setBelongingROIs(collection);

        Assert.assertEquals(collection, pointOfInterestImp.getAllBelongingROIs());
    }
}