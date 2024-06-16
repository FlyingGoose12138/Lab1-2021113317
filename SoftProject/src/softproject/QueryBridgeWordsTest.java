package softproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class QueryBridgeWordsTest {

    private Map<String, Map<String, Integer>> adjacencyList;

    @BeforeEach
    public void setUp() {
        adjacencyList = new HashMap<>();
        adjacencyList.put("explorers", Map.of("ventured", 1));
        adjacencyList.put("ventured", Map.of("into", 1));
        adjacencyList.put("into", Map.of("unknown", 1));
        adjacencyList.put("unknown", Map.of("territories", 1));
        adjacencyList.put("territories", Map.of("seeking", 1));
        adjacencyList.put("seeking", Map.of("new", 1));
        adjacencyList.put("new", Map.of("civilizations", 1, "worlds", 1));
        adjacencyList.put("civilizations", Map.of("and", 1));
        adjacencyList.put("and", Map.of("discovering", 1));
        adjacencyList.put("discovering", Map.of("strange", 1));
        adjacencyList.put("strange", Map.of("new", 1));
        adjacencyList.put("worlds", Map.of("as", 1));
        adjacencyList.put("as", Map.of("GalAxy", 1));
        adjacencyList.put("GalAxy", Map.of("explorers", 1));
        adjacencyList.put("explorers", Map.of("They", 1));
        adjacencyList.put("They", Map.of("encountered", 1));
        adjacencyList.put("encountered", Map.of("to", 1));
        adjacencyList.put("to", Map.of("challenges", 1));
        adjacencyList.put("challenges", Map.of("and", 1));
        adjacencyList.put("and", Map.of("wonders", 1));
    }

    @Test
    public void testIllegalCharactersInFirstWord() {
        assertEquals("The word1 contains illegal characters. Please enter again.",
                queryBridgeWords(adjacencyList, "explorers!", "ventured"));
    }

    @Test
    public void testIllegalCharactersInSecondWord() {
        assertEquals("The word2 contains illegal characters. Please enter again.",
                queryBridgeWords(adjacencyList, "explorers", "ventured!"));
    }

    @Test
    public void testSameWord() {
        assertEquals("Word1 and word2 can’t be same!",
                queryBridgeWords(adjacencyList, "explorers", "explorers"));
    }

    @Test
    public void testNullAdjacencyList() {
        assertEquals("Lack of adjacencyList!",
                queryBridgeWords(null, "explorers", "ventured"));
    }

    @Test
    public void testEmptyAdjacencyList() {
        assertEquals("Lack of adjacencyList!",
                queryBridgeWords(new HashMap<>(), "explorers", "ventured"));
    }

    @Test
    public void testWordNotInGraph() {
        assertEquals("No \"hello\" in the graph!",
                queryBridgeWords(adjacencyList, "hello", "universe"));
        assertEquals("No \"universe\" in the graph!",
                queryBridgeWords(adjacencyList, "explorers", "universe"));
    }

    @Test
    public void testFindSingleBridgeWord() {
        assertEquals("The bridge word from \"ventured\" to \"unknown\" is: \"into\".",
                queryBridgeWords(adjacencyList, "ventured", "unknown"));
    }

    @Test
    public void testNoBridgeWordFound() {
        assertEquals("No bridge words from \"territories\" to \"civilizations\"!",
                queryBridgeWords(adjacencyList, "territories", "civilizations"));
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
}

