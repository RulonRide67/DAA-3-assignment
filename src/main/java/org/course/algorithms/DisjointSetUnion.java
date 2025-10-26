package org.course.algorithms;

public class DisjointSetUnion {
    private final int[] parent;
    private final int[] rank;
    private int operations;

    public DisjointSetUnion(int size) {
        parent = new int[size];
        rank = new int[size];
        operations = 0;
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        operations++;
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // path compression
        }
        return parent[x];
    }

    public boolean union(int x, int y) {
        operations++;
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return false;

        // union by rank
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }

    public int getOperations() {
        return operations;
    }
}
