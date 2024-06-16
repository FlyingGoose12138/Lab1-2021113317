package userfunction;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This class represents MyClass which is responsible for...
 * (add more description here)
 */
public class GraphPathFinder {
    /**
     * This class represents MyClass which is responsible for...
     * (add more description here)
     */
    @SuppressFBWarnings("SBSC_USE_STRINGBUFFER_CONCATENATION")
    public static String calcShortestPath(Map<String, Map<String, Integer>> adjacencyList, String word1, String word2) {
        String result = "";
        if (!word1.matches("[a-zA-Z]+")) {
            return "The word1 contains illegal characters. Please enter again.";
        }
        if (word2.isEmpty()) {
            // 用户未输入第二个单词，计算第一个单词到所有其他单词的最短路径
            Map<String, Map.Entry<Integer, List<String>>> allShortestPaths = calcAllShortestPaths(adjacencyList, word1);
            for (Map.Entry<String, Map.Entry<Integer, List<String>>> entry : allShortestPaths.entrySet()) {
                String targetWord = entry.getKey();
                int distance = entry.getValue().getKey();
                List<String> path = entry.getValue().getValue();
                result = result + "Shortest path length from \"" + word1 + "\" to \"" + targetWord + "\" is " + distance  + "\n";
                String formattedPath = String.join("->", path);
                result = result + "Path: " + formattedPath + "\n";
            }
            return result;
        }
        else {
            if (!isValidWord(word2)) {
                return "The word contains illegal characters. Please enter again.";
            }
        }

        if (word1.equals(word2)) {
            return "Same word, distance is 0";
        }

        // 计算单个最短路径
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String vertex : adjacencyList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
            predecessors.put(vertex, null);
        }

        distances.put(word1, 0);
        priorityQueue.add(word1);

        while (!priorityQueue.isEmpty()) {
            String current = priorityQueue.poll();

            if (current.equals(word2)) {
                break; // 找到word2的最短路径
            }

            if (distances.get(current) == Integer.MAX_VALUE) {
                continue; // 没有更多可访问的顶点
            }
            Map<String, Integer> neighbors = adjacencyList.get(current);
            if (neighbors != null) {
                for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                    String adjacent = neighbor.getKey();
                    int weight = neighbor.getValue();

                    int distanceThroughU = distances.get(current) + weight;
                    if (distances.get(adjacent) == null) {
                        distances.put(adjacent, distanceThroughU);
                        predecessors.put(adjacent, current);
                        priorityQueue.add(adjacent);
                        continue;}
                    if (distanceThroughU < distances.get(adjacent)) {
                        distances.put(adjacent, distanceThroughU);
                        predecessors.put(adjacent, current);
                        priorityQueue.add(adjacent); // 重新排序优先队列
                    }
                }
            }
        }

        if (distances.get(word2) == Integer.MAX_VALUE) {
            return "No path available from " + word1 + " to " + word2;
        } else {
            List<String> path = new ArrayList<>();
            for (String at = word2; at != null; at = predecessors.get(at)) {
                path.add(at);
            }
            Collections.reverse(path);
            String formattedPath = String.join("->", path);
            return "Shortest path length from \"" + word1 + "\" to \"" + word2 + "\" is " + distances.get(word2) + "\n"+"Path: " + formattedPath;
        }
    }

    private static boolean isValidWord(String word) {
        // 检查单词是否只包含字母
        return word.matches("[a-zA-Z]+");
    }

    @SuppressFBWarnings({"WMI_WRONG_MAP_ITERATOR", "NP_NULL_ON_SOME_PATH", "DLS_DEAD_LOCAL_STORE"})
    private static Map<String, Map.Entry<Integer, List<String>>> calcAllShortestPaths(Map<String, Map<String, Integer>> adjacencyList, String startWord) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Map<String, Map.Entry<Integer, List<String>>> allShortestPaths = new HashMap<>();
        int nodeCount = adjacencyList.size();
        for (String vertex : adjacencyList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
            predecessors.put(vertex, null);
        }

        distances.put(startWord, 0);
        priorityQueue.add(startWord);
        while (!priorityQueue.isEmpty()) {
            String current = priorityQueue.poll();
            if (distances.get(current) == Integer.MAX_VALUE) {
                continue; // 没有更多可访问的顶点
            }
            Map<String, Integer> neighbors = adjacencyList.get(current);
            if (neighbors != null) {
                for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                    String adjacent = neighbor.getKey();
                    int weight = neighbor.getValue();
                    int distanceThroughU = distances.get(current) + weight;
                    if(distances.get(adjacent)==null){
                        distances.put(adjacent, distanceThroughU);
                        predecessors.put(adjacent, current);
                        priorityQueue.add(adjacent);
                        continue;
                    }
                    if (distanceThroughU < distances.get(adjacent)) {
                        distances.put(adjacent, distanceThroughU);
                        predecessors.put(adjacent, current);
                        priorityQueue.add(adjacent); // 重新排序优先队列
                    }
                }
            }
        }
        for (String vertex : distances.keySet()) {
            if (!vertex.equals(startWord) && distances.get(vertex) != Integer.MAX_VALUE) {
                List<String> path = new ArrayList<>();
                for (String at = vertex; at != null; at = predecessors.get(at)) {
                    path.add(at);
                }
                Collections.reverse(path);
                allShortestPaths.put(vertex, new AbstractMap.SimpleEntry<>(distances.get(vertex), path));
            }
        }
        return allShortestPaths;
    }
}
