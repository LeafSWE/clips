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
public class RemoteCategoryDaoTest {

    private JsonObject js;
    private CategoryTable categoryTable;
    private RemoteCategoryDao remoteCategoryDao;
    private final String jsString = "{"
            +"\"id\" : 1,"
            +"\"description\" : \"description\""
            +"}";
    private JsonParser parser;

    @Before
    public void init() {
        parser = new JsonParser();
        js = parser.parse(jsString).getAsJsonObject();
        remoteCategoryDao = new RemoteCategoryDao();
        categoryTable = remoteCategoryDao.fromJSONToTable(js);
    }

    @Test
    public void testFromJSONToTable() throws Exception {
        Assert.assertEquals(1, categoryTable.getId());
        Assert.assertEquals("description", categoryTable.getDescription());
    }
}