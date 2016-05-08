package com.leaf.clips.model.navigator.graph.navigationinformation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public class NavigationInformationImpTest {

    private NavigationInformationImp navigationInformationImp;
    private PhotoInformation photoInformation;

    @Before
    public void init() {
        //Create a basic info
        BasicInformation basicInformation = new BasicInformation("Basic Instruction");

        //Create a detailed info
        DetailedInformation detailedInformation = new DetailedInformation("Detailed Instruction");

        //Create a Collection of PhotoRef
        Collection<PhotoRef> photoRefs = new ArrayList<>();
        try {
            photoRefs.add(new PhotoRef(1,new URI("www.google.com")));
            photoRefs.add(new PhotoRef(2,new URI("www.apple.com")));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //Create a PhotoInformation
        this.photoInformation = new PhotoInformation(photoRefs);

        this.navigationInformationImp = new NavigationInformationImp(basicInformation, detailedInformation, photoInformation);
    }
    /*
     * TU13
     */
    @Test
    public void testGetBasicInformation() throws Exception {
        Assert.assertEquals("Basic Instruction", navigationInformationImp.getBasicInformation());
    }
    /*
     * TU13
     */
    @Test
    public void testGetDetailedInformation() throws Exception {
        Assert.assertEquals("Detailed Instruction", navigationInformationImp.getDetailedInformation());
    }
    /*
     * TU13
     */
    @Test
    public void testGetPhotoInformation() throws Exception {
        Assert.assertNotNull(navigationInformationImp.getPhotoInformation());
        Assert.assertEquals(photoInformation, navigationInformationImp.getPhotoInformation());
    }
}