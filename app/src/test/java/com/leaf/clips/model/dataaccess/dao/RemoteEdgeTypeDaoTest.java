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

public class RemoteEdgeTypeDaoTest {

    private JsonObject js;
    private EdgeTypeTable edgeTypeTable;
    private RemoteEdgeTypeDao remoteEdgeTypeDao;
    private final String jsString = "{"
            +"\"id\" : 1,"
            +"\"typeName\" : \"description\""
        +"}";
    private JsonParser parser;

    @Before
    public void init() {
        parser = new JsonParser();
        js = parser.parse(jsString).getAsJsonObject();
        remoteEdgeTypeDao = new RemoteEdgeTypeDao();
        edgeTypeTable = remoteEdgeTypeDao.fromJSONToTable(js);
    }

    @Test
    public void testFromJSONToTable() throws Exception {
        edgeTypeTable = remoteEdgeTypeDao.fromJSONToTable(js);
        Assert.assertEquals(1, edgeTypeTable.getId());
        Assert.assertEquals("description", edgeTypeTable.getTypeName());
    }
}