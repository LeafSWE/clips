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

public class RegionOfInterestTableTest {

    private String uuid;
    private int id;
    private int major;
    private int minor;
    private RegionOfInterestTable regionOfInterestTable;

    @Before
    public void init(){
        this.uuid = "uuid";
        this.major = 1;
        this.minor = 2;
        this.id = 3;
        regionOfInterestTable = new RegionOfInterestTable(id, uuid, major, minor);
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals(id, regionOfInterestTable.getId());
    }

    @Test
    public void testGetMajor() throws Exception {
        Assert.assertEquals(major, regionOfInterestTable.getMajor());
    }

    @Test
    public void testGetMinor() throws Exception {
        Assert.assertEquals(minor, regionOfInterestTable.getMinor());
    }

    @Test
    public void testGetUUID() throws Exception {
        Assert.assertEquals(uuid, regionOfInterestTable.getUUID());
    }
}