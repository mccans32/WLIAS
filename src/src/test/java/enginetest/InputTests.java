package enginetest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.lwjgl.glfw.GLFW.glfwInit;

import engine.Input;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InputTests {
  private Input input;

  @BeforeEach
  void setup() {
    assumeTrue(glfwInit());
    input = new Input();
  }

  @AfterEach
  public void cleanUpEach() {
    assumeTrue(glfwInit());
    Input.destroy();
  }

  @Test
  public void keyBoardIsSet() {
    assertNotNull(input.getKeyboard());
  }

  @Test
  public void mouseMoveIsSet() {
    assertNotNull(input.getMouseMove());
  }

  @Test
  public void mouseScrollIsSet() {
    assertNotNull(input.getMouseScroll());
  }

  @Test
  public void mouseButtonsIsSet() {
    assertNotNull(input.getMouseButtons());
  }
}
