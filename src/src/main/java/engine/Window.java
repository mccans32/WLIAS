package engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static java.lang.System.currentTimeMillis;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class Window {

  private long window;
  private int height;
  private int width;
  private String title;
  public int frames;
  public long time;
  private float backgroundR, backgroundG, backgroundB, backgroundAlpha;
  private GLFWWindowSizeCallback windowSizeCallback;
  private boolean hasResized = false;
  private boolean fullscreen = false;
  private int[] windowPosX = new int[1], windowPosY = new int[1];

  //FINALS
  final boolean ENABLE_VSYNC = true;

  private Input input;

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

  public Window(int width, int height, String title) {
    this.width = width;
    this.height = height;
    this.title = title;
  }

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
    setVSync(ENABLE_VSYNC);
    // set timer for window
    time = currentTimeMillis();

  }

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

  public void destroy() {
    Input.destroy();
    glfwWindowShouldClose(window);
    glfwDestroyWindow(window);
  }

  public void swapBuffers() {
    glfwSwapBuffers(window);
  }

  public boolean shouldClose() {
    return glfwWindowShouldClose(window);
  }

  public void showWindow() {
    glfwShowWindow(window);
  }

  public void setVSync(boolean V_sync) {
    glfwSwapInterval(V_sync ? 1 : 0);
  }

  public void setBackgroundColour(float r, float g, float b, float alpha) {
    backgroundR = r;
    backgroundG = g;
    backgroundB = b;
    backgroundAlpha = alpha;
  }

  public void updateFrameCounter() {
    frames++;
    if (currentTimeMillis() > time + 1000) {
      glfwSetWindowTitle(window, title + " | FPS " + frames);
      time = currentTimeMillis();
      frames = 0;
    }
  }

  public boolean isFullscreen() {
    return fullscreen;
  }

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

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public String getTitle() {
    return title;
  }

  public boolean isHasResized() {
    return hasResized;
  }
}
