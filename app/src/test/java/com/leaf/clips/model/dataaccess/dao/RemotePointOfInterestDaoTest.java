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
public class RemotePointOfInterestDaoTest {

    private JsonObject js;
    private PointOfInterestTable pointOfInterestTable;
    private RemotePointOfInterestDao remotePointOfInterestDao;
    private final String jsString = "{" +
            "\"id\" : 1," +
            "\"name\" : \"2BC60\"," +
            "\"description\" : \"Aula 2BC60\"," +
            "\"categoryId\" : 2" +
            "}";
    private JsonParser parser;

    @Before
    public void init() {
        parser = new JsonParser();
        js = parser.parse(jsString).getAsJsonObject();
        remotePointOfInterestDao = new RemotePointOfInterestDao();
        pointOfInterestTable = remotePointOfInterestDao.fromJSONToTable(js);
    }

    @Test
    public void testFromJSONToTable() throws Exception {
        Assert.assertEquals(1, pointOfInterestTable.getId());
        Assert.assertEquals(2, pointOfInterestTable.getCategoryId());
        Assert.assertEquals("2BC60", pointOfInterestTable.getName());
        Assert.assertEquals("Aula 2BC60", pointOfInterestTable.getDescription());
    }
}