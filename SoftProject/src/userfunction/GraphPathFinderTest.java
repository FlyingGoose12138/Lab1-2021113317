package userfunction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents MyClass which is responsible for...
 * (add more description here)
 */
public class GraphPathFinderTest {
    /**
     * This class represents MyClass which is responsible for...
     * (add more description here)
     */
    private Map<String, Map<String, Integer>> adjacencyList;

    @BeforeEach
    void setUp() {
        adjacencyList = new HashMap<>();
        adjacencyList.put("explorers", Map.of("ventured", 1, "They", 1));
        adjacencyList.put("ventured", Map.of("into", 1));
        adjacencyList.put("into", Map.of("unknown", 1));
        adjacencyList.put("unknown", Map.of("territories", 1));
        adjacencyList.put("territories", Map.of("seeking", 1));
        adjacencyList.put("seeking", Map.of("new", 1));
        adjacencyList.put("new", Map.of("civilizations", 1, "worlds", 1));
        adjacencyList.put("civilizations", Map.of("and", 1));
        adjacencyList.put("and", Map.of("discovering", 1, "wonders", 1));
        adjacencyList.put("discovering", Map.of("strange", 1));
        adjacencyList.put("strange", Map.of("new", 1));
        adjacencyList.put("worlds", Map.of("as", 1));
        adjacencyList.put("as", Map.of("GalAxy", 1));
        adjacencyList.put("GalAxy", Map.of("explorers", 1));
        adjacencyList.put("They", Map.of("encountered", 1));
        adjacencyList.put("encountered", Map.of("to", 1));
        adjacencyList.put("to", Map.of("challenges", 1));
        adjacencyList.put("challenges", Map.of("and", 1));
    }
    // Test invalid characters in input
    @Test
    public void testInvalidCharacters() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "3xplorers", "worlds");
        assertEquals("The word1 contains illegal characters. Please enter again.", result);
    }
    @Test
    public void testCalcAllPathsFromNode() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "explorers", "");
        assertTrue(result.contains("Shortest path length from \"explorers\" to \"ventured\" is 1"));
        assertTrue(result.contains("Shortest path length from \"explorers\" to \"They\" is 1"));
    }


    // Test a valid path with multiple hops
    @Test
    public void testLongPath() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "explorers", "wonders");
        assertTrue(result.contains("Shortest path length from \"explorers\" to \"wonders\" is 9"));
        assertTrue(result.contains("Path: explorers->ventured->into->unknown->territories->seeking->new->civilizations->and->wonders"));
    }
    @Test
    public void testInvalidCharacters2() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "explorers", "hello");
        assertEquals("The word2 contains illegal characters. Please enter again.", result);
    }
    // Test path to itself
    @Test
    public void testLoopPath() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "explorers", "explorers");
        assertEquals("Same word, distance is 0", result);
    }

    // Test no available path
    @Test
    public void testNoPathAvailable() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "wonders", "ventured");
        assertEquals("No path available from wonders to ventured", result);
    }

    // Test input words that don't exist in the graph
    @Test
    public void testNonexistentStart() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "mythical", "legends");
        assertEquals("No path available from mythical to legends", result);
    }

    // Test a short valid path
    @Test
    public void testShortPath() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "worlds", "GalAxy");
        assertTrue(result.contains("Shortest path length from \"worlds\" to \"GalAxy\" is 1"));
        assertTrue(result.contains("Path: worlds->GalAxy"));
    }

    // Test case sensitivity
    @Test
    public void testCaseSensitivity() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "as", "galaxy");
        assertEquals("The word contains illegal characters. Please enter again.", result);
    }

    // Test paths with empty second word to find all paths


    // Test a more complex path calculation
    @Test
    public void testComplexPath() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "into", "wonders");
        assertTrue(result.contains("Shortest path length from \"into\" to \"wonders\" is 7"));
    }

    // Test edge case with an isolated node
    @Test
    public void testIsolatedNode() {
        // Temporarily add an isolated node
        adjacencyList.put("isolated", new HashMap<>());
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "isolated", "explorers");
        assertEquals("No path available from isolated to explorers", result);
        // Remove the isolated node after test
        adjacencyList.remove("isolated");
    }

    // Test valid path calculation with uppercase
    @Test
    public void testUppercasePathCalculation() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "EXPLORERS?", "GALAXY");
        assertEquals("The word1 contains illegal characters. Please enter again.", result);
    }

    // Test output format checking
    @Test
    public void testOutputFormat() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "explorers", "and");
        assertTrue(result.matches("Shortest path length from \"explorers\" to \"and\" is \\d+\\nPath: explorers->.*->and"));
    }



    // Test path calculation with branching paths
    @Test
    public void testBranchingPaths() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "new", "and");
        assertTrue(result.contains("Shortest path length from \"new\" to \"and\" is 2"));
    }

    // Test handling of spaces in input
    @Test
    public void testSpacesInInput() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "explorers ", " wonders");
        assertEquals("The word1 contains illegal characters. Please enter again.", result);
    }

    // Test redirection in paths
    @Test
    public void testRedirectionPath() {
        String result = GraphPathFinder.calcShortestPath(adjacencyList, "new", "GalAxy");
        assertTrue(result.contains("Shortest path length from \"new\" to \"GalAxy\" is 3"));
    }

}