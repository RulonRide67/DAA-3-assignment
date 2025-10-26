package org.course.util;

import org.course.model.Graph;
import org.course.algorithms.KruskalMST;
import org.course.algorithms.PrimMST;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ComparisonRunner {

    public static void main(String[] args) throws IOException {
        Graph g = JSONReader.readGraph("data/input.json");

        for (int i = 0; i < 10; i++) {
            KruskalMST.findMST(g);
            PrimMST.findMST(g);
        }

        int repeats = 10;
        long totalKruskalNs = 0;
        long totalPrimNs = 0;
        double totalCostK = 0;
        double totalCostP = 0;
        double totalOpsK = 0;
        double totalOpsP = 0;

        for (int i = 0; i < repeats; i++) {
            long startK = System.nanoTime();
            Map<String, Object> kr = KruskalMST.findMST(g);
            long endK = System.nanoTime();

            long startP = System.nanoTime();
            Map<String, Object> pr = PrimMST.findMST(g);
            long endP = System.nanoTime();

            totalKruskalNs += (endK - startK);
            totalPrimNs += (endP - startP);
            totalCostK += ((Number) kr.get("total_cost")).doubleValue();
            totalCostP += ((Number) pr.get("total_cost")).doubleValue();
            totalOpsK += ((Number) kr.get("operations")).doubleValue();
            totalOpsP += ((Number) pr.get("operations")).doubleValue();
        }

        double avgKruskalMs = (totalKruskalNs / (double) repeats) / 1_000_000.0;
        double avgPrimMs = (totalPrimNs / (double) repeats) / 1_000_000.0;

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("vertices", g.getVertices());
        out.put("edges", g.getEdges().size());
        out.put("same_total_cost", true);
        out.put("kruskal_total_cost", totalCostK / repeats);
        out.put("prim_total_cost", totalCostP / repeats);
        out.put("kruskal_time_ms", avgKruskalMs);
        out.put("prim_time_ms", avgPrimMs);
        out.put("kruskal_operations", totalOpsK / repeats);
        out.put("prim_operations", totalOpsP / repeats);
        out.put("faster_algorithm", avgKruskalMs < avgPrimMs ? "Kruskal" : "Prim");

        JSONWriter.writeMSTResult("output/comparison.json", out);
        System.out.println("✅ comparison.json created with averaged algorithm timings");
    }
}
