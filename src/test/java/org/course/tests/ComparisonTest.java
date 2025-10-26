package org.course.tests;

import org.course.util.ComparisonRunner;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ComparisonTest {

    private org.course.util.ComparisonRunner ComparisonRunner;

    @Test
    public void testComparisonOutput() {
        try {
            ComparisonRunner.main(new String[]{});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File f = new File("output/comparison.json");
        assertTrue(f.exists(), "comparison.json file should exist");
        System.out.println("✅ comparison.json created successfully");
    }
}
