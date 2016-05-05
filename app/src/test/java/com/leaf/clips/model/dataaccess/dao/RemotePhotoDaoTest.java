package com.leaf.clips.model.dataaccess.dao;

import org.junit.Before;
import org.junit.Test;
import com.google.gson.*;
import junit.framework.Assert;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

public class RemotePhotoDaoTest {

    private JsonObject js;
    private PhotoTable photoTable;
    private RemotePhotoDao remotePhotoDao;
    private final String jsString = "{"
            +"\"id\" : 1,"+
            "\"edgeId\" : 2,"+
            "\"url\" : \"URL della prima foto\"}";
    private JsonParser parser;

    @Before
    public void init() {
        parser = new JsonParser();
        js = parser.parse(jsString).getAsJsonObject();
        remotePhotoDao = new RemotePhotoDao();
        photoTable = remotePhotoDao.fromJSONToTable(js);
    }

    @Test
    public void testFromJSONToTable() throws Exception {
        photoTable = remotePhotoDao.fromJSONToTable(js);
        Assert.assertEquals(1, photoTable.getId());
        Assert.assertEquals(2, photoTable.getEdgeId());
        Assert.assertEquals("URL della prima foto", photoTable.getUrl());
    }
}