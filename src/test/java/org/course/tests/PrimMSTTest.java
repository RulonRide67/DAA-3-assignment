package org.course.tests;

import org.course.model.Graph;
import org.course.util.JSONReader;
import org.course.util.JSONWriter;
import org.course.algorithms.PrimMST;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PrimMSTTest {

    @Test
    public void testPrimMST() throws IOException {
        Graph g = JSONReader.readGraph("data/input.json");
        Map<String, Object> result = PrimMST.findMST(g);

        assertNotNull(result);
        assertTrue((int) result.get("edges_in_mst") <= g.getVertices() - 1);
        assertTrue((double) result.get("execution_time_ms") >= 0);

        JSONWriter.writeMSTResult("output/prim_result.json", result);
        System.out.println("✅ Prim MST result saved to output/prim_result.json");
    }
}
