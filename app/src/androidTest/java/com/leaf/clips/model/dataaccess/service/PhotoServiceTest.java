package com.leaf.clips.model.dataaccess.service;

import com.google.gson.JsonObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.dao.PhotoContract;
import com.leaf.clips.model.dataaccess.dao.PhotoTable;
import com.leaf.clips.model.dataaccess.dao.RemotePhotoDao;
import com.leaf.clips.model.dataaccess.dao.SQLitePhotoDao;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class PhotoServiceTest {

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

    private PhotoService photoService;
    private Context context;
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;
    private SQLitePhotoDao sqLitePhotoDao;
    private RemotePhotoDao remotePhotoDao;
    private String[] columns;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        Assert.assertNotNull(context);
        dbHelper = new MapsDbHelper(context);
        Assert.assertNotNull(dbHelper);
        database = dbHelper.getWritableDatabase();
        Assert.assertNotNull(database);
        database.execSQL(DatabaseTest.SQL_DELETE_ENTRIES);
        database.execSQL(DatabaseTest.SQL_CREATE_ENTRIES);
        sqLitePhotoDao = new SQLitePhotoDao(database);
        Assert.assertNotNull(sqLitePhotoDao);
        remotePhotoDao = new RemotePhotoDao();
        Assert.assertNotNull(remotePhotoDao);
        photoService = new PhotoService(sqLitePhotoDao, remotePhotoDao);
        Assert.assertNotNull(photoService);
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

    private ContentValues valuesAndUrl(int id, String url) {
        ContentValues values = new ContentValues();
        values.put(PhotoContract.COLUMN_ID, id);
        values.put(PhotoContract.COLUMN_EDGEID, 2);
        values.put(PhotoContract.COLUMN_URL, url);
        return values;
    }

    private ContentValues valuesAndEdgeAndUrl(int id, int edge, String url) {
        ContentValues values = new ContentValues();
        values.put(PhotoContract.COLUMN_ID, id);
        values.put(PhotoContract.COLUMN_EDGEID, edge);
        values.put(PhotoContract.COLUMN_URL, url);
        return values;
    }

    @Test
    public void shouldDeleteAPhoto() throws Exception {
        setUp();
        databaseInsert(valuesAndUrl(1, "myUrl"));
        photoService.deletePhoto(1);

        Cursor cursor = database.query(true, PhotoContract.TABLE_NAME, columns,
                PhotoContract.COLUMN_ID + "=\"1\"", null, null, null, null, null);
        Assert.assertEquals(0, cursor.getCount());
    }

    @Test
    public void shouldRetrieveAPhoto() throws Exception {
        setUp();
        int photoId = 1;
        String myUrl = "myUrl";
        databaseInsert(valuesAndUrl(photoId, myUrl));

        PhotoRef ref = photoService.findPhoto(photoId);
        URI retrievedUri = ref.getPhotoUri();
        String retrievedUri_s = retrievedUri.toString();
        Assert.assertEquals(myUrl, retrievedUri_s);
    }

    @Test
    public void shouldRetrieveAllPhotosOfAnEdge() throws Exception {
        setUp();
        int edgeId = 1;
        databaseInsert(valuesAndEdgeAndUrl(1, edgeId, "myUrl_1"));
        databaseInsert(valuesAndEdgeAndUrl(2, edgeId, "myUrl_2"));
        databaseInsert(valuesAndEdgeAndUrl(3, edgeId, "myUrl_3"));
        databaseInsert(valuesAndEdgeAndUrl(4, edgeId, "myUrl_4"));

        Collection<PhotoRef> refs = photoService.findAllPhotosOfEdge(edgeId);
        Map<Integer, String> mapRefs = new HashMap<>();
        for(PhotoRef ref : refs) mapRefs.put(ref.getId(), ref.getPhotoUri().toString());
        Assert.assertEquals("myUrl_1", mapRefs.get(1));
        Assert.assertEquals("myUrl_2", mapRefs.get(2));
        Assert.assertEquals("myUrl_3", mapRefs.get(3));
        Assert.assertEquals("myUrl_4", mapRefs.get(4));
    }

    @Test
    public void shouldCreatePhotoTableAndInsertItInTheDB() throws Exception {
        setUp();
        JsonObject js = new JsonObject();
        js.addProperty("id", 1);
        js.addProperty("url", "myUrl");
        js.addProperty("edgeId", 2);
        photoService.convertAndInsert(js);

        PhotoRef ref = photoService.findPhoto(1);
        Assert.assertNotNull(ref);
    }
}
