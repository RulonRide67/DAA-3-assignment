package org.course.algorithms;

import org.course.model.Edge;
import org.course.model.Graph;
import java.util.*;

public class KruskalMST {

    public static Map<String, Object> findMST(Graph graph) {
        long startTime = System.nanoTime();

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges); // sort by weight

        DisjointSetUnion dsu = new DisjointSetUnion(graph.getVertices());
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        for (Edge edge : edges) {
            if (dsu.union(edge.src, edge.dest)) {
                mstEdges.add(edge);
                totalCost += edge.weight;
            }
        }

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("algorithm", "Kruskal");
        result.put("vertices", graph.getVertices());
        result.put("edges_in_mst", mstEdges.size());
        result.put("total_cost", totalCost);
        result.put("operations", dsu.getOperations());
        result.put("execution_time_ms", executionTimeMs);

        List<Map<String, Object>> edgeList = new ArrayList<>();
        for (Edge e : mstEdges) {
            Map<String, Object> edgeMap = new LinkedHashMap<>();
            edgeMap.put("src", e.src);
            edgeMap.put("dest", e.dest);
            edgeMap.put("weight", e.weight);
            edgeList.add(edgeMap);
        }
        result.put("mst_edges", edgeList);

        return result;
    }
}
