package com.leaf.clips.model.navigator.graph.navigationinformation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public class DetailedInformationTest {

    private DetailedInformation detailedInformation;

    @Before
    public void init() throws Exception {
        this.detailedInformation = new DetailedInformation("DetailedInformation");
    }
    /*
     * TU10
     */
    @Test
    public void testGetDetailedInformation() throws Exception {
        Assert.assertEquals("DetailedInformation", detailedInformation.getDetailedInformation());
    }
}