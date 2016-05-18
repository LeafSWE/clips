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
 *
 *
 */

public class RemoteRoiPoiDaoTest {

    private JsonObject js;
    private RoiPoiTable roiPoiTable;
    private RemoteRoiPoiDao remoteRoiPoiDao;
    private final String jsString = "{"+
        "\"roiid\" : 1, " +
        "\"poiid\" : 2"
        +"}";
    private JsonParser parser;

    @Before
    public void init() {
        parser = new JsonParser();
        js = parser.parse(jsString).getAsJsonObject();
        remoteRoiPoiDao = new RemoteRoiPoiDao();
        roiPoiTable = remoteRoiPoiDao.fromJSONToTable(js);
    }

    @Test
    public void testFromJSONToTable() throws Exception {
        Assert.assertEquals(2, roiPoiTable.getPoiID());
        Assert.assertEquals(1, roiPoiTable.getRoiID());
    }
}