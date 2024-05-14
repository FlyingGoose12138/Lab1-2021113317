package UserFunction;

import java.util.*;
import java.io.*;
public class GraphWalker {

    private final Map<String, Map<String, Integer>> adjacencyList;

    public GraphWalker(Map<String, Map<String, Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public void randomWalk() throws IOException {
        Random random = new Random();
        List<String> visitedNodes = new ArrayList<>();
        Set<String> visitedEdges = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        boolean interactive = false;
        System.out.println("Enter 1 for interactive mode or 2 for automatic mode:"); //按1是单步模式，2是自动输出全部节点
        String choice = scanner.nextLine();
        if ("1".equals(choice)) {
            interactive = true;
        } else if ("2".equals(choice)) {
            interactive = false;
        } else {
            System.out.println("Invalid choice. Exiting.");
        }
        boolean flag = !interactive;

        // 选择一个随机的起点
        List<String> keys = new ArrayList<>(adjacencyList.keySet());
        if (keys.isEmpty()) {
            return;
        }
        String current = keys.get(random.nextInt(keys.size()));
        visitedNodes.add(current);

        // 随机游走直到出现重复的边或者没有出边
        while (true) {
            if (!adjacencyList.containsKey(current) || adjacencyList.get(current).isEmpty()) {
                break; // 没有出边
            }

            List<String> possibleNextNodes = new ArrayList<>(adjacencyList.get(current).keySet());
            String nextNode = possibleNextNodes.get(random.nextInt(possibleNextNodes.size()));
            String edge = current + "->" + nextNode;

            if (visitedEdges.contains(edge)) {
                break; // 出现了重复的边
            }
            visitedEdges.add(edge);
            visitedNodes.add(nextNode);
            current = nextNode;
            if(flag){
                System.out.println("Visited node: " + nextNode);
            }
            else if (interactive) {
                System.out.println("Visited node: " + nextNode);
                System.out.println("Press Enter to continue or 'c' to exit interactive mode:");
                String input = scanner.nextLine();
                if ("c".equalsIgnoreCase(input)) {
                    break;
                }
            }
        }

        // 输出遍历的节点到文件
        writeFile(visitedNodes);
    }

    private void writeFile(List<String> visitedNodes) throws IOException {
        File file = new File("random_walk.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String node : visitedNodes) {
                writer.write(node + " ");
            }
        }
    }

}


