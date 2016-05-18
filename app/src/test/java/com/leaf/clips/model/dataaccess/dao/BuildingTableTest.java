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

public class BuildingTableTest {
    private String address;
    private String description;
    private int id;
    private int major;
    private String name;
    private String openingHour;
    private String size;
    private String uuid;
    private int version;
    private BuildingTable building;

    @Before
    public void init() {
        address = "address";
        description = "description";
        id = 1;
        major = 1;
        name = "name";
        openingHour = "openingHours";
        size = "size";
        uuid = "uuid";
        version = 1;
        building = new BuildingTable(id, uuid, major, name, description, openingHour, address, version, size);
    }

    @Test
    public void testGetAddress() throws Exception {
        Assert.assertEquals(address, building.getAddress());
    }

    @Test
    public void testGetDescription() throws Exception {
        Assert.assertEquals(description, building.getDescription());
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals(id, building.getId());
    }

    @Test
    public void testGetMajor() throws Exception {
        Assert.assertEquals(major, building.getMajor());
    }

    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals(name, building.getName());
    }

    @Test
    public void testGetOpeningHours() throws Exception {
        Assert.assertEquals(openingHour, building.getOpeningHours());
    }

    @Test
    public void testGetSize() throws Exception {
        Assert.assertEquals(size, building.getSize());
    }

    @Test
    public void testGetUUID() throws Exception {
        Assert.assertEquals(uuid, building.getUUID());
    }

    @Test
    public void testGetVersion() throws Exception {
        Assert.assertEquals(version, building.getVersion());
    }
}