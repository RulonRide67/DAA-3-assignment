package org.course.util;

import com.google.gson.*;
import org.course.model.Graph;

import java.io.FileReader;
import java.io.IOException;

public class JSONReader {

    public static Graph readGraph(String filePath) throws IOException {
        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(new FileReader(filePath)).getAsJsonObject();

        int vertices = root.get("vertices").getAsInt();
        Graph graph = new Graph(vertices);

        JsonArray edgesArray = root.getAsJsonArray("edges");
        for (JsonElement elem : edgesArray) {
            JsonObject edge = elem.getAsJsonObject();
            int src = edge.get("src").getAsInt();
            int dest = edge.get("dest").getAsInt();
            int weight = edge.get("weight").getAsInt();
            graph.addEdge(src, dest, weight);
        }

        return graph;
    }
}
