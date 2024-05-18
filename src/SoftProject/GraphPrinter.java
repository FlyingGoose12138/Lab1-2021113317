package SoftProject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GraphPrinter {
    public static void printAdjacencyList(Map<String, Map<String, Integer>> adjacencyList) {
        // 获取当前工作目录
        String currentDir = System.getProperty("user.dir");

        String dotFilePath = currentDir + File.separator + "graph.dot";
        String pngFilePath = currentDir + File.separator + "graph.png";
        System.out.println(dotFilePath);
        // 生成 DOT 代码
        StringBuilder dotCode = new StringBuilder();
        dotCode.append("digraph G {\n");

        // 添加顶点和边
        for (Map.Entry<String, Map<String, Integer>> entry : adjacencyList.entrySet()) {
            String source = entry.getKey();
            dotCode.append("  ").append(source).append(";\n");

            Map<String, Integer> neighbors = entry.getValue();
            for (Map.Entry<String, Integer> neighborEntry : neighbors.entrySet()) {
                String destination = neighborEntry.getKey();
                int weight = neighborEntry.getValue();
                dotCode.append("  ").append(source).append(" -> ").append(destination);
                dotCode.append(" [label=\"").append(weight).append("\"];\n");
            }
        }

        dotCode.append("}\n");

        // 将 DOT 代码写入文件
        try {
            File dotFile = new File(dotFilePath);
            // 写入 DOT 文件
            java.nio.file.Files.write(dotFile.toPath(), dotCode.toString().getBytes());

            // 使用 Graphviz 的 dot 命令将 DOT 文件转换为 PNG 图像
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", dotFilePath, "-o", pngFilePath);
            Process process = pb.start();
            process.waitFor();

            System.out.println("PNG image has been generated: " + pngFilePath);

            File pngfile = new File(pngFilePath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pngfile);
            } else {
                System.out.println("Desktop is not supported on this platform.");
            }

            System.out.println("PNG image has been generated and opened: " + pngFilePath);
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
