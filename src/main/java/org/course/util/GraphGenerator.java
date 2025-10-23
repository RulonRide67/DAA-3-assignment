package org.course.util;

import org.course.model.Graph;
import org.course.algorithms.KruskalMST;
import org.course.algorithms.PrimMST;

import java.io.IOException;
import java.util.*;

public class GraphGenerator {

    // Генерация случайного неориентированного графа
    public static Graph generateGraph(int vertices, int edgeCount, int maxWeight) {
        Graph g = new Graph(vertices);
        Random rand = new Random();

        Set<String> added = new HashSet<>();

        while (g.getEdges().size() < edgeCount) {
            int u = rand.nextInt(vertices);
            int v = rand.nextInt(vertices);
            if (u == v) continue;
            String key = u + "-" + v;
            String rev = v + "-" + u;
            if (added.contains(key) || added.contains(rev)) continue;

            int w = rand.nextInt(maxWeight) + 1;
            g.addEdge(u, v, w);
            added.add(key);
        }

        return g;
    }

    public static Map<String, Object> runComparison(Graph g) {
        Map<String, Object> kruskal = KruskalMST.findMST(g);
        Map<String, Object> prim = PrimMST.findMST(g);

        int costKruskal = (int) kruskal.get("total_cost");
        int costPrim = (int) prim.get("total_cost");

        double timeKruskal = (double) kruskal.get("execution_time_ms");
        double timePrim = (double) prim.get("execution_time_ms");

        int opsKruskal = (int) kruskal.get("operations");
        int opsPrim = (int) prim.get("operations");

        boolean sameCost = costKruskal == costPrim;

        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("vertices", g.getVertices());
        summary.put("edges", g.getEdges().size());
        summary.put("same_total_cost", sameCost);
        summary.put("kruskal_time_ms", timeKruskal);
        summary.put("prim_time_ms", timePrim);
        summary.put("kruskal_operations", opsKruskal);
        summary.put("prim_operations", opsPrim);
        summary.put("total_cost", costKruskal);

        return summary;
    }

    public static void main(String[] args) throws IOException {
        List<Map<String, Object>> allResults = new ArrayList<>();

        // Small
        Graph g1 = generateGraph(6, 8, 20);
        allResults.add(runComparison(g1));

        // Medium
        Graph g2 = generateGraph(12, 25, 50);
        allResults.add(runComparison(g2));

        // Large
        Graph g3 = generateGraph(25, 60, 100);
        allResults.add(runComparison(g3));

        Map<String, Object> output = new LinkedHashMap<>();
        output.put("results", allResults);
        JSONWriter.writeMSTResult("output/all_results.json", output);

        System.out.println("✅ All graph results saved to output/all_results.json");
    }
}
