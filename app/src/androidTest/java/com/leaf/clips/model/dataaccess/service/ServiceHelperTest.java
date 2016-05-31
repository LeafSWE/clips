package com.leaf.clips.model.dataaccess.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.dao.RemoteDaoFactory;
import com.leaf.clips.model.dataaccess.dao.SQLiteDaoFactory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 */

/**
 * TU45
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class ServiceHelperTest {
    private Context context;
    private SQLiteDatabase database;
    private MapsDbHelper dbHelper;
    private String dbURL;
    private SQLiteDaoFactory sqLiteDaoFactory;
    private RemoteDaoFactory remoteDaoFactory;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MapsDbHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);
        dbURL = dbHelper.getRemoteDatabaseURL();
        Assert.assertNotNull(dbURL);
        sqLiteDaoFactory = new SQLiteDaoFactory(database);
        Assert.assertNotNull(sqLiteDaoFactory);
        remoteDaoFactory = new RemoteDaoFactory();
        Assert.assertNotNull(remoteDaoFactory);
    }

    @Test
    public void shouldCreateADatabaseService() throws Exception {
        setUp();
        DatabaseService dbService =
                ServiceHelper.getService(sqLiteDaoFactory, remoteDaoFactory, dbURL);
        Assert.assertNotNull(dbService);
    }
}
