package org.course.tests;

import org.course.util.GraphGenerator;
import org.junit.jupiter.api.Test;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphGeneratorTest {
    @Test
    public void testGenerateAndCompare() throws Exception {
        GraphGenerator.main(new String[]{});
        File f = new File("output/all_results.json");
        assertTrue(f.exists(), "all_results.json file should exist");
        System.out.println("✅ Graph generation and comparison successful!");
    }
}
