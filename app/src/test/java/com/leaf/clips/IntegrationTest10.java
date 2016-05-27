package com.leaf.clips;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterestInformation;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterestImp;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Integration test 10
 */

@RunWith(JUnit4.class)
@SmallTest
public class IntegrationTest10 extends TestCase{

    PointOfInterest poi;
    PointOfInterestInformation poiInfo;
    RegionOfInterest roi;

    @Before
    public void init(){
        poiInfo = new PointOfInterestInformation("A","B","C");
        poi = new PointOfInterestImp(0, poiInfo);
        roi = new RegionOfInterestImp(0,"19235dd2-574a-4702-a42e-caccac06e325",666,10010);
        List<RegionOfInterest> rois = new ArrayList<>();
        rois.add(roi);
        poi.setBelongingROIs(rois);
        List<PointOfInterest> pois = new ArrayList<>();
        pois.add(poi);
        roi.setNearbyPOIs(pois);
    }

    @Test
    public void shouldAccessPOIInfo(){
        assertEquals(poiInfo.getCategory(), poi.getCategory());
        assertEquals(poiInfo.getDescription(), poi.getDescription());
        assertEquals(poiInfo.getName(), poi.getName());
    }

    @Test
    public void ROIShouldContainPOI(){

        Collection<PointOfInterest> pois = roi.getAllNearbyPOIs();
        Iterator<PointOfInterest> it = pois.iterator();
        PointOfInterest singlePOI = it.next();
        assertEquals(singlePOI,poi);
    }

    @Test
    public void POIShouldBelongToROI(){

        Collection<RegionOfInterest> rois = poi.getAllBelongingROIs();
        Iterator<RegionOfInterest> it = rois.iterator();
        RegionOfInterest singleROI = it.next();
        assertEquals(singleROI,roi);
    }



}
