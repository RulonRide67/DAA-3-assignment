package org.course.util;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ResultsExporter {

    public static void main(String[] args) {
        try {
            String json = Files.readString(Paths.get("output/all_results.json"));
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            JsonArray results = root.getAsJsonArray("results");

            StringBuilder md = new StringBuilder();

            md.append("# 📊 MST Performance Summary\n\n");
            md.append("| Graph Size | Vertices | Edges | Kruskal Time (ms) | Prim Time (ms) | Kruskal Cost | Prim Cost | Kruskal Ops | Prim Ops | Faster Algorithm |\n");
            md.append("|-------------|-----------|--------|--------------------|----------------|---------------|-------------|--------------|------------|------------------|\n");

            for (int i = 0; i < results.size(); i++) {
                JsonObject obj = results.get(i).getAsJsonObject();

                int vertices = obj.get("vertices").getAsInt();
                int edges = obj.get("edges").getAsInt();

                double kruskalTime = obj.get("kruskal_time_ms").getAsDouble();
                double primTime = obj.get("prim_time_ms").getAsDouble();
                double kruskalCost = obj.get("kruskal_cost").getAsDouble();
                double primCost = obj.get("prim_cost").getAsDouble();
                double kruskalOps = obj.get("kruskal_operations").getAsDouble();
                double primOps = obj.get("prim_operations").getAsDouble();

                String faster = obj.has("faster_algorithm")
                        ? obj.get("faster_algorithm").getAsString()
                        : (kruskalTime < primTime ? "Kruskal" : "Prim");

                String sizeLabel;
                if (vertices <= 8) sizeLabel = "Small";
                else if (vertices <= 20) sizeLabel = "Medium";
                else sizeLabel = "Large";

                md.append(String.format(Locale.US,
                        "| %s | %d | %d | %.3f | %.3f | %.1f | %.1f | %.1f | %.1f | %s |\n",
                        sizeLabel, vertices, edges,
                        kruskalTime, primTime,
                        kruskalCost, primCost,
                        kruskalOps, primOps,
                        faster));
            }

            Path outputPath = Paths.get("output/results_table.md");
            Files.writeString(outputPath, md.toString());

            System.out.println("✅ Results table successfully exported to output/results_table.md");

        } catch (IOException e) {
            System.err.println("❌ Error while generating Markdown table: " + e.getMessage());
        }
    }
}
