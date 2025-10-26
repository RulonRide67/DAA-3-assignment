package org.course.tests;

import org.course.model.Graph;
import org.course.algorithms.PrimMST;
import org.course.util.JSONReader;
import org.course.util.JSONWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PrimMSTTest {

    @Test
    public void testPrimOutputJson() throws IOException {
        Graph g = JSONReader.readGraph("data/input.json");

        for (int i = 0; i < 10; i++) PrimMST.findMST(g);

        Map<String, Object> result = PrimMST.findMST(g);
        assertNotNull(result);
        assertEquals("Prim", result.get("algorithm"));

        JSONWriter.writeMSTResult("output/prim_result.json", result);
    }
}
