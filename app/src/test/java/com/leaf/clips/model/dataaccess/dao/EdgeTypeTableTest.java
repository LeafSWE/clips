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
 * TU76
 */

@RunWith(JUnit4.class)
public class EdgeTypeTableTest {

    private int id;
    private String typeName;
    private EdgeTypeTable edgeTypeTable;

    @Before
    public void init() {
        id = 1;
        typeName = "typeName";
        edgeTypeTable = new EdgeTypeTable(id, typeName);
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals(id, edgeTypeTable.getId());
    }

    @Test
    public void testGetTypeName() throws Exception {
        Assert.assertEquals(typeName, edgeTypeTable.getTypeName());
    }
}