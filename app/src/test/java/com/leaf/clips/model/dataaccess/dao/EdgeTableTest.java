package com.leaf.clips.model.dataaccess.dao;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

public class EdgeTableTest {

    private String action;
    private String coordinate;
    private double distance;
    private int endROI;
    private int id;
    private String longDescription;
    private int startROI;
    private int typeId;
    private EdgeTable edgeTable;

    @Before
    public void init() {
        action = "action";
        coordinate = "coordinate";
        distance = 3.3;
        endROI = 1;
        id = 2;
        longDescription = "longDescription";
        startROI = 3;
        typeId = 4;
        edgeTable = new EdgeTable(id, startROI, endROI, distance, coordinate, typeId, action, longDescription);
    }

    @Test
    public void testGetAction() throws Exception {
        Assert.assertEquals(action, edgeTable.getAction());
    }

    @Test
    public void testGetCoordinate() throws Exception {
        Assert.assertEquals(coordinate, edgeTable.getCoordinate());
    }

    @Test
    public void testGetDistance() throws Exception {
        Assert.assertEquals(distance, edgeTable.getDistance());
    }

    @Test
    public void testGetEndROI() throws Exception {
        Assert.assertEquals(endROI, edgeTable.getEndROI());
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals(id, edgeTable.getId());
    }

    @Test
    public void testGetLongDescription() throws Exception {
        Assert.assertEquals(longDescription, edgeTable.getLongDescription());
    }

    @Test
    public void testGetStartROI() throws Exception {
        Assert.assertEquals(startROI, edgeTable.getStartROI());
    }

    @Test
    public void testGetTypeId() throws Exception {
        Assert.assertEquals(typeId, edgeTable.getTypeId());
    }
}