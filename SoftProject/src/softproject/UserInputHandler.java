package softproject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import userfunction.GraphPathFinder;
import userfunction.GraphWalker;
import userfunction.TextGenerator;


/**
 * This class represents MyClass which is responsible for...
 * (add more description here)
 */
public class UserInputHandler {
    /**
     * This class represents MyClass which is responsible for...
     * (add more description here)
     */
    public static void handleUserInput(Map<String, Map<String, Integer>> adjacencyList) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        int choice;

        do {
            // 打印选项列表
            System.out.println("Please select an operation: ");
            System.out.println("1. Query bridge words.");
            System.out.println("2. Show directed graph.");
            System.out.println("3. Generate new text based on bridge words.");
            System.out.println("4. Calculate the shortest path between two words.");
            System.out.println("5. Random walk.");
            System.out.println("6. Exit the program.");

            // 获取用户输入
            System.out.print("Please enter the option number: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // 查询连接词
                    System.out.print("Enter the first word: ");
                    String word1 = scanner.next();
                    System.out.print("Enter the second word: ");
                    String word2 = scanner.next();
                    String result = queryBridgeWords(adjacencyList, word1, word2);
                    System.out.println(result);
                    break;
                case 2:
                    GraphPrinter.printAdjacencyList(adjacencyList);
                    break;
                case 3:
                    // 插入连接词
                    TextGenerator.generateTextWithBridgeWords(adjacencyList);
                    break;
                case 4:
                    // 计算最短路径
                    System.out.print("Enter the first word: ");
                    String firstWord = scanner.next();
                    System.out.print("Please enter the second word (or press Enter to skip): ");
                    scanner.nextLine();
                    String endWord = scanner.nextLine();
                    String calResult = GraphPathFinder.calcShortestPath(adjacencyList, firstWord, endWord);
                    System.out.println(calResult);
                    break;
                case 5:
                    GraphWalker walker = new GraphWalker(adjacencyList);

                    try {
                        walker.randomWalk();
                        System.out.println("\nRandom walk completed and written to file.");
                    } catch (IOException e) {
                        System.out.println("Error writing to file: " + e.getMessage());
                    }
                    break;

                case 6:
                    // 退出程序
                    System.out.println("The program has exited.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        } while (true);

    }

    public static String queryBridgeWords(Map<String, Map<String, Integer>> adjacencyList, String word1, String word2) {
        // 检查单词是否合法
        if (!word1.matches("[a-zA-Z]+")) {
            return "The word1 contains illegal characters. Please enter again.";
        } else if (!word2.matches("[a-zA-Z]+")) {
            return "The word2 contains illegal characters. Please enter again.";
        }

        // 检查单词是否相同
        if (word1.equals(word2)) {
            return "Word1 and word2 can’t be same!";
        }

        // 检查邻接表输入
        if (adjacencyList == null) {
            return "Lack of adjacencyList!";
        } else if (adjacencyList.isEmpty()) {
            return "Lack of adjacencyList!";
        }

        // 检查图中单词是否存在
        if (!adjacencyList.containsKey(word1)) {
            return "No \"" + word1 + "\" in the graph!";
        } else if (!adjacencyList.containsKey(word2)) {
            return "No \"" + word2 + "\" in the graph!";
        }

        // 查找桥接词
        boolean foundBridge = false;
        Set<String> bridgeWords = new HashSet<>();
        if (adjacencyList.get(word1) != null) {
            for (String bridgeWord : adjacencyList.get(word1).keySet()) {
                if (adjacencyList.containsKey(bridgeWord) && adjacencyList.get(bridgeWord).containsKey(word2)) {
                    foundBridge = true;
                    bridgeWords.add(bridgeWord);
                }
            }
        }

        // 输出结果
        if (!foundBridge) {
            return "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!";
        } else {
            StringBuilder result = new StringBuilder();
            for (String bridgeWord : bridgeWords) {
                result.append("\"").append(bridgeWord).append("\", ");
            }
            result.setLength(result.length() - 2); // 移除最后一个逗号和空格

            if (bridgeWords.size() == 1) {
                return "The bridge word from \"" + word1 + "\" to \"" + word2 + "\" is: " + result + ".";
            } else {
                return "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: " + result + ".";
            }
        }
    }

    private static boolean isValidWord(String word) {
        // 检查单词是否只包含字母
        return !word.matches("[a-zA-Z]+");
    }
}
