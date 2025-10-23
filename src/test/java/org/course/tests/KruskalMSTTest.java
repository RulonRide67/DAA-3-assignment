package org.course.tests;

import org.course.model.Graph;
import org.course.util.JSONReader;
import org.course.algorithms.KruskalMST;
import org.course.util.JSONWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class KruskalMSTTest {

    @Test
    public void testKruskalMST() throws IOException {
        Graph g = JSONReader.readGraph("data/input.json");
        Map<String, Object> result = KruskalMST.findMST(g);

        assertNotNull(result);
        assertTrue((int) result.get("edges_in_mst") <= g.getVertices() - 1);
        assertTrue((double) result.get("execution_time_ms") >= 0);

        JSONWriter.writeMSTResult("output/kruskal_result.json", result);
        System.out.println("✅ Kruskal MST result saved to output/kruskal_result.json");
    }
}
