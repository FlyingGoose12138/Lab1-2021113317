package SoftProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextAnalyzer {
    public static Map<String, Map<String, Integer>> analyzeText(String filePath) {
        Map<String, Map<String, Integer>> adjacencyList = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 删除特殊字符
                line = line.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
                String[] words = line.split("\\s+");
                for (int i = 0; i < words.length - 1; i++) {
                    String source = words[i];
                    String destination = words[i + 1];

                    // 更新邻接表
                    if (!adjacencyList.containsKey(source)) {
                        adjacencyList.put(source, new HashMap<>());
                    }
                    Map<String, Integer> neighbors = adjacencyList.get(source);
                    neighbors.put(destination, neighbors.getOrDefault(destination, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        return adjacencyList;
    }
}

