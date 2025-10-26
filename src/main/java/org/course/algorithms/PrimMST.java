package org.course.algorithms;

import org.course.model.Graph;
import org.course.model.Edge;

import java.util.*;

public class PrimMST {

    static class Node implements Comparable<Node> {
        int vertex, weight;
        Node(int v, int w) { vertex = v; weight = w; }
        public int compareTo(Node o) { return this.weight - o.weight; }
    }

    public static Map<String, Object> findMST(Graph graph) {
        long start = System.nanoTime();

        int V = graph.getVertices();
        boolean[] visited = new boolean[V];
        int[] key = new int[V];
        int[] parent = new int[V];
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        key[0] = 0;

        Map<Integer, List<Edge>> adj = new HashMap<>();
        for (Edge e : graph.getEdges()) {
            adj.computeIfAbsent(e.getSrc(), k -> new ArrayList<>()).add(e);
            adj.computeIfAbsent(e.getDest(), k -> new ArrayList<>())
                    .add(new Edge(e.getDest(), e.getSrc(), e.getWeight()));
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0, 0));

        int operations = 0;

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int u = node.vertex;
            if (visited[u]) continue;
            visited[u] = true;
            operations++;

            if (!adj.containsKey(u)) continue;
            for (Edge e : adj.get(u)) {
                int v = e.getDest();
                int w = e.getWeight();
                if (!visited[v] && w < key[v]) {
                    key[v] = w;
                    parent[v] = u;
                    pq.add(new Node(v, key[v]));
                }
            }
        }

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        for (int i = 1; i < V; i++) {
            if (parent[i] != -1) {
                Edge e = new Edge(parent[i], i, key[i]);
                mstEdges.add(e);
                totalCost += key[i];
            }
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("algorithm", "Prim");
        result.put("vertices", V);
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
