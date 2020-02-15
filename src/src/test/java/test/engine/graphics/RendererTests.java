package test.engine.graphics;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.lwjgl.glfw.GLFW.glfwInit;

import engine.Window;
import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.graphics.Vertex2D;
import engine.utils.ColourUtils;
import java.util.concurrent.TimeUnit;
import math.Vector2f;
import org.jfree.chart.ChartColor;
import org.junit.jupiter.api.Test;

public class RendererTests {
  static final String SHADERS_PATH = "/shaders/";
  static final String VERTEX_SHADER_FILE_NAME = "testVertex.glsl";
  static final String FRAGMENT_SHADER_FILE_NAME = "testFragment.glsl";
  static final int WINDOW_HEIGHT = 600;
  static final int WINDOW_WIDTH = 800;
  static final String WINDOW_TITLE = "Test Window";
  static int[] rectangleIndices = {0, 1, 2, 3};
  static int[] triangleIndices = {0, 1, 2};
  Vertex2D topLeftVertex = new Vertex2D(new Vector2f(-0.5f, 0.5f));
  Vertex2D topRightVertex = new Vertex2D(new Vector2f(0.5f, 0.5f));
  Vertex2D topCenterVertex = new Vertex2D(new Vector2f(0f, 0.5f));
  Vertex2D bottomLeftVertex = new Vertex2D(new Vector2f(-0.5f, -0.5f));
  Vertex2D bottomRightVertex = new Vertex2D(new Vector2f(0.5f, -0.5f));
  Vertex2D[] rectangleVertices = {topLeftVertex, topRightVertex, bottomLeftVertex,
      bottomRightVertex};
  Vertex2D[] triangleVertices = {topCenterVertex, bottomLeftVertex, bottomRightVertex};
  private Window window;
  private Renderer renderer;

  /**
   * Sets .
   */
  public void setupWindow() {
    Shader shader = new Shader(SHADERS_PATH + VERTEX_SHADER_FILE_NAME,
        SHADERS_PATH + FRAGMENT_SHADER_FILE_NAME);
    assumeTrue(glfwInit());
    renderer = new Renderer(shader);
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE);
    window.setBackgroundColour(1.0f, 1.0f, 1.0f, 1.0f);
    window.create();
    shader.create();
  }

  private void drawMesh(Mesh mesh) throws InterruptedException {
    mesh.create();
    window.update();
    renderer.renderMesh(mesh);
    window.swapBuffers();
    window.update();
    TimeUnit.SECONDS.sleep(3);
  }

  @Test
  public void testRectangle() throws InterruptedException {
    setupWindow();
    Mesh testMesh = new Mesh(rectangleVertices, rectangleIndices);
    drawMesh(testMesh);
  }

  @Test
  public void testTriangle() throws InterruptedException {
    setupWindow();
    Mesh testMesh = new Mesh(triangleVertices, triangleIndices);
    drawMesh(testMesh);
  }

  @Test
  public void testBoth() throws InterruptedException {
    setupWindow();
    Mesh rectangleMesh = new Mesh(rectangleVertices, rectangleIndices);
    rectangleMesh.setColour(ColourUtils.convertColor(ChartColor.BLUE));
    Mesh triangleMesh = new Mesh(triangleVertices, triangleIndices);
    triangleMesh.setColour(ColourUtils.convertColor(ChartColor.YELLOW));
    drawMesh(rectangleMesh);
    drawMesh(triangleMesh);
  }

  @Test void testBlueSquare() throws InterruptedException {
    setupWindow();
    Mesh testMesh = new Mesh(rectangleVertices, rectangleIndices);
    testMesh.setColour(ColourUtils.convertColor(ChartColor.BLUE));
    drawMesh(testMesh);
  }

  @Test void testPinkTriangle() throws InterruptedException {
    setupWindow();
    Mesh testMesh = new Mesh(triangleVertices, triangleIndices);
    testMesh.setColour(ColourUtils.convertColor(ChartColor.PINK));
    drawMesh(testMesh);
  }

}
