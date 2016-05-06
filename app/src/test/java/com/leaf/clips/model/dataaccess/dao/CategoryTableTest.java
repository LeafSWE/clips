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

public class CategoryTableTest {

    private String description;
    private int id;
    private CategoryTable categoryTable;

    @Before
    public void init() {
        description = "description";
        id = 1;
        categoryTable = new CategoryTable(id, description);
    }

    @Test
    public void testGetDescription() throws Exception {
        Assert.assertEquals(description, categoryTable.getDescription());
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals(id, categoryTable.getId());
    }
}