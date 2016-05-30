package com.leaf.clips.presenter;

import android.content.ContentProvider;
import android.database.Cursor;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.util.Log;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.InformationManagerImp;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.BuildingMapImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestInformation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockSettings;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */
@RunWith(AndroidJUnit4.class)
public class SearchSuggestionsProviderTest extends ProviderTestCase2<SearchSuggestionsProvider>{
    InformationManager mockIM;
    BuildingMap mockBM;
    private ContentProvider provider;

    public SearchSuggestionsProviderTest() {
        super(SearchSuggestionsProvider.class,SearchSuggestionsProvider.class.getName() );
    }

    @Before
    @Override
    public void setUp() throws Exception {
        setContext(InstrumentationRegistry.getTargetContext());
        super.setUp();
        mockIM = Mockito.mock(InformationManagerImp.class);
        mockBM = Mockito.mock(BuildingMapImp.class);
        PointOfInterestInformation info1 = new PointOfInterestInformation("1C150","Descrizione POI 1","Aule");
        PointOfInterestInformation info2 = new PointOfInterestInformation("2C150","Descrizione POI 2","Aule");
        PointOfInterestInformation info3 = new PointOfInterestInformation("4B150","Descrizione POI 3","Aule");
        PointOfInterest p1 = new PointOfInterestImp(1,info1);
        PointOfInterest p2 = new PointOfInterestImp(2,info2);
        PointOfInterest p3 = new PointOfInterestImp(3,info3);
        Collection<PointOfInterest> pois = new ArrayList<>();
        pois.add(p1);
        pois.add(p2);
        pois.add(p3);
        Field field = (InformationManagerImp.class).getDeclaredField("map");
        Field field2 = (BuildingMapImp.class).getDeclaredField("pois");
        Field field3 = (getProvider().getClass()).getDeclaredField("informationManager");
        field.setAccessible(true);
        field2.setAccessible(true);
        field3.setAccessible(true);
        field.set(mockIM, mockBM);
        field2.set(mockBM,pois);
        field3.set(getProvider(),mockIM);

    }

    @Test
    public void testQuery() throws Exception {
        provider = getProvider();
        String[] projection = {};
        String selection = null;
        String[] selectionArgs = {"1C150"};
        String sortOrder = null;
        Cursor result = provider.query(Uri.parse("content://com.leaf.clips.presenter.SearchSuggestionsProvider"), projection, selection, selectionArgs, sortOrder);
        assertEquals(result.getCount(), 1); //check number of returned rows
        assertEquals(result.getColumnCount(), 3); //check number of returned columns
        /*
        result.moveToNext();

        for(int i = 0; i < result.getCount(); i++) {
            String name = result.getString(1);
            result.moveToNext();
        }*/


    }
}