package SoftProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextAnalyzer {

    public static Map<String, Map<String, Integer>> analyzeText(String filePath) {
        Map<String, Map<String, Integer>> adjacencyList = new HashMap<>();
        StringBuilder allText = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 删除特殊字符，并转换为小写
                line = line.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
                allText.append(line).append(" ");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
            return adjacencyList;
        }

        // 分割整个文本为单词数组
        String[] words = allText.toString().trim().split("\\s+");
        for (int i = 0; i < words.length - 1; i++) {
            String source = words[i];
            String destination = words[i + 1];

            // 更新邻接表
            adjacencyList.computeIfAbsent(source, k -> new HashMap<>())
                    .merge(destination, 1, Integer::sum);
        }

        return adjacencyList;
    }
}
