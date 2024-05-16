package UserFunction;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BridgeWordFinder {
    public static void findBridgeWords(Map<String, Map<String, Integer>> adjacencyList, String word1, String word2) {
        // 检查 word1 和 word2 是否在图中出现
        if (!adjacencyList.containsKey(word1) && !adjacencyList.containsKey(word2)) {
            System.out.println("Neither \"" + word1 + "\" nor \"" + word2 +"\" is in the graph!");
            return;
        } else if (!adjacencyList.containsKey(word1)) {
            System.out.println("No \"" + word1 +  "\" in the graph!");
            return;
        } else if (!adjacencyList.containsKey(word2)) {
            System.out.println("No \"" + word2 + "\" in the graph!");
            return;
        }

        // 查找桥接词
        boolean foundBridge = false;
        Set<String> bridgeWords = new HashSet<>();
        for (String bridgeWord : adjacencyList.get(word1).keySet()) {
            if (adjacencyList.containsKey(bridgeWord) && adjacencyList.get(bridgeWord).containsKey(word2)) {
                foundBridge = true;
                bridgeWords.add(bridgeWord);
            }
        }

        // 输出结果
        if (!foundBridge) {
            System.out.println("No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!");
        } else {
            StringBuilder result = new StringBuilder();
            for (String bridgeWord : bridgeWords) {
                result.append("\"").append(bridgeWord).append("\", ");
            }
            result.setLength(result.length() - 2); // 移除最后一个逗号和空格

            if (bridgeWords.size() == 1) {
                System.out.println("The bridge word from \"" + word1 + "\" to \"" + word2 + "\" is: " + result + ".");
            } else {
                System.out.println("The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: " + result + ".");
            }
        }
    }
}
