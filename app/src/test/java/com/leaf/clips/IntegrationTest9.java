package com.leaf.clips;

import com.leaf.clips.model.navigator.graph.vertex.Vertex;
import com.leaf.clips.model.navigator.graph.vertex.VertexImp;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class IntegrationTest9 {
    @Test
    public void shouldRepresentAVertex() {
        Vertex v = new VertexImp(1);
        Assert.assertNotNull(v);
        Assert.assertEquals(1, v.getId());
    }
}
