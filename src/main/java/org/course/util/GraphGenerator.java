package org.course.util;

import org.course.model.Graph;
import org.course.algorithms.KruskalMST;
import org.course.algorithms.PrimMST;

import java.io.IOException;
import java.util.*;

public class GraphGenerator {

    private static final int RUNS = 10;

    public static Graph generateGraph(int vertices, int edgeCount, int maxWeight) {
        Graph g = new Graph(vertices);
        Random rand = new Random();
        Set<String> added = new HashSet<>();

        while (g.getEdges().size() < edgeCount) {
            int u = rand.nextInt(vertices);
            int v = rand.nextInt(vertices);
            if (u == v) continue;
            String key = u + "-" + v;
            if (added.contains(key)) continue;
            int w = rand.nextInt(maxWeight) + 1;
            g.addEdge(u, v, w);
            added.add(key);
        }
        return g;
    }

    private static void warmUp() {
        Graph g = generateGraph(8, 10, 10);
        KruskalMST.findMST(g);
        PrimMST.findMST(g);
    }

    private static Map<String, Double> measureAlgorithm(String algorithm, Graph g) {
        double totalTime = 0;
        double totalCost = 0;
        double totalOps = 0;

        for (int i = 0; i < RUNS; i++) {
            Graph graphCopy = generateGraph(g.getVertices(), g.getEdges().size(), 100);
            long start = System.nanoTime();

            Map<String, Object> result = algorithm.equalsIgnoreCase("kruskal")
                    ? KruskalMST.findMST(graphCopy)
                    : PrimMST.findMST(graphCopy);

            long end = System.nanoTime();
            totalTime += (end - start);
            totalCost += ((Number) result.get("total_cost")).doubleValue();
            totalOps += ((Number) result.get("operations")).doubleValue();
        }

        Map<String, Double> stats = new HashMap<>();
        stats.put("avg_time", (totalTime / RUNS) / 1_000_000.0);
        stats.put("avg_cost", totalCost / RUNS);
        stats.put("avg_ops", totalOps / RUNS);
        return stats;
    }

    public static void main(String[] args) throws IOException {
        warmUp();

        List<Map<String, Object>> results = new ArrayList<>();
        int[][] configs = {
                {6, 8, 20},
                {12, 25, 50},
                {25, 60, 100}
        };

        for (int[] cfg : configs) {
            int v = cfg[0], e = cfg[1], w = cfg[2];
            Graph base = generateGraph(v, e, w);

            Map<String, Double> kruskal = measureAlgorithm("kruskal", base);
            Map<String, Double> prim = measureAlgorithm("prim", base);

            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("vertices", v);
            entry.put("edges", e);
            entry.put("kruskal_time_ms", kruskal.get("avg_time"));
            entry.put("prim_time_ms", prim.get("avg_time"));
            entry.put("kruskal_cost", kruskal.get("avg_cost"));
            entry.put("prim_cost", prim.get("avg_cost"));
            entry.put("kruskal_operations", kruskal.get("avg_ops"));
            entry.put("prim_operations", prim.get("avg_ops"));
            entry.put("same_total_cost", Math.abs(kruskal.get("avg_cost") - prim.get("avg_cost")) < 0.001);
            results.add(entry);
        }

        Map<String, Object> output = new LinkedHashMap<>();
        output.put("results", results);
        JSONWriter.writeMSTResult("output/all_results.json", output);

        System.out.println("✅ Full performance metrics saved to output/all_results.json");
    }
}
