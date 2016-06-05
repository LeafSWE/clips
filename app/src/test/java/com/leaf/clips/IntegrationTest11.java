package com.leaf.clips;
/**
 * @author Federico Tavella
 * @version 0.00
 * @since 0.00
 */

import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.navigator.graph.navigationinformation.BasicInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.DetailedInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformationImp;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Description
 */
@SmallTest
@RunWith(JUnit4.class)
public class IntegrationTest11 extends TestCase {

    BasicInformation basicInformation;
    DetailedInformation detailedInformation;
    NavigationInformation navigationInformation;
    PhotoInformation photoInformation;
    PhotoRef photoRef;

    @Before
    public void init(){
        basicInformation = new BasicInformation("A");
        detailedInformation = new DetailedInformation("B");
        try {
            photoRef = new PhotoRef(0, new URI("http://www.no.com"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<PhotoRef> list = new ArrayList<>();
        list.add(photoRef);
        photoInformation = new PhotoInformation(list);
        navigationInformation = new NavigationInformationImp(basicInformation,detailedInformation,photoInformation);
    }

    @Test
    public void shouldPreferBasicInformation(){
        assertEquals("A",navigationInformation.getBasicInformation());
    }

    @Test
    public void shouldPreferDetailedInformation(){
        assertEquals("B",navigationInformation.getDetailedInformation());
    }

    @Test
    public void shouldPreferPhotoInformation(){
        assertEquals("http://www.no.com",navigationInformation.getPhotoInformation().
                getPhotoInformation().iterator().next().getPhotoUri().toString());
    }
}
