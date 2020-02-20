package engine.graphics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.lwjgl.glfw.GLFW.glfwInit;

import engine.Window;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaterialTests {

  static final String TEST_FILE_DIRECTORY = "/images/";
  static final String[] TEST_FILES_NAMES = {
      "bad-tile.png",
      "default_texture.png",
      "mid-tier-tile.png",
      "water-tile.png"
  };

  private Window window;

  /**
   * Sets .
   */
  @BeforeEach
  public void setup() {
    assumeTrue(glfwInit());
    window = new Window(10, 10, "Test Window");
    window.setVisible(false);
    assertFalse(window.isGlfwInitialised);
    window.create();
  }

  /**
   * Cleanup.
   */
  @AfterEach
  public void cleanup() {
    assumeTrue(glfwInit());
    window.destroy();
  }

  @Test
  public void testGetters() {
    for (String testFilesName : TEST_FILES_NAMES) {
      Material testMaterial = new Material(TEST_FILE_DIRECTORY + testFilesName);
      testMaterial.create();
      assertTrue(testMaterial.getTextureID() > 0);
      assertTrue(testMaterial.getWidth() > 0);
      assertTrue(testMaterial.getHeight() > 0);
      testMaterial.destroy();
    }
  }
}
