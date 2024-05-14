package UserFunction;

import java.util.*;

public class GraphPathFinder {

    public static void calcShortestPath(Map<String, Map<String, Integer>> adjacencyList) {
        Scanner scanner = new Scanner(System.in);
        String word1, word2;

        // 获取并检查第一个单词
        do {
            System.out.print("Please enter the first word: ");
            word1 = scanner.next().toLowerCase();
            if (isValidWord(word1)) {
                System.out.println("The word contains illegal characters. Please enter again.");
            }
        } while (isValidWord(word1));

// Get and check the second word
        do {
            System.out.print("Please enter the second word: ");
            word2 = scanner.next().toLowerCase();
            if (isValidWord(word2)) {
                System.out.println("The word contains illegal characters. Please enter again.");
            }
        } while (isValidWord(word2));

        if (word1.equals(word2)) {
            System.out.println("Same word, distance is 0");
            return;
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String vertex : adjacencyList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
            predecessors.put(vertex, null);
            priorityQueue.add(vertex);
        }

        distances.put(word1, 0);

        while (!priorityQueue.isEmpty()) {
            String current = priorityQueue.poll();

            if (current.equals(word2)) {
                break; // Found the shortest path to word2
            }

            if (distances.get(current) == Integer.MAX_VALUE) {
                continue; // No more accessible vertices
            }

            for (Map.Entry<String, Integer> neighbor : adjacencyList.get(current).entrySet()) {
                String adjacent = neighbor.getKey();
                int weight = neighbor.getValue();
                int distanceThroughU = distances.get(current) + weight;

                if (distanceThroughU < distances.get(adjacent)) {
                    distances.put(adjacent, distanceThroughU);
                    predecessors.put(adjacent, current);
                    priorityQueue.add(adjacent); // Reorder the priority queue
                }
            }
        }

        if (distances.get(word2) == Integer.MAX_VALUE) {
            System.out.println("No path available from " + word1 + " to " + word2);
        } else {
            System.out.println("Shortest path length from " + word1 + " to " + word2 + " is " + distances.get(word2));
            List<String> path = new ArrayList<>();
            for (String at = word2; at != null; at = predecessors.get(at)) {
                path.add(at);
            }
            Collections.reverse(path);
            System.out.println("Path: " + path);
        }
    }

    private static boolean isValidWord(String word) {
        // 检查单词是否只包含字母
        return !word.matches("[a-zA-Z]+");
    }
}
