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
public class SQLiteEdgeTypeDaoTest extends InstrumentationTestCase {

    public class DatabaseTest implements BaseColumns {

        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
                EdgeTypeContract.TABLE_NAME + " (" +
                EdgeTypeContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                EdgeTypeContract.COLUMN_TYPENAME+ " TEXT" +
                " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                EdgeTypeContract.TABLE_NAME;
    }

    public class MySQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "SQLiteEdgeTypeDaoTest.db";
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
            db.execSQL("DROP TABLE IF EXISTS " + EdgeTypeContract.TABLE_NAME);
            onCreate(db);
        }

    }

    private Context context;
    private SQLiteDatabase database;
    private SQLiteEdgeTypeDao edgeTypeDao;
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
        edgeTypeDao = new SQLiteEdgeTypeDao(database);
        Assert.assertNotNull(edgeTypeDao);
        columns = new String[]{
                EdgeTypeContract.COLUMN_ID,
                EdgeTypeContract.COLUMN_TYPENAME
        };
        Assert.assertNotNull(columns);
    }

    private void databaseInsert(ContentValues values){
        database.insert(EdgeTypeContract.TABLE_NAME, null, values);
    }

    private ContentValues values(int id) {
        ContentValues values = new ContentValues();
        values.put(EdgeTypeContract.COLUMN_ID, id);
        values.put(EdgeTypeContract.COLUMN_TYPENAME, "TypeName");
        return values;
    }

    @Test
    public void testCursorToType() throws Exception {
        setUp();
        databaseInsert(values(1));
        Cursor cursor = database.query(true, EdgeTypeContract.TABLE_NAME, columns,
                EdgeTypeContract.COLUMN_ID + "=\"1\"", null, null, null, null, null);
        EdgeTypeTable edgeTypeTable = edgeTypeDao.cursorToType(cursor);
        Assert.assertEquals(1, edgeTypeTable.getId());
        Assert.assertEquals("TypeName", edgeTypeTable.getTypeName());
    }

    @Test
    public void testDeleteEdgeType() throws Exception {
        setUp();
        databaseInsert(values(1));
        databaseInsert(values(2));
        edgeTypeDao.deleteEdgeType(1);
        edgeTypeDao.deleteEdgeType(2);
        Cursor cursor = database.query(true, EdgeTypeContract.TABLE_NAME, columns,
                EdgeTypeContract.COLUMN_ID+"=\"1\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
        cursor = database.query(true, EdgeTypeContract.TABLE_NAME, columns,
                EdgeTypeContract.COLUMN_ID+"=\"2\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
    }

    @Test
    public void testFindEdgeType() throws Exception {
        setUp();
        databaseInsert(values(1));
        databaseInsert(values(2));
        EdgeTypeTable edgeTypeTable = edgeTypeDao.findEdgeType(2);
        Assert.assertEquals(2, edgeTypeTable.getId());
        Assert.assertEquals("TypeName", edgeTypeTable.getTypeName());
    }

    @Test
    public void testInsertEdgeType() throws Exception {
        setUp();
        EdgeTypeTable edgeTypeTable = new EdgeTypeTable(1, "newEdgeType");
        edgeTypeDao.insertEdgeType(edgeTypeTable);
        Cursor cursor = database.query(true, EdgeTypeContract.TABLE_NAME, columns,
                EdgeTypeContract.COLUMN_ID + "=" + 1, null, null, null, null, null);
        Assert.assertEquals(1, cursor.getCount());
    }

    @Test
    public void testUpdateEdgeType() throws Exception {
        setUp();
        databaseInsert(values(22));
        EdgeTypeTable edgeTypeTable = new EdgeTypeTable(21, "updated");
        edgeTypeDao.updateEdgeType(22, edgeTypeTable);
        Cursor cursor = database.query(true, EdgeTypeContract.TABLE_NAME, columns,
                EdgeTypeContract.COLUMN_ID + "=" + 21, null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(EdgeTypeContract.COLUMN_ID);
        int typeNameIndex = cursor.getColumnIndex(EdgeTypeContract.COLUMN_TYPENAME);
        Assert.assertEquals(1, cursor.getCount());
        cursor.moveToNext();
        Assert.assertEquals(21, cursor.getInt(idIndex));
        Assert.assertEquals("updated", cursor.getString(typeNameIndex));
    }
}