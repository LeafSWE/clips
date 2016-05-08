package com.leaf.clips.model.navigator.graph.navigationinformation;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public class BasicInformationTest {

    private BasicInformation basicInformation;

    @Before
    public void init() {
        this.basicInformation = new BasicInformation("TestAction");
    }

    /*
     * TU9
     */
    @Test
    public void testGetBasicInstruction() throws Exception {
        Assert.assertEquals("TestAction", basicInformation.getBasicInstruction());
    }
}