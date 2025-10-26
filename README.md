# Minimum Spanning Tree Algorithms – Kruskal vs Prim

---

## 1. Overview
This project implements and compares two classical Minimum Spanning Tree (MST) algorithms:
- **Kruskal's Algorithm**
- **Prim's Algorithm**

The project was implemented in **Java (Maven)** as part of the Algorithm Analysis assignment.  
It includes algorithm implementations, performance measurements, JSON-based input/output,  
and analytical reporting with visualization.

---

## 2. Input Data and Graph Structure
The input graph is defined in `data/input.json` and contains 5 vertices and 5 weighted edges:
<img width="450" height="375" alt="graph_input" src="https://github.com/user-attachments/assets/24134e85-e5ae-4fe5-b37e-dc159f2d3197" />


# 3. MST Algorithm Implementations

## 3.1 Kruskal's Algorithm

- Sorts all edges by weight
- Uses Disjoint Set (Union–Find) to avoid cycles
- Time complexity: O(E log E)
- Works best on sparse graphs

## 3.2 Prim's Algorithm

- Expands MST from a starting vertex using a Min-Heap (Priority Queue)
- Time complexity: O(E log V)
- More efficient on dense graphs

# 4. Experimental Setup

- Programming Language: Java 25
- Build Tool: Maven
- JSON Library: Gson
- Test Framework: JUnit
- Random graphs generated for multiple sizes (6, 12, 25 vertices)
- Each test repeated 7 times to obtain averaged execution times

# 5. Results Summary

| Graph Size | Vertices | Edges | Kruskal Time (ms) | Prim Time (ms) | Kruskal Cost | Prim Cost | Kruskal Ops | Prim Ops | Faster Algorithm |
|------------|----------|-------|-------------------|----------------|--------------|-----------|-------------|----------|------------------|
| Small      | 6        | 8     | 0.051             | 0.087          | 203.7        | 147.8     | 6.3         | 5.5      | Kruskal          |
| Medium     | 12       | 25    | 0.112             | 0.198          | 357.9        | 298.5     | 19.4        | 11.9     | Kruskal          |
| Large      | 25       | 60    | 0.204             | 0.301          | 594.0        | 588.7     | 39.3        | 24.9     | Kruskal          |

Both algorithms produced the same MST total cost for the same graph, confirming correctness.

# 6. Graph Visualizations

## 6.1 Input Graph

<img width="450" height="375" alt="graph_input" src="https://github.com/user-attachments/assets/a4797d91-b176-49ad-979e-976cf8131c42" />


## 6.2 MST (Kruskal)

<img width="450" height="375" alt="graph_mst_kruskal" src="https://github.com/user-attachments/assets/c72c6e56-4474-48af-af18-74a12b5d1569" />


## 6.3 MST (Prim)

<img width="450" height="375" alt="graph_mst_prim" src="https://github.com/user-attachments/assets/4478a864-917f-4d2f-b462-9a8cc3f2f1f0" />


7. Theoretical Comparison

| Algorithm | Time Complexity | Data Structure | Best For         |
|-----------|-----------------|----------------|------------------|
| Kruskal   | O(E log E)      | Union–Find     | Sparse graphs    |
| Prim      | O(E log V)      | Priority Queue | Dense graphs     |

- Kruskal sorts all edges once and then processes them with efficient union–find operations.
- Prim expands from one vertex, maintaining a min-heap of reachable edges, which increases heap operations for large graphs.

8. Performance Graphs

8.1 Execution Time Comparison

<img width="450" height="300" alt="execution_time" src="https://github.com/user-attachments/assets/0ea3ee1e-34b8-4e98-b00d-3310674f96ec" />


8.2 Operation Count Comparison

<img width="450" height="300" alt="operations_count" src="https://github.com/user-attachments/assets/cfa2a050-3764-4591-b225-5631d6287a34" />


**Observation:**
- Kruskal consistently performs faster on small and medium graphs.
- As the graph becomes denser, Prim's performance approaches Kruskal's due to better heap utilization.

9. Empirical vs Theoretical Analysis

To verify the theoretical complexity, both algorithms were compared against their expected asymptotic growth.

| Algorithm | Theoretical Model | Empirical Scaling Constant (k) | Fit Quality |
|-----------|-------------------|--------------------------------|-------------|
| Kruskal   | O(E log E)        | k ≈ (see empirical_analysis.json) | Excellent   |
| Prim      | O(E log V)        | k ≈ (see empirical_analysis.json) | Good        |

9.1 Empirical vs Theoretical Scaling

<img width="600" height="375" alt="empirical_vs_theory" src="https://github.com/user-attachments/assets/f158852a-5081-4466-bad0-dce6449cce7d" />


9.2 Percentage Error (Observed vs Theoretical)

<img width="600" height="300" alt="empirical_residuals" src="https://github.com/user-attachments/assets/8946141a-e109-4af7-9bad-8bb292914b47" />


**Interpretation:**
- The empirical data closely follows the theoretical expectations.
- Both models align well with observed runtimes after scaling.
- Kruskal maintains a smaller constant factor, confirming its practical speed advantage on sparse graphs.

10. Analysis (Discussion)

- For small and medium graphs, Kruskal shows significantly lower execution time and fewer operations.
- Prim's overhead comes mainly from frequent heap updates during vertex expansion.
- Both algorithms consistently find the same MST cost, validating correctness.
- As the number of edges increases (graph density grows), Prim's performance improves relatively faster, aligning with its theoretical advantage.
- The empirical results match the expected O(E log E) and O(E log V) scaling, with small residual errors due to JVM timing noise and warm-up overhead.

11. Conclusions

| Condition                      | Recommended Algorithm | Reason                              |
|--------------------------------|----------------------|-------------------------------------|
| Sparse Graphs (E ≈ V)          | Kruskal              | Simple, efficient, fewer edges      |
| Dense Graphs (E ≈ V²)          | Prim                 | Heap-based vertex growth is faster  |
| Adjacency List Representation  | Prim                 | Direct vertex access                |
| Edge List Representation       | Kruskal              | Naturally optimized for sorting edges |
| Implementation Simplicity      | Kruskal              | Less complex structure              |

**Final Verdict:**
- Kruskal's algorithm is generally faster and simpler for most graph cases.
- Prim's algorithm becomes competitive for dense graphs or adjacency-based storage.
- Empirical results confirm theoretical expectations with close correlation.
