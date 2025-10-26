package org.course.tests;

import org.course.model.Graph;
import org.course.algorithms.KruskalMST;
import org.course.util.JSONReader;
import org.course.util.JSONWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class KruskalMSTTest {

    @Test
    public void testKruskalOutputJson() throws IOException {
        Graph g = JSONReader.readGraph("data/input.json");


            for (int i = 0; i < 5; i++) KruskalMST.findMST(g);

        Map<String, Object> result = KruskalMST.findMST(g);
        assertNotNull(result);
        assertEquals("Kruskal", result.get("algorithm"));

        JSONWriter.writeMSTResult("output/kruskal_result.json", result);
    }
}
