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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */

/**
 * TU104 & TU110
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
        //costruzione mock per campi informationManager e per metodi stub
        mockIM = Mockito.mock(InformationManager.class);
        mockBM = Mockito.mock(BuildingMap.class);

        //costruzione lista di poi
        PointOfInterestInformation info1 =
                new PointOfInterestInformation("1C150","Descrizione POI 1","Aule");
        PointOfInterestInformation info2 =
                new PointOfInterestInformation("2C150","Descrizione POI 2","Aule");
        PointOfInterestInformation info3 =
                new PointOfInterestInformation("4B150","Descrizione POI 3","Aule");
        PointOfInterest p1 = new PointOfInterestImp(1,info1);
        PointOfInterest p2 = new PointOfInterestImp(2,info2);
        PointOfInterest p3 = new PointOfInterestImp(3,info3);
        Collection<PointOfInterest> pois = new ArrayList<>();
        pois.add(p1);
        pois.add(p2);
        pois.add(p3);

        //stub per la chiamata informationManager.getBuildingMap().getAllPOIs
        //ritorno di un mock per BuildingMap
        when(mockIM.getBuildingMap()).thenReturn(mockBM);
        //ritorno di una lista di poi per il mock di BuildingMap
        when(mockBM.getAllPOIs()).thenReturn(pois);
        //recupero tramite reflection della Collection<PointOfInterest> pois del Provider
        Field field_pois = (getProvider().getClass()).getDeclaredField("pois");
        //recupero tramite reflection del campo informationManager del Provider
        Field field_informationManager = (getProvider().getClass()).
                getDeclaredField("informationManager");

        field_pois.setAccessible(true);
        //Setto il campo pois con la lista pois creata
        field_pois.set(getProvider(), pois);

        field_informationManager.setAccessible(true);
        //Setto il campo informationManager con il mock
        field_informationManager.set(getProvider(),mockIM);

    }

    @Test
    public void testQuery() throws Exception {
        provider = getProvider();
        String query = "1C150";
        String[] projection = {};
        String selection = null;
        String[] selectionArgs = {query};
        String sortOrder = null;

        Cursor result =
                provider.query(Uri
                        .parse("content://com.leaf.clips.presenter.SearchSuggestionsProvider"),
                        projection, selection, selectionArgs, sortOrder);
        assertEquals(1,result.getCount());
        assertEquals(3, result.getColumnCount());

        result.moveToNext();
        for(int i = 0; i < result.getCount(); i++) {
            String name = result.getString(1);
            assertEquals(query,name);
            result.moveToNext();
        }


    }
}