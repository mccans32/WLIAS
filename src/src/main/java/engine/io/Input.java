package engine.io;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;


/**
 * The type Input.
 */
public class Input {

  private static boolean[] keys = new boolean[GLFW_KEY_LAST];
  private static boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
  private static double mouseX;
  private static double mouseY;
  private static double scrollX;
  private static double scrollY;
  private static double oldMouseX;
  private static double oldMouseY;

  private static GLFWKeyCallback keyboard;
  private static GLFWCursorPosCallback mouseMove;
  private static GLFWMouseButtonCallback mouseButtons;
  private static GLFWScrollCallback mouseScroll;

  /**
   * Instantiates a new Input.
   */
  public Input() {
    setKeyboard(new GLFWKeyCallback() {
      @Override
      public void invoke(long window, int key, int scancode, int action, int mods) {
        Input.keys[key] = (action != GLFW_RELEASE);
      }
    });

    setMouseMove(new GLFWCursorPosCallback() {
      @Override
      public void invoke(long window, double xpos, double ypos) {
        Input.oldMouseX = Input.mouseX;
        Input.oldMouseY = Input.mouseY;
        Input.mouseX = xpos;
        Input.mouseY = ypos;
      }
    });

    setMouseButtons(new GLFWMouseButtonCallback() {
      @Override
      public void invoke(long window, int button, int action, int mods) {
        Input.buttons[button] = (action != GLFW.GLFW_RELEASE);
      }
    });

    setMouseScroll(new GLFWScrollCallback() {
      @Override
      public void invoke(long window, double xoffset, double yoffset) {
        Input.scrollX += xoffset;
        Input.scrollY += yoffset;
      }
    });
  }

  /**
   * Is key down boolean.
   *
   * @param key the key
   * @return the boolean
   */
  public static boolean isKeyDown(int key) {
    return keys[key];
  }

  /**
   * Is button down boolean.
   *
   * @param button the button
   * @return the boolean
   */
  public static boolean isButtonDown(int button) {
    return buttons[button];
  }

  /**
   * Destroy.
   */
  public static void destroy() {
    Input.keyboard.free();
    Input.mouseMove.free();
    Input.mouseButtons.free();
    Input.mouseScroll.free();
  }

  /**
   * Gets mouse x.
   *
   * @return the mouse x
   */
  public static double getMouseX() {
    return mouseX;
  }

  /**
   * Gets mouse y.
   *
   * @return the mouse y
   */
  public static double getMouseY() {
    return mouseY;
  }

  /**
   * Gets scroll x.
   *
   * @return the scroll x
   */
  public static double getScrollX() {
    return scrollX;
  }

  /**
   * Gets scroll y.
   *
   * @return the scroll y
   */
  public static double getScrollY() {
    return scrollY;
  }

  public static double getOldMouseX() {
    return oldMouseX;
  }

  public static double getOldMouseY() {
    return oldMouseY;
  }

  /**
   * Gets mouse scroll.
   *
   * @return the mouse scroll
   */
  public GLFWScrollCallback getMouseScroll() {
    return mouseScroll;
  }

  public static void setMouseScroll(GLFWScrollCallback mouseScroll) {
    Input.mouseScroll = mouseScroll;
  }

  /**
   * Gets mouse move.
   *
   * @return the mouse move
   */
  public GLFWCursorPosCallback getMouseMove() {
    return mouseMove;
  }

  public static void setMouseMove(GLFWCursorPosCallback mouseMove) {
    Input.mouseMove = mouseMove;
  }

  /**
   * Gets keyboard.
   *
   * @return the keyboard
   */
  public GLFWKeyCallback getKeyboard() {
    return keyboard;
  }

  /**
   * Sets keyboard.
   *
   * @param keyboard the keyboard
   */
  public static void setKeyboard(GLFWKeyCallback keyboard) {
    Input.keyboard = keyboard;
  }

  /**
   * Gets mouse buttons.
   *
   * @return the mouse buttons
   */
  public GLFWMouseButtonCallback getMouseButtons() {
    return mouseButtons;
  }

  public static void setMouseButtons(GLFWMouseButtonCallback mouseButtons) {
    Input.mouseButtons = mouseButtons;
  }
}
