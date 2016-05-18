package com.leaf.clips.model.dataaccess.dao;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class RemoteRegionOfInterestDaoTest {

    private JsonObject js;
    private RegionOfInterestTable regionOfInterestTable;
    private RemoteRegionOfInterestDao remoteRegionOfInterestDao;
    private final String jsString = "{"+
        "\"id\" : 1, " +
        "\"uuid\" : \"f7826da6-4fa2-4e98-8024-bc5b71e0893e\","+
        "\"major\" : 666,"+
        "\"minor\" : 1001"+
        "}";
    private JsonParser parser;

    @Before
    public void init() {
        parser = new JsonParser();
        js = parser.parse(jsString).getAsJsonObject();
        remoteRegionOfInterestDao = new RemoteRegionOfInterestDao();
        regionOfInterestTable = remoteRegionOfInterestDao.fromJSONToTable(js);
    }

    @Test
    public void testFromJSONToTable() throws Exception {
        Assert.assertEquals(1, regionOfInterestTable.getId());
        Assert.assertEquals(1001, regionOfInterestTable.getMinor());
        Assert.assertEquals(666, regionOfInterestTable.getMajor());
        Assert.assertEquals("f7826da6-4fa2-4e98-8024-bc5b71e0893e", regionOfInterestTable.getUUID());
    }
}