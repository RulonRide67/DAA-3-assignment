package org.course.tests;

import org.course.model.Graph;
import org.course.util.JSONReader;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JSONReaderTest {

    @Test
    public void testReadGraph() throws IOException {
        Graph g = JSONReader.readGraph("data/input.json");
        assertEquals(5, g.getVertices());
        assertEquals(5, g.getEdges().size());
    }
}
