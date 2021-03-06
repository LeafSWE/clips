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
 * TU77
 */
@RunWith(JUnit4.class)
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