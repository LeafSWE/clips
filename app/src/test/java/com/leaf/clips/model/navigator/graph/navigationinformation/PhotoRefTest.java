package com.leaf.clips.model.navigator.graph.navigationinformation;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public class PhotoRefTest {

    private PhotoRef photoRef;

    @Before
    public void init() {
        try {
            this.photoRef = new PhotoRef(1,new URI("www.google.com"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /*
     * TU11
     */
    @Test
    public void testGetPhotoUri() throws Exception {
        Assert.assertEquals(new URI("www.google.com"), photoRef.getPhotoUri());
    }
    /*
     * TU11
     */
    @Test
    public void testGetPhotoId() throws Exception {
        Assert.assertEquals(1, photoRef.getId());
    }
}