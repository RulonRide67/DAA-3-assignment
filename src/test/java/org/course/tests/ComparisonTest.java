package org.course.tests;

import org.course.algorithms.ComparisonRunner;
import org.junit.jupiter.api.Test;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class ComparisonTest {

    @Test
    public void testComparisonOutput() {
        ComparisonRunner.main(new String[]{});
        File f = new File("output/comparison.json");
        assertTrue(f.exists(), "comparison.json file should exist");
        System.out.println("✅ comparison.json created successfully");
    }
}
