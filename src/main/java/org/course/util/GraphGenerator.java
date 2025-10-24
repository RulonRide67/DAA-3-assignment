package org.course.util;

import org.course.model.Graph;
import org.course.algorithms.KruskalMST;
import org.course.algorithms.PrimMST;
import java.io.IOException;
import java.util.*;

public class GraphGenerator {

    private static final int RUNS = 7; // the number of runs algorithms

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

    private static double measureAlgorithm(String algorithm, Graph g) {
        double total = 0;
        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();

            if (algorithm.equalsIgnoreCase("kruskal")) {
                KruskalMST.findMST(g);
            } else {
                PrimMST.findMST(g);
            }

            long end = System.nanoTime();
            total += (end - start);
        }
        return (total / RUNS) / 1_000_000.0; // среднее в миллисекундах
    }

    public static void main(String[] args) throws IOException {
        warmUp();

        List<Map<String, Object>> results = new ArrayList<>();

        int[][] configs = {
                {6, 8, 20},    // small
                {12, 25, 50},  // medium
                {25, 60, 100}  // large
        };

        for (int[] cfg : configs) {
            int v = cfg[0], e = cfg[1], w = cfg[2];
            Graph g = generateGraph(v, e, w);

            double timeKruskal = measureAlgorithm("kruskal", g);
            double timePrim = measureAlgorithm("prim", g);

            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("vertices", v);
            entry.put("edges", e);
            entry.put("kruskal_time_ms", timeKruskal);
            entry.put("prim_time_ms", timePrim);
            results.add(entry);
        }

        Map<String, Object> output = new LinkedHashMap<>();
        output.put("results", results);
        JSONWriter.writeMSTResult("output/all_results.json", output);

        System.out.println("✅ Timing data saved to output/all_results.json");
    }
}
