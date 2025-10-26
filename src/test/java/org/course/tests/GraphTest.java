package org.course.tests;

import org.course.model.Graph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    public void testAddEdge() {
        Graph g = new Graph(3);
        g.addEdge(0, 1, 5);
        g.addEdge(1, 2, 7);

        assertEquals(3, g.getVertices());
        assertEquals(2, g.getEdges().size());
        assertEquals(5, g.getEdges().get(0).weight);
    }
}
