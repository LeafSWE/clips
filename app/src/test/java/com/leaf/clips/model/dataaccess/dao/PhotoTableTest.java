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

public class PhotoTableTest {

    private int edgeId;
    private int id;
    private String url;
    private PhotoTable photoTable;

    @Before
    public void init() {
        edgeId = 1;
        id = 2;
        url = "url";
        photoTable = new PhotoTable(id, url, edgeId);
    }

    @Test
    public void testGetEdgeId() throws Exception {
        Assert.assertEquals(edgeId, photoTable.getEdgeId());
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals(id, photoTable.getId());
    }

    @Test
    public void testGetUrl() throws Exception {
        Assert.assertEquals(url, photoTable.getUrl());
    }
}