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

public class PointOfInterestTableTest {

    private String description;
    private int id;
    private int category;
    private String name;
    private PointOfInterestTable pointOfInterestTable;

    @Before
    public void init() {
        description = "description";
        id = 1;
        category = 1;
        name = "name";
        pointOfInterestTable = new PointOfInterestTable(description, id, name, category);
    }

    @Test
    public void testGetCategoryId() throws Exception {
        Assert.assertEquals(category, pointOfInterestTable.getCategoryId());
    }

    @Test
    public void testGetDescription() throws Exception {
        Assert.assertEquals(description, pointOfInterestTable.getDescription());
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals(id, pointOfInterestTable.getId());
    }

    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals(name, pointOfInterestTable.getName());
    }
}