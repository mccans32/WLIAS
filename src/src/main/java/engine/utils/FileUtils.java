package engine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * The type File utils.
 */
public class FileUtils {
  /**
   * Load as string string.
   *
   * @param path the path
   * @return the string
   */
  public static String loadAsString(String path) {
    StringBuilder result = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(FileUtils.class.getResourceAsStream(path), StandardCharsets.UTF_8))) {
      String line;
      while ((line = reader.readLine()) != null) {
        result.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result.toString();
  }
}