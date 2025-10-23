package org.course.algorithms;

import org.course.model.Edge;
import org.course.model.Graph;
import java.util.*;

public class PrimMST {

    // Вспомогательный класс для хранения рёбер в куче
    static class Node implements Comparable<Node> {
        int vertex;
        int weight;
        int parent;

        Node(int vertex, int weight, int parent) {
            this.vertex = vertex;
            this.weight = weight;
            this.parent = parent;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    public static Map<String, Object> findMST(Graph graph) {
        long startTime = System.nanoTime();

        int V = graph.getVertices();
        List<Edge> allEdges = graph.getEdges();

        // создаём список смежности
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        for (Edge e : allEdges) {
            adj.get(e.src).add(e);
            adj.get(e.dest).add(new Edge(e.dest, e.src, e.weight));
        }

        boolean[] inMST = new boolean[V];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        List<Edge> mstEdges = new ArrayList<>();

        int totalCost = 0;
        int operations = 0;

        // начинаем с вершины 0
        pq.offer(new Node(0, 0, -1));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            operations++;
            if (inMST[current.vertex]) continue;

            inMST[current.vertex] = true;
            if (current.parent != -1) {
                mstEdges.add(new Edge(current.parent, current.vertex, current.weight));
                totalCost += current.weight;
            }

            for (Edge edge : adj.get(current.vertex)) {
                if (!inMST[edge.dest]) {
                    pq.offer(new Node(edge.dest, edge.weight, current.vertex));
                }
            }
        }

        long endTime = System.nanoTime();
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("algorithm", "Prim");
        result.put("vertices", V);
        result.put("edges_in_mst", mstEdges.size());
        result.put("total_cost", totalCost);
        result.put("operations", operations);
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
