package softproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents MyClass which is responsible for...
 * (add more description here)
 */
public class TextAnalyzer {
    /**
     * This class represents MyClass which is responsible for...
     * (add more description here)
     */
    public static Map<String, Map<String, Integer>> analyzeText(String filePath) {
        Map<String, Map<String, Integer>> adjacencyList = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            StringBuilder contentBuilder = new StringBuilder();
            String line;

            // 读取文件的所有行，并将其连接到一个字符串中，换行符替换为空格
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append(" ");
            }

            // 获取完整的内容并进行处理
            String content = contentBuilder.toString();
            content = content.replaceAll("[^a-zA-Z\\s]", "").toLowerCase().trim();
            String[] words = content.split("\\s+");

            // 更新邻接表
            for (int i = 0; i < words.length - 1; i++) {
                String source = words[i];
                String destination = words[i + 1];

                if (!adjacencyList.containsKey(source)) {
                    adjacencyList.put(source, new HashMap<>());
                }
                Map<String, Integer> neighbors = adjacencyList.get(source);
                neighbors.put(destination, neighbors.getOrDefault(destination, 0) + 1);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        return adjacencyList;
    }
}
