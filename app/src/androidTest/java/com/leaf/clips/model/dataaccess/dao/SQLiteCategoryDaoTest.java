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

import static org.junit.Assert.*;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SQLiteCategoryDaoTest extends InstrumentationTestCase {

    public class DatabaseTest implements BaseColumns {

        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
                CategoryContract.TABLE_NAME + " (" +
                CategoryContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CategoryContract.COLUMN_DESCRIPTION + " TEXT" +
                " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                CategoryContract.TABLE_NAME;
    }

    public class MySQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "SQLiteCategoryDaoTest.db";
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
            db.execSQL("DROP TABLE IF EXISTS " + CategoryContract.TABLE_NAME);
            onCreate(db);
        }

    }

    private Context context;
    private SQLiteDatabase database;
    private SQLiteCategoryDao categoryDao;
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
        categoryDao = new SQLiteCategoryDao(database);
        Assert.assertNotNull(categoryDao);
        columns = new String[]{
                CategoryContract.COLUMN_ID,
                CategoryContract.COLUMN_DESCRIPTION
        };
        Assert.assertNotNull(columns);
    }

    private void databaseInsert(ContentValues values){
        database.insert(CategoryContract.TABLE_NAME, null, values);
    }

    private ContentValues values(int id) {
        ContentValues values = new ContentValues();
        values.put(CategoryContract.COLUMN_ID, id);
        values.put(CategoryContract.COLUMN_DESCRIPTION, "description");
        return values;
    }
    
    @Test
    public void testCursorToType() throws Exception {
        setUp();
        databaseInsert(values(1));
        Cursor cursor = database.query(true, CategoryContract.TABLE_NAME, columns,
                CategoryContract.COLUMN_ID + "=\"1\"", null, null, null, null, null);
        CategoryTable CategoryTable = categoryDao.cursorToType(cursor);
        Assert.assertEquals(1, CategoryTable.getId());
        Assert.assertEquals("description", CategoryTable.getDescription());
    }

    @Test
    public void testDeleteCategory() throws Exception {
        setUp();
        databaseInsert(values(1));
        databaseInsert(values(2));
        categoryDao.deleteCategory(1);
        categoryDao.deleteCategory(2);
        Cursor cursor = database.query(true, CategoryContract.TABLE_NAME, columns,
                CategoryContract.COLUMN_ID+"=\"1\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
        cursor = database.query(true, CategoryContract.TABLE_NAME, columns,
                CategoryContract.COLUMN_ID+"=\"2\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
    }

    @Test
    public void testFindCategory() throws Exception {
        setUp();
        databaseInsert(values(1));
        databaseInsert(values(2));
        CategoryTable categoryTable = categoryDao.findCategory(2);
        Assert.assertEquals(2, categoryTable.getId());
        Assert.assertEquals("description", categoryTable.getDescription());
    }

    @Test
    public void testInsertCategory() throws Exception {
        setUp();
        CategoryTable categoryTable = new CategoryTable(1, "newCategory");
        categoryDao.insertCategory(categoryTable);
        Cursor cursor = database.query(true, CategoryContract.TABLE_NAME, columns,
                CategoryContract.COLUMN_ID + "=" + 1, null, null, null, null, null);
        Assert.assertEquals(1, cursor.getCount());
    }

    @Test
    public void testUpdateCategory() throws Exception {
        setUp();
        databaseInsert(values(22));
        CategoryTable categoryTable = new CategoryTable(21, "updated");
        categoryDao.updateCategory(22, categoryTable);
        Cursor cursor = database.query(true, CategoryContract.TABLE_NAME, columns,
                CategoryContract.COLUMN_ID + "=" + 21, null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(CategoryContract.COLUMN_ID);
        int typeNameIndex = cursor.getColumnIndex(CategoryContract.COLUMN_DESCRIPTION);
        Assert.assertEquals(1, cursor.getCount());
        cursor.moveToNext();
        Assert.assertEquals(21, cursor.getInt(idIndex));
        Assert.assertEquals("updated", cursor.getString(typeNameIndex));
    }
}