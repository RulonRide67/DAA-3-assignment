package org.course.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    public final int V;
    public final List<Edge> edges;

    public Graph(int V) {
        this.V = V;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int u, int v, int w) {
        edges.add(new Edge(u, v, w));
    }
}
