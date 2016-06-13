package com.leaf.clips.model.dataaccess.dao;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

/**
 * TU74
 */
@RunWith(JUnit4.class)
public class RoiPoiTableTest {
    private int poi;
    private int roi;
    private  RoiPoiTable roiPoiTable;

    @Before
    public void init() {
        poi = 1;
        roi = 2;
        roiPoiTable = new RoiPoiTable(roi, poi);
    }

    @Test
    public void testGetRoiID() throws Exception {
        Assert.assertEquals(roi, roiPoiTable.getRoiID());
    }

    @Test
    public void testGetPoiID() throws Exception {
        Assert.assertEquals(poi, roiPoiTable.getPoiID());
    }
}