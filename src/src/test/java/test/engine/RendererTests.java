package test.engine;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.lwjgl.glfw.GLFW.glfwInit;

import engine.Window;
import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Vertex2D;
import java.util.concurrent.TimeUnit;
import math.Vector2f;
import org.junit.jupiter.api.Test;

public class RendererTests {
  /**
   * The Window height.
   */
  static final int WINDOW_HEIGHT = 600;
  /**
   * The Window width.
   */
  static final int WINDOW_WIDTH = 800;
  /**
   * The Window title.
   */
  static final String WINDOW_TITLE = "Test Window";
  /**
   * The Rectangle indices.
   */
  static int[] rectangleIndices = {0, 1, 2, 2, 3, 1};
  /**
   * The Triangle indices.
   */
  static int[] triangleIndices = {1, 2, 3};
  /**
   * The Top left vertex.
   */
  Vertex2D topLeftVertex = new Vertex2D(new Vector2f(-0.5f, 0.5f));
  /**
   * The Top right vertex.
   */
  Vertex2D topRightVertex = new Vertex2D(new Vector2f(0.5f, 0.5f));
  /**
   * The Top center vertex.
   */
  Vertex2D topCenterVertex = new Vertex2D(new Vector2f(0f, 0.5f));
  /**
   * The Bottom left vertex.
   */
  Vertex2D bottomLeftVertex = new Vertex2D(new Vector2f(-0.5f, -0.5f));
  /**
   * The Bottom right vertex.
   */
  Vertex2D bottomRightVertex = new Vertex2D(new Vector2f(0.5f, -0.5f));
  /**
   * The Rectangle vertices.
   */
  Vertex2D[] rectangleVertices = {topLeftVertex, topRightVertex, bottomLeftVertex,
      bottomRightVertex};
  /**
   * The Triangle vertices.
   */
  Vertex2D[] triangleVertices = {topCenterVertex, bottomLeftVertex, bottomRightVertex};
  private Window window;
  private Renderer renderer;

  /**
   * Sets .
   */
  public void setupWindow() {
    assumeTrue(glfwInit());
    renderer = new Renderer();
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE);
    window.setBackgroundColour(1.0f, 0.0f, 0.0f, 1.0f);
    window.create();
  }

  @Test
  public void testRectangle() throws InterruptedException {
    setupWindow();
    Mesh testMesh = new Mesh(rectangleVertices, rectangleIndices);
    testMesh.create();
    window.update();
    renderer.renderMesh(testMesh);
    window.swapBuffers();
    window.update();
    TimeUnit.SECONDS.sleep(3);
  }

  @Test
  public void testTriangle() throws InterruptedException {
    setupWindow();
    Mesh testMesh = new Mesh(triangleVertices, triangleIndices);
    testMesh.create();
    window.update();
    renderer.renderMesh(testMesh);
    window.swapBuffers();
    window.update();
    TimeUnit.SECONDS.sleep(3);
  }
}
