package UserFunction;

import java.util.Map;
import java.util.Scanner;

public class TextGenerator {
    public static void generateTextWithBridgeWords(Map<String, Map<String, Integer>> adjacencyList) {
        Scanner scanner = new Scanner(System.in);

        // 提示用户输入新文本
        System.out.print("Please enter a new line of text: ");
        String newText = scanner.nextLine().toLowerCase();

        // 分割文本为单词数组
        String[] words = newText.split("\\s+");

        StringBuilder generatedText = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            String currentWord = words[i];
            String nextWord = words[i + 1];

            // 添加当前单词到生成文本中
            generatedText.append(currentWord).append(" ");

            // 检查当前单词和下一个单词是否有桥接词
            // 获取桥接词
            boolean word1InGraph = adjacencyList.containsKey(currentWord);
            if(!word1InGraph){
                for (Map<String, Integer> innerMap : adjacencyList.values()) {
                    if (innerMap.containsKey(currentWord)) {
                        word1InGraph = true;
                        break;
                    }
                }
            }

            boolean word2InGraph = adjacencyList.containsKey(nextWord);
            if(!word2InGraph) {
                for (Map<String, Integer> innerMap : adjacencyList.values()) {
                    if (innerMap.containsKey(nextWord)) {
                        word2InGraph = true;
                        break;
                    }
                }
            }
            if (word1InGraph&&word2InGraph) {
                String bridgeWord = getBridgeWord(adjacencyList, currentWord, nextWord);
                if (!bridgeWord.isEmpty()) {
                    // 插入桥接词
                    generatedText.append(bridgeWord).append(" ");
                }
            }
        }

        // 添加最后一个单词到生成文本中
        generatedText.append(words[words.length - 1]);

        // 输出生成的文本
        System.out.println("Generated text with bridge words: " + generatedText);
    }

    private static String getBridgeWord(Map<String, Map<String, Integer>> adjacencyList, String word1, String word2) {
        // 获取 word1 和 word2 之间的桥接词
        StringBuilder bridgeWords = new StringBuilder();
        for (String bridgeWord : adjacencyList.get(word1).keySet()) {
            if (adjacencyList.containsKey(bridgeWord) && adjacencyList.get(bridgeWord).containsKey(word2)) {
                bridgeWords.append(bridgeWord).append(" ");
                break;
            }
        }
        return bridgeWords.toString().trim(); // 移除末尾的空格
    }
}
