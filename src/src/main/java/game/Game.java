package game;

import engine.Input;
import engine.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import java.lang.System;

public class Game {

  final int WINDOW_WIDTH = 1200, WINDOW_HEIGHT = 900;
  final String WINDOW_TILE = "We Live in a Society";
  final float BACKGROUND_RED = 1.0f;
  final float BACKGROUND_GREEN = 0.0f;
  final float BACKGROUND_BLUE = 0.0f;
  final float BACKGROUND_ALPHA = 1.0f;
  public Window window;

  public void start() {
    initialize();
    gameLoop();
    dispose();
  }

  private void dispose() {
    System.out.println("Disposing active processes");
    window.destroy();
  }

  private void gameLoop() {
    System.out.println("This is the Game Loo\n");
    // while (true)
    while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
      update();
      render();

      //Check for fullscreen
        if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
            window.setFullscreen(!window.isFullscreen());
        }
    }
  }

  private void initialize() {
    System.out.println("Initializing Simulation\n");
    // Create main window
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TILE);
    window.setBackgroundColour(BACKGROUND_RED, BACKGROUND_GREEN, BACKGROUND_BLUE, BACKGROUND_ALPHA);
    window.create();
  }

  public void update() {
    window.update();
    // Temporary
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
      System.out.println("X: " + Input.getMouseX() + ", Y: " + Input.getMouseY());
    }
  }

  public void render() {
    window.swapBuffers();
  }
}
