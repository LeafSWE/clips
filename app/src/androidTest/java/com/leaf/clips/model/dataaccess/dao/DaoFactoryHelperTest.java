package com.leaf.clips.model.dataaccess.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DaoFactoryHelperTest extends InstrumentationTestCase {

    public class MySQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "test.db";

        private static final int DATABASE_VERSION = 1;
        public MySQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }

    private DaoFactoryHelper factoryHelper;
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    //@Before
    public void setUp() {
        factoryHelper = DaoFactoryHelper.getInstance();
        Assert.assertNotNull(factoryHelper);
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MySQLiteHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);
    }

    @Test
    public void testGetInstance() throws Exception {
        Assert.assertNotNull(DaoFactoryHelper.getInstance());
    }

    @Test
    public void testGetRemoteDaoFactory() throws Exception {
        setUp();
        Assert.assertNotNull(factoryHelper.getRemoteDaoFactory());
    }

    @Test
    public void testGetSQLiteDaoFactory() throws Exception {
        setUp();
        Assert.assertNotNull(factoryHelper.getSQLiteDaoFactory(database));
    }
}