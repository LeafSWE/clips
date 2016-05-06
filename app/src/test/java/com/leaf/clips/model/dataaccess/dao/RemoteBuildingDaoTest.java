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
public class RemoteBuildingDaoTest {

    private JsonObject js;
    private BuildingTable buildingTable;
    private RemoteBuildingDao remoteBuildingDao;
    private final String jsString = "{" +
            "\"id\" : 1," +
            "\"name\" : \"Torre Archimede\"," +
            "\"uuid\" : \"f7826da6-4fa2-4e98-8024-bc5b71e0893e\"," +
            "\"major\" : 666," +
            "\"description\" : \"Edificio in cui è situato il Dipartimento di Matematica.\"," +
            "\"openingHours\" : \"08:00-19:00\"," +
            "\"address\" : \"Via Trieste 63, 35121, Padova (PD)\"," +
            "\"mapVersion\" : 3," +
            "\"mapSize\" : \"5.2 KB\"" +
            "}";
    private JsonParser parser;

    @Before
    public void init() {
        parser = new JsonParser();
        js = parser.parse(jsString).getAsJsonObject();
        remoteBuildingDao = new RemoteBuildingDao();
        buildingTable = remoteBuildingDao.fromJSONToTable(js);
    }

    @Test
    public void testFromJSONToTable() throws Exception {
        Assert.assertEquals(1, buildingTable.getId());
        Assert.assertEquals("f7826da6-4fa2-4e98-8024-bc5b71e0893e", buildingTable.getUUID());
        Assert.assertEquals(666, buildingTable.getMajor());
        Assert.assertEquals("Torre Archimede", buildingTable.getName());
        Assert.assertEquals("Edificio in cui è situato il Dipartimento di Matematica.", buildingTable.getDescription());
        Assert.assertEquals("08:00-19:00", buildingTable.getOpeningHours());
        Assert.assertEquals("Via Trieste 63, 35121, Padova (PD)", buildingTable.getAddress());
        Assert.assertEquals(3, buildingTable.getVersion());
        Assert.assertEquals("5.2 KB", buildingTable.getSize());
    }
}