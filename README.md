# 🧮 Minimum Spanning Tree Algorithms: Kruskal vs Prim

## 📌 Overview
This project implements and compares two classical **Minimum Spanning Tree (MST)** algorithms:
- **Kruskal’s algorithm** (based on edge sorting and union–find)
- **Prim’s algorithm** (based on vertex expansion using a priority queue)

All code is implemented in Java using Maven, and the analysis is documented here.

---

## 📊 1. Input Summary & Algorithm Results

### Input Data
Graphs are randomly generated with increasing sizes:

| Graph | Vertices | Edges | Density | Max Weight |
|--------|-----------|--------|----------|-------------|
| Small | 6 | 8 | Sparse | 20 |
| Medium | 12 | 25 | Moderate | 50 |
| Large | 25 | 60 | Dense | 100 |

### Experimental Results

| Graph Size | Vertices | Edges | Kruskal Time (ms) | Prim Time (ms) | Kruskal Cost | Prim Cost | Kruskal Ops | Prim Ops | Faster Algorithm |
|-------------|-----------|--------|--------------------|----------------|---------------|-------------|--------------|------------|------------------|
| Small | 6 | 8 | 0.069 | 0.121 | 172.0 | 201.9 | 32.5 | 9.0 | Kruskal |
| Medium | 12 | 25 | 0.149 | 0.449 | 279.9 | 300.7 | 112.7 | 26.0 | Kruskal |
| Large | 25 | 60 | 0.385 | 0.465 | 550.3 | 592.1 | 279.6 | 61.0 | Kruskal |

The data was generated using `GraphGenerator.java`, averaged over 7 runs, and exported via `ResultsExporter.java`.

---

## ⚙️ 2. Theoretical and Practical Comparison

### 🧠 Theoretical Complexity

| Algorithm | Time Complexity | Data Structure | Typical Use Case |
|------------|----------------|----------------|------------------|
| **Kruskal** | O(E log E) | Union–Find (Disjoint Set) | Sparse graphs, edge lists |
| **Prim** | O(E log V) | PriorityQueue (Min-Heap) | Dense graphs, adjacency list |

### 🧪 Observations in Practice
- For **small and medium sparse graphs**, Kruskal consistently outperforms Prim.  
- As graph density increases, the gap narrows, since Prim benefits from adjacency-based expansion.  
- In all cases, both algorithms produced identical total MST cost (validation success).  
- The difference in runtime is due to:
  - Kruskal sorts edges once and performs efficient unions.
  - Prim relies on repeated heap operations (O(log V) per edge).

### 🧩 Example: Small Graph (V=5)

#### Input Graph
![Input Graph](images/graph_input.png)

#### MST Found
![MST Result](images/graph_mst.png)

Example MST edges (from JSON output):
```json
[
  {"src": 2, "dest": 4, "weight": 2},
  {"src": 1, "dest": 2, "weight": 3},
  {"src": 0, "dest": 2, "weight": 5},
  {"src": 1, "dest": 3, "weight": 7}
]
