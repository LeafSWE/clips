package com.leaf.clips.model.navigator.graph.vertex;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public class VertexImpTest {

    private VertexImp vertexImp;

    @Before
    public void init() {
        this.vertexImp = new VertexImp(15);
    }

    /*
     * TU8
     */
    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals(15, vertexImp.getId());
    }
}