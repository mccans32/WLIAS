package engine;

import static java.lang.System.currentTimeMillis;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

/**
 * The type Window.
 */
public class Window {

  /**
   * The Enable vsync.
   */
  static final boolean ENABLE_V_SYNC = true;
  /**
   * The Frames.
   */
  public int frames;
  /**
   * The Time.
   */
  public long time;
  private long window;
  private int height;
  private int width;
  private String title;
  private float backgroundR;
  private float backgroundG;
  private float backgroundB;
  private float backgroundAlpha;
  private GLFWWindowSizeCallback windowSizeCallback;
  private boolean hasResized = false;
  private boolean fullscreen = false;
  private int[] windowPosX = new int[1];
  private int[] windowPosY = new int[1];
  private Input input;

  /**
   * Instantiates a new Window.
   *
   * @param width  the width
   * @param height the height
   * @param title  the title
   */
  public Window(int width, int height, String title) {
    this.width = width;
    this.height = height;
    this.title = title;
  }

  /**
   * Sets error callback.
   */
  public static void setErrorCallback() {
    glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
  }

  private void setLocalCallbacks() {
    windowSizeCallback = new GLFWWindowSizeCallback() {
      @Override
      public void invoke(long argWindow, int argWidth, int argHeight) {
        width = argWidth;
        height = argHeight;
        hasResized = true;
      }
    };

    //Set Size Callback
    glfwSetWindowSizeCallback(window, windowSizeCallback);
    //Set input callbacks
    glfwSetKeyCallback(window, input.getKeyboard());
    glfwSetCursorPosCallback(window, input.getMouseMove());
    glfwSetScrollCallback(window, input.getMouseScroll());
    glfwSetMouseButtonCallback(window, input.getMouseButtons());
  }

  /**
   * Create.
   */
  public void create() {
    setErrorCallback();

    if (!glfwInit()) {
      System.err.println(("GLFW Failed to start!"));
      System.exit(1);
    }

    input = new Input();

    window = glfwCreateWindow(width, height, title, (fullscreen ? glfwGetPrimaryMonitor() : 0), 0);

    if (window == 0) {
      throw new IllegalStateException("Failed to create window!");
    }

    if (!fullscreen) {
      GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
      // X value
      windowPosX[0] = (videoMode.width() - width) / 2;
      // Y value
      windowPosY[0] = (videoMode.height() - height) / 2;
      glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
    }

    //Set Current Context to Window
    glfwMakeContextCurrent(window);
    // Enables OpenGL functionality
    GL.createCapabilities();
    // Set Depth Test
    GL11.glEnable(GL11.GL_DEPTH_TEST);
    //Set local callbacks
    setLocalCallbacks();
    //Display Window
    showWindow();
    //Enable VSync
    setVSync(ENABLE_V_SYNC);
    // set timer for window
    time = currentTimeMillis();

  }

  /**
   * Update.
   */
  public void update() {
    if (hasResized) {
      GL11.glViewport(0, 0, width, height);
      hasResized = false;
    }
    GL11.glClearColor(backgroundR, backgroundG, backgroundB, backgroundAlpha);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    glfwPollEvents();
    updateFrameCounter();
  }

  /**
   * Destroy.
   */
  public void destroy() {
    Input.destroy();
    glfwWindowShouldClose(window);
    glfwDestroyWindow(window);
  }

  /**
   * Swap buffers.
   */
  public void swapBuffers() {
    glfwSwapBuffers(window);
  }

  /**
   * Should close boolean.
   *
   * @return the boolean
   */
  public boolean shouldClose() {
    return glfwWindowShouldClose(window);
  }

  /**
   * Show window.
   */
  public void showWindow() {
    glfwShowWindow(window);
  }

  /**
   * Sets v sync.
   *
   * @param vsync the v sync
   */
  public void setVSync(boolean vsync) {
    glfwSwapInterval(vsync ? 1 : 0);
  }

  /**
   * Sets background colour.
   *
   * @param r     the r
   * @param g     the g
   * @param b     the b
   * @param alpha the alpha
   */
  public void setBackgroundColour(float r, float g, float b, float alpha) {
    backgroundR = r;
    backgroundG = g;
    backgroundB = b;
    backgroundAlpha = alpha;
  }

  /**
   * Update frame counter.
   */
  public void updateFrameCounter() {
    frames++;
    if (currentTimeMillis() > time + 1000) {
      glfwSetWindowTitle(window, title + " | FPS " + frames);
      time = currentTimeMillis();
      frames = 0;
    }
  }

  /**
   * Is fullscreen boolean.
   *
   * @return the boolean
   */
  public boolean isFullscreen() {
    return fullscreen;
  }

  /**
   * Sets fullscreen.
   *
   * @param fullscreen the fullscreen
   */
  public void setFullscreen(boolean fullscreen) {
    this.fullscreen = fullscreen;
    this.hasResized = true;
    if (isFullscreen()) {
      glfwGetWindowPos(window, windowPosX, windowPosY);
      glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
    } else {
      glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
    }
  }

  /**
   * Gets height.
   *
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets width.
   *
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Is has resized boolean.
   *
   * @return the boolean
   */
  public boolean isHasResized() {
    return hasResized;
  }
}
