package org.course.algorithms;

import org.course.model.Graph;
import org.course.model.Edge;

import java.util.*;

public class KruskalMST {

    static class DisjointSet {
        int[] parent, rank;

        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (x != parent[x])
                parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY])
                    parent[rootX] = rootY;
                else if (rank[rootX] > rank[rootY])
                    parent[rootY] = rootX;
                else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }

    public static Map<String, Object> findMST(Graph graph) {
        long start = System.nanoTime();

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);

        DisjointSet ds = new DisjointSet(graph.getVertices());
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        int operations = 0;

        for (Edge edge : edges) {
            operations++;
            int rootU = ds.find(edge.getSrc());
            int rootV = ds.find(edge.getDest());
            if (rootU != rootV) {
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                ds.union(rootU, rootV);
            }
            if (mstEdges.size() == graph.getVertices() - 1)
                break;
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("algorithm", "Kruskal");
        result.put("vertices", graph.getVertices());
        result.put("edges_in_mst", mstEdges.size());
        result.put("total_cost", totalCost);
        result.put("operations", operations);
        result.put("execution_time_ms", timeMs);

        List<Map<String, Object>> mstEdgeList = new ArrayList<>();
        for (Edge e : mstEdges) {
            Map<String, Object> edgeData = new LinkedHashMap<>();
            edgeData.put("src", e.getSrc());
            edgeData.put("dest", e.getDest());
            edgeData.put("weight", e.getWeight());
            mstEdgeList.add(edgeData);
        }
        result.put("mst_edges", mstEdgeList);

        return result;
    }
}
