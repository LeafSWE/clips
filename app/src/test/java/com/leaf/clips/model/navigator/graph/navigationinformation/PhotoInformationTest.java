package com.leaf.clips.model.navigator.graph.navigationinformation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public class PhotoInformationTest {

    private PhotoInformation photoInformation;

    @Before
    public void init() {
        Collection<PhotoRef> collection = new ArrayList<>(5);
        try {
            collection.add(new PhotoRef(1,new URI("www.google.com")));
            collection.add(new PhotoRef(2,new URI("www.ciao.com")));
            collection.add(new PhotoRef(3,new URI("www.roma.com")));
            collection.add(new PhotoRef(4,new URI("www.lazio.com")));
            collection.add(new PhotoRef(5,new URI("www.vr46.com")));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        this.photoInformation = new PhotoInformation(collection);
    }

    /*
     * TU12
     */
    @Test
    public void testGetPhotoInformation() throws Exception {
        Collection<PhotoRef> photosCollection = this.photoInformation.getPhotoInformation();
        Iterator<PhotoRef> iteratorPhotoRef = photosCollection.iterator();
        PhotoRef phothoRef = iteratorPhotoRef.next();
        Assert.assertEquals(new URI("www.google.com"), phothoRef.getPhotoUri());
        phothoRef = iteratorPhotoRef.next();
        Assert.assertEquals(new URI("www.ciao.com"), phothoRef.getPhotoUri());
        phothoRef = iteratorPhotoRef.next();
        Assert.assertEquals(new URI("www.roma.com"), phothoRef.getPhotoUri());
        phothoRef = iteratorPhotoRef.next();
        Assert.assertEquals(new URI("www.lazio.com"), phothoRef.getPhotoUri());
        phothoRef = iteratorPhotoRef.next();
        Assert.assertEquals(new URI("www.vr46.com"), phothoRef.getPhotoUri());
    }
}