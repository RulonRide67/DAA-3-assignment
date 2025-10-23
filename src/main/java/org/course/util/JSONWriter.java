package org.course.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.course.model.Edge;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JSONWriter {

    public static void writeMSTResult(String filePath, Map<String, Object> result) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(result, writer);
        }
    }
}
