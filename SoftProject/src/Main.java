import softproject.TextAnalyzer;
import softproject.UserInputHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * This class represents MyClass which is responsible for...
 * (add more description here)
 */
public class Main {
  /**
  * This class represents MyClass which is responsible for...
  * (add more description here)
  */
  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("NP_IMMEDIATE_DEREFERENCE_OF_READLINE")
  public static void main(String[] args) {
    String filePath = "";

    boolean isValidInput = false;
    while (!isValidInput) {
      try {
        BufferedReader reader = new BufferedReader(
            new java.io.InputStreamReader(System.in, StandardCharsets.UTF_8));
        System.out.print("Do you want to provide a file path? (y/n): ");
        String input = reader.readLine().toLowerCase();

        if (input.equals("y")) {
          // 如果用户选择提供文件参数，则让用户输入文件名
          System.out.print(
                    "Please enter the absolute path or filename of the text file: ");
          filePath = reader.readLine();
          isValidInput = true; // 设置为有效输入，退出循环
        } else if (input.equals("n")) {
          // 如果用户选择不提供文件参数，则使用当前目录的 test.txt 文件
          filePath = "test.txt";
          isValidInput = true; // 设置为有效输入，退出循环
        } else {
          // 如果用户输入无效，则提示错误，并继续等待用户输入
          System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }
      } catch (IOException e) {
        System.out.println("An error occurred while reading user input: " + e.getMessage());
        return;
      }
    }

    // 解析用户输入的文件路径
    Path inputPath = Paths.get(filePath);

    // 如果输入路径是相对路径，则将其与当前工作目录组合成绝对路径
    if (!inputPath.isAbsolute()) {
      // 获取当前工作目录
      String currentDir = System.getProperty("user.dir");
      // 将相对路径与当前工作目录以及 "src" 目录组合成绝对路径
      inputPath = Paths.get(currentDir).resolve("src").resolve(filePath);
    }

    // 读取文本文件内容并打印到控制台
    String stringentPath = inputPath.toString();
    Map<String, Map<String, Integer>> adjacencyList = TextAnalyzer.analyzeText(stringentPath);
    UserInputHandler.handleUserInput(adjacencyList);
  }
}
