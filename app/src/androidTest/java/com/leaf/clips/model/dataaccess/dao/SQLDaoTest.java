package com.leaf.clips.model.dataaccess.dao;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SQLDaoTest extends InstrumentationTestCase {

    public class DatabaseTest implements BaseColumns {
        public static final String TABLE_NAME = "Test_Table";
        public static final String COLUMN_NAME_ID_TEST = "IDTest";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID_TEST + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_DESCRIPTION + " TEXT "+ " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public class MySQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "test.db";
        private static final int DATABASE_VERSION = 1;

        public MySQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(MySQLiteHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DatabaseTest.TABLE_NAME);
            onCreate(db);
        }

    }

    private MySQLiteHelper dbHelper;
    private SQLiteDatabase database;
    private SQLDao sqlDao;
    private Context context;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MySQLiteHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);
        database.execSQL(DatabaseTest.SQL_DELETE_ENTRIES);
        database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        sqlDao = new SQLDao(database);
        Assert.assertNotNull(sqlDao);
    }

    @Test
    public void testDelete() throws Exception {
        setUp();
        database.delete(DatabaseTest.TABLE_NAME, "1", null);
        for (int i = 0; i < 5; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseTest.COLUMN_NAME_ID_TEST, i);
            values.put(DatabaseTest.COLUMN_NAME_DESCRIPTION, "description:" + i);
            database.insert(DatabaseTest.TABLE_NAME, "",values);
        }
        int deletedRows;
        deletedRows = sqlDao.delete(DatabaseTest.TABLE_NAME, "1", null);
        Assert.assertEquals(5, deletedRows);
        deletedRows = sqlDao.delete(DatabaseTest.TABLE_NAME, "1", null);
        Assert.assertEquals(0, deletedRows);
    }

    @Test
    public void testInsert() throws Exception {
        setUp();
        for (int i = 0; i < 5; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseTest.COLUMN_NAME_ID_TEST, i);
            values.put(DatabaseTest.COLUMN_NAME_DESCRIPTION, "description:" + i);
            sqlDao.insert(DatabaseTest.TABLE_NAME, values);
        }
        Cursor cursor = database.rawQuery("select * from " + DatabaseTest.TABLE_NAME, null);
        int numberOfRows = cursor.getCount();
        Assert.assertEquals(5, numberOfRows);
        int idIndex = cursor.getColumnIndex(DatabaseTest.COLUMN_NAME_ID_TEST);
        int descriptionIndex = cursor.getColumnIndex(DatabaseTest.COLUMN_NAME_DESCRIPTION);
        for (int i = 0; i < 5; i++) {
            cursor.moveToNext();
            int id = cursor.getInt(idIndex);
            String description = cursor.getString(descriptionIndex);
            Assert.assertEquals(i, id);
            Assert.assertEquals("description:"+i, description);
        }
    }

    @Test
    public void testQuery() throws Exception {
        setUp();
        String[] columns = {DatabaseTest.COLUMN_NAME_ID_TEST,
                DatabaseTest.COLUMN_NAME_DESCRIPTION};
        for (int i = 0; i < 5; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseTest.COLUMN_NAME_ID_TEST, i);
            values.put(DatabaseTest.COLUMN_NAME_DESCRIPTION, "description:" + i);
            database.insert(DatabaseTest.TABLE_NAME, null, values);
        }
        int numberOfEntry = sqlDao.query(true, DatabaseTest.TABLE_NAME, columns, "1", null, null,
                null, null, null).getCount();
        Assert.assertTrue("number:" + numberOfEntry, 5 <= numberOfEntry);
        numberOfEntry = sqlDao.query(true, DatabaseTest.TABLE_NAME, columns,
                DatabaseTest.COLUMN_NAME_DESCRIPTION + "=\"description:3\"", null, null,
                null, null, null).getCount();
        Assert.assertFalse("number:" + numberOfEntry, numberOfEntry == 0);
        database.delete(DatabaseTest.TABLE_NAME, "1", null);
        int numberOfRows = sqlDao.query(true, DatabaseTest.TABLE_NAME, columns,
                DatabaseTest.COLUMN_NAME_DESCRIPTION + "=\"description:3\"", null, null,
                null, null, null).getCount();
        Assert.assertTrue("number of rows:" + numberOfRows, numberOfRows == 0);
    }

    @Test
    public void testRawQuery() throws Exception {
        setUp();
        String query = "select * from " + DatabaseTest.TABLE_NAME;
        Cursor c1 = database.rawQuery(query, null);
        Cursor c2 = sqlDao.rawQuery(query, null);
        ContentValues values = new ContentValues();
        values.put(DatabaseTest.COLUMN_NAME_ID_TEST, "127");
        values.put(DatabaseTest.COLUMN_NAME_DESCRIPTION, "description:127");
        database.insert(DatabaseTest.TABLE_NAME, null, values);
        while (c1.moveToNext()) {
            c2.moveToNext();
            Assert.assertEquals(c1.getString(0), c2.getString(0));
            Assert.assertEquals(c1.getString(1), c2.getString(1));
        }
    }

    @Test
    public void testUpdate() throws Exception {
        setUp();
        database.delete(DatabaseTest.TABLE_NAME, DatabaseTest.COLUMN_NAME_ID_TEST + "=33", null);
        ContentValues values = new ContentValues();
        values.put(DatabaseTest.COLUMN_NAME_ID_TEST, "33");
        values.put(DatabaseTest.COLUMN_NAME_DESCRIPTION, "d:33");
        database.insert(DatabaseTest.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(DatabaseTest.COLUMN_NAME_DESCRIPTION, "description:33");
        sqlDao.update(DatabaseTest.TABLE_NAME, values, DatabaseTest.COLUMN_NAME_DESCRIPTION + "=\"d:33\"", null);
        Cursor cursor = database.rawQuery("select * from " + DatabaseTest.TABLE_NAME
                + " where " + DatabaseTest.COLUMN_NAME_ID_TEST + "=33", null);
        Assert.assertTrue(cursor.getCount()>0);
        int descriptionIndex = cursor.getColumnIndex(DatabaseTest.COLUMN_NAME_DESCRIPTION);
        String description;
        while (cursor.moveToNext()){
            description = cursor.getString(descriptionIndex);
            Assert.assertEquals("description:33", description);
        }
    }
}