package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Minimal CSV reader for data-driven tests — no external library needed.
 * Assumes the first row is a header row and skips it.
 */
public class CsvDataReader {

    public static Object[][] readCsv(String filePath) {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                if (firstLine) {
                    firstLine = false;
                    continue; // skip header
                }
                rows.add(line.split(",", -1));
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read test data file: " + filePath, e);
        }

        Object[][] data = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }
        return data;
    }
}
