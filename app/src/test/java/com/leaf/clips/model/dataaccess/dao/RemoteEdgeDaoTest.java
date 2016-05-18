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

public class RemoteEdgeDaoTest {

    private JsonObject js;
    private EdgeTable edgeTable;
    private RemoteEdgeDao remoteEdgeDao;
    private final String jsString = "{"
            +"\"id\" : 1,"
            +"\"startROI\" : 2,"
            +"\"endROI\" : 3,"
            +"\"distance\" : 50,"
            +"\"coordinate\" : \"23 N-E\","
            +"\"typeId\" : 4,"
            +"\"action\" : \"Alla fine del corridoio troverai il bagno femminile.\","
            +"\"longDescription\" : \"Esci da aula 2BC60, prosegui nel corridoio e in fondo a sinistra troverai il bagno femminile\""
        +"}";
    private JsonParser parser;

    @Before
    public void init() {
        parser = new JsonParser();
        js = parser.parse(jsString).getAsJsonObject();
        remoteEdgeDao = new RemoteEdgeDao();
        edgeTable = remoteEdgeDao.fromJSONToTable(js);
    }

    @Test
    public void testFromJSONToTable() throws Exception {
        Assert.assertEquals(1, edgeTable.getId());
        Assert.assertEquals(2, edgeTable.getStartROI());
        Assert.assertEquals(3, edgeTable.getEndROI());
        Assert.assertEquals(50.0, edgeTable.getDistance());
        Assert.assertEquals("23 N-E", edgeTable.getCoordinate());
        Assert.assertEquals(4, edgeTable.getTypeId());
        Assert.assertEquals("Alla fine del corridoio troverai il bagno femminile.", edgeTable.getAction());
        Assert.assertEquals("Esci da aula 2BC60, prosegui nel corridoio e in fondo a sinistra troverai il bagno femminile", edgeTable.getLongDescription());
    }
}