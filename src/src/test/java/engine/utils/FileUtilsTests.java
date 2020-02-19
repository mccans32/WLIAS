package engine.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FileUtilsTests {
  static final String TEST_FILES_PATH = "/readFiles/";
  private String[] nonexistentPaths = {"/fake/path/1.txt", "/fake/path/2.txt"};
  // Array of Arrays of form {file name, file text}
  private String[][] fileData = {{"test1.txt", "Line 1\nLine 2\n"}, {"test2.txt", ""}};

  @Test
  public void testNonexistentPaths() {
    for (String path : nonexistentPaths) {
      assertThrows(NullPointerException.class, () -> FileUtils.loadAsString(path));
    }
  }

  @Test
  public void testLoadAsString() {
    for (String[] data : fileData) {
      String fileName = data[0];
      String fileText = data[1];
      assertEquals(FileUtils.loadAsString(TEST_FILES_PATH + fileName), fileText);
    }
  }
}
