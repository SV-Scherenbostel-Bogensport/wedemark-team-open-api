package dev.laubfrosch.bogensport.wedemarkteamopenapi.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SqlFileReader {

    public static String getSqlQueryFromFile(String fileName) throws IOException {

        try (var reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder combinedLine = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                combinedLine.append(line).append(" ");
            }

            return combinedLine.toString();
        }
    }
}
