package com.leaf.clips.model.dataaccess.dao;

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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SQLitePhotoDaoTest extends InstrumentationTestCase {

    public class DatabaseTest implements BaseColumns {

        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
                PhotoContract.TABLE_NAME + " (" +
                PhotoContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PhotoContract.COLUMN_EDGEID + " INTEGER, " +
                PhotoContract.COLUMN_URL + " TEXT" +
                " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                PhotoContract.TABLE_NAME;
    }

    public class MySQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "SQLitePhotoDaoTest.db";
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
            db.execSQL("DROP TABLE IF EXISTS " + PhotoContract.TABLE_NAME);
            onCreate(db);
        }

    }

    private Context context;
    private SQLiteDatabase database;
    private SQLitePhotoDao photoDao;
    private MySQLiteHelper dbHelper;
    private String [] columns;

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
        photoDao = new SQLitePhotoDao(database);
        Assert.assertNotNull(photoDao);
        columns = new String[]{
                PhotoContract.COLUMN_ID,
                PhotoContract.COLUMN_EDGEID,
                PhotoContract.COLUMN_URL
        };
        Assert.assertNotNull(columns);
    }

    private void databaseInsert(ContentValues values){
        database.insert(PhotoContract.TABLE_NAME, null, values);
    }

    private ContentValues values(int id) {
        ContentValues values = new ContentValues();
        values.put(PhotoContract.COLUMN_ID, id);
        values.put(PhotoContract.COLUMN_EDGEID, 2);
        values.put(PhotoContract.COLUMN_URL, "url");
        return values;
    }

    private ContentValues valuesAndEdge(int id, int edge) {
        ContentValues values = new ContentValues();
        values.put(PhotoContract.COLUMN_ID, id);
        values.put(PhotoContract.COLUMN_EDGEID, edge);
        values.put(PhotoContract.COLUMN_URL, "url");
        return values;
    }

    @Test
    public void testCursorToType() throws Exception {
        setUp();
        databaseInsert(values(1));
        Cursor cursor = database.query(true, PhotoContract.TABLE_NAME, columns,
                PhotoContract.COLUMN_ID + "=\"1\"", null, null, null, null, null);
        PhotoTable photoTable = photoDao.cursorToType(cursor);
        Assert.assertEquals(1, photoTable.getId());
        Assert.assertEquals(2, photoTable.getEdgeId());
        Assert.assertEquals("url", photoTable.getUrl());
    }

    @Test
    public void testDeletePhoto() throws Exception {
        setUp();
        databaseInsert(values(1));
        databaseInsert(values(2));
        photoDao.deletePhoto(1);
        photoDao.deletePhoto(2);
        Cursor cursor = database.query(true, PhotoContract.TABLE_NAME, columns,
                PhotoContract.COLUMN_ID+"=\"1\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
        cursor = database.query(true, PhotoContract.TABLE_NAME, columns,
                PhotoContract.COLUMN_ID+"=\"2\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
    }

    @Test
    public void testFindAllPhotosOfEdge() throws Exception {
        setUp();
        databaseInsert(valuesAndEdge(1, 44));
        databaseInsert(valuesAndEdge(2, 44));
        databaseInsert(valuesAndEdge(3, 44));
        databaseInsert(valuesAndEdge(4, 45));
        databaseInsert(valuesAndEdge(5, 45));
        Collection<PhotoTable> photoTables = photoDao.findAllPhotosOfEdge(44);
        Assert.assertEquals(3, photoTables.size());
        int i = 1;
        for (PhotoTable photoTable : photoTables) {
            Assert.assertEquals(i, photoTable.getId());
            Assert.assertEquals(44, photoTable.getEdgeId());
            i++;
        }
    }

    @Test
    public void testFindPhoto() throws Exception {
        setUp();
        databaseInsert(valuesAndEdge(1, 44));
        databaseInsert(valuesAndEdge(2, 44));
        databaseInsert(valuesAndEdge(3, 44));
        databaseInsert(valuesAndEdge(4, 45));
        PhotoTable photoTable = photoDao.findPhoto(4);
        Assert.assertEquals(4, photoTable.getId());
        Assert.assertEquals(45, photoTable.getEdgeId());
        Assert.assertEquals("url", photoTable.getUrl());
    }

    @Test
    public void testInsertPhoto() throws Exception {
        setUp();
        PhotoTable photoTable = new PhotoTable(1, "newurl", 2);
        photoDao.insertPhoto(photoTable);
        Cursor cursor = database.query(true, PhotoContract.TABLE_NAME, columns,
                PhotoContract.COLUMN_ID + "=" + 1, null, null, null, null, null);
        Assert.assertEquals(1, cursor.getCount());
    }

    @Test
    public void testUpdatePhoto() throws Exception {
        setUp();
        databaseInsert(valuesAndEdge(22, 33));
        PhotoTable photoTable = new PhotoTable(21, "updated", 32);
        photoDao.updatePhoto(22, photoTable);
        Cursor cursor = database.query(true, PhotoContract.TABLE_NAME, columns,
                PhotoContract.COLUMN_ID + "=" + 21, null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(PhotoContract.COLUMN_ID);
        int edgeIndex = cursor.getColumnIndex(PhotoContract.COLUMN_EDGEID);
        int urlIndex = cursor.getColumnIndex(PhotoContract.COLUMN_URL);
        Assert.assertEquals(1, cursor.getCount());
        cursor.moveToNext();
        Assert.assertEquals(21, cursor.getInt(idIndex));
        Assert.assertEquals(32, cursor.getInt(edgeIndex));
        Assert.assertEquals("updated", cursor.getString(urlIndex));
    }
}