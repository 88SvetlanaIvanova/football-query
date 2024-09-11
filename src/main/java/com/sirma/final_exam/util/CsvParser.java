package com.sirma.final_exam.util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CsvParser {
    public static List<String[]> loadData(String filePath) throws IOException{
        List<String[]> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] val = line.split(",");
                records.add(val);
            }
        }
        return records;
    }
}
