package SoftProject;

import UserFunction.BridgeWordFinder;
import UserFunction.GraphPathFinder;
import UserFunction.GraphWalker;
import UserFunction.TextGenerator;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class UserInputHandler {
    public static void handleUserInput(Map<String, Map<String, Integer>> adjacencyList) {
        Scanner scanner = new Scanner(System.in);
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
                    queryBridgeWords(adjacencyList);
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
                    GraphPathFinder.calcShortestPath(adjacencyList);
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

    private static void queryBridgeWords(Map<String, Map<String, Integer>> adjacencyList) {
        // 读取用户输入的两个单词
        Scanner scanner = new Scanner(System.in);
        String word1, word2;

        // 获取并检查第一个单词
        do {
            System.out.print("Please enter the first word: ");
            word1 = scanner.next();
            if (isValidWord(word1)) {
                System.out.println("The word contains illegal characters. Please enter again.");
            }
        } while (isValidWord(word1));

// Get and check the second word
        do {
            System.out.print("Please enter the second word: ");
            word2 = scanner.next();
            if (isValidWord(word2)) {
                System.out.println("The word contains illegal characters. Please enter again.");
            }
        } while (isValidWord(word2));

// Output the query result
        System.out.println("Querying bridge words...");
        BridgeWordFinder.findBridgeWords(adjacencyList,word1,word2);

        //
    }

    private static boolean isValidWord(String word) {
        // 检查单词是否只包含字母
        return !word.matches("[a-zA-Z]+");
    }
}
