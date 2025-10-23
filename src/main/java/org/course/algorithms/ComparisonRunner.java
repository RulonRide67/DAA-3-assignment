package org.course.algorithms;

import org.course.model.Graph;
import org.course.util.JSONReader;
import org.course.util.JSONWriter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ComparisonRunner {

    public static void main(String[] args) {
        try {
            Graph g = JSONReader.readGraph("data/input.json");

            Map<String, Object> kruskalResult = KruskalMST.findMST(g);
            Map<String, Object> primResult = PrimMST.findMST(g);

            int costKruskal = (int) kruskalResult.get("total_cost");
            int costPrim = (int) primResult.get("total_cost");

            double timeKruskal = (double) kruskalResult.get("execution_time_ms");
            double timePrim = (double) primResult.get("execution_time_ms");

            int opsKruskal = (int) kruskalResult.get("operations");
            int opsPrim = (int) primResult.get("operations");

            boolean sameCost = costKruskal == costPrim;
            String fasterAlgorithm = (timeKruskal < timePrim) ? "Kruskal" :
                    (timePrim < timeKruskal) ? "Prim" : "Equal";

            Map<String, Object> comparison = new LinkedHashMap<>();
            comparison.put("vertices", g.getVertices());
            comparison.put("edges", g.getEdges().size());
            comparison.put("same_total_cost", sameCost);
            comparison.put("kruskal_total_cost", costKruskal);
            comparison.put("prim_total_cost", costPrim);
            comparison.put("kruskal_time_ms", timeKruskal);
            comparison.put("prim_time_ms", timePrim);
            comparison.put("kruskal_operations", opsKruskal);
            comparison.put("prim_operations", opsPrim);
            comparison.put("faster_algorithm", fasterAlgorithm);

            JSONWriter.writeMSTResult("output/comparison.json", comparison);

            System.out.println("✅ Comparison completed. Results saved to output/comparison.json");
        } catch (IOException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
}
