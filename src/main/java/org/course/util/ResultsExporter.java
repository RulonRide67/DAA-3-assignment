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
            md.append("| Graph Size | Vertices | Edges | Kruskal Time (ms) | Prim Time (ms) | Faster Algorithm |\n");
            md.append("|-------------|-----------|--------|--------------------|----------------|------------------|\n");

            for (int i = 0; i < results.size(); i++) {
                JsonObject obj = results.get(i).getAsJsonObject();
                int v = obj.get("vertices").getAsInt();
                int e = obj.get("edges").getAsInt();
                double kruskal = obj.get("kruskal_time_ms").getAsDouble();
                double prim = obj.get("prim_time_ms").getAsDouble();

                String sizeLabel = (i == 0) ? "Small" : (i == 1) ? "Medium" : "Large";
                String faster = (kruskal < prim) ? "Kruskal" : (prim < kruskal) ? "Prim" : "Equal";

                md.append(String.format("| %s | %d | %d | %.3f | %.3f | %s |\n",
                        sizeLabel, v, e, kruskal, prim, faster));
            }

            Files.writeString(Paths.get("output/results_table.md"), md.toString());
            System.out.println("✅ Markdown table saved to output/results_table.md");

        } catch (IOException e) {
            System.err.println("❌ Error reading JSON file: " + e.getMessage());
        }
    }
}
