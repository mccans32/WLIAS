package engine.graphics;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.lwjgl.glfw.GLFW.glfwInit;

import engine.Window;
import engine.utils.ColourUtils;
import java.awt.Color;
import java.util.concurrent.TimeUnit;
import math.Vector2f;
import org.jfree.chart.ChartColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RendererTests {
  static final String TEST_IMAGES_DIRECTORY = "/images/";
  static final String[] TEST_IMAGES_NAMES = {
      "bad-tile.png",
      "default_texture.png",
      "mid-tier-tile.png",
      "water-tile.png"
  };

  private static final int SLEEP_TIME = 1;
  static final String SHADERS_PATH = "/shaders/";
  static final String VERTEX_COLOUR_SHADER_FILE_NAME = "testColourVertex.glsl";
  static final String FRAGMENT_COLOUR_SHADER_FILE_NAME = "testColourFragment.glsl";
  static final String VERTEX_IMAGE_SHADER_FILE_NAME = "testImageVertex.glsl";
  static final String FRAGMENT_IMAGE_SHADER_FILE_NAME = "testImageFragment.glsl";
  static final int WINDOW_HEIGHT = 600;
  static final int WINDOW_WIDTH = 800;
  static final String WINDOW_TITLE = "Test Window";
  static int[] rectangleIndices = {0, 1, 2, 3};
  static int[] triangleIndices = {0, 1, 2};
  static int[] imageRectangleIndices = rectangleIndices;
  Vertex2D topLeftVertex = new Vertex2D(new Vector2f(-0.5f, 0.5f));
  Vertex2D topRightVertex = new Vertex2D(new Vector2f(0.5f, 0.5f));
  Vertex2D topCenterVertex = new Vertex2D(new Vector2f(0f, 0.5f));
  Vertex2D bottomLeftVertex = new Vertex2D(new Vector2f(-0.5f, -0.5f));
  Vertex2D bottomRightVertex = new Vertex2D(new Vector2f(0.5f, -0.5f));
  Vertex2D[] rectangleVertices = {topLeftVertex, topRightVertex, bottomLeftVertex,
      bottomRightVertex};
  Vertex2D[] triangleVertices = {topCenterVertex, bottomLeftVertex, bottomRightVertex};
  Vertex2D topLeftImageVertex = new Vertex2D(new Vector2f(-0.5f, 0.5f),
      ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 0f));
  Vertex2D topRightImageVertex = new Vertex2D(new Vector2f(0.5f, 0.5f),
      ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 0f));
  Vertex2D bottomLeftImageVertex = new Vertex2D(new Vector2f(-0.5f, -0.5f),
      ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 1f));
  Vertex2D bottomRightImageVertex = new Vertex2D(new Vector2f(0.5f, -0.5f),
      ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 1f));
  Vertex2D[] imageRectangleVertices = {topLeftImageVertex, topRightImageVertex,
      bottomLeftImageVertex, bottomRightImageVertex};
  private Window window;
  private Renderer renderer;
  private Shader shader;

  @BeforeEach
  public void setup() {
    assumeTrue(glfwInit());
  }

  /**
   * Sets .
   */
  public void setupWindow(String vertexFileName, String fragmentFileName) {
    shader = new Shader(SHADERS_PATH + vertexFileName,
        SHADERS_PATH + fragmentFileName);
    renderer = new Renderer(shader);
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE);
    window.setBackgroundColour(1.0f, 1.0f, 1.0f, 1.0f);
    window.create();
    shader.create();
  }


  private void drawMesh(Mesh mesh) throws InterruptedException {
    window.update();
    mesh.create();
    mesh.getMaterial().create();
    renderer.renderMesh(mesh);
    window.swapBuffers();
    window.update();
    TimeUnit.SECONDS.sleep(SLEEP_TIME);
  }

  private void shutDownWindow() {
    window.destroy();
    shader.destroy();
  }

  @Test
  public void testRectangle() throws InterruptedException {
    setupWindow(VERTEX_COLOUR_SHADER_FILE_NAME, FRAGMENT_COLOUR_SHADER_FILE_NAME);
    Mesh testMesh = new Mesh(rectangleVertices, rectangleIndices);
    drawMesh(testMesh);
  }

  @Test
  public void testTriangle() throws InterruptedException {
    setupWindow(VERTEX_COLOUR_SHADER_FILE_NAME, FRAGMENT_COLOUR_SHADER_FILE_NAME);
    Mesh testMesh = new Mesh(triangleVertices, triangleIndices);
    drawMesh(testMesh);
    testMesh.destroy();
    shutDownWindow();
  }

  @Test
  public void testBoth() throws InterruptedException {
    setupWindow(VERTEX_COLOUR_SHADER_FILE_NAME, FRAGMENT_COLOUR_SHADER_FILE_NAME);
    Mesh rectangleMesh = new Mesh(rectangleVertices, rectangleIndices);
    rectangleMesh.setColour(ColourUtils.convertColor(ChartColor.BLUE));
    Mesh triangleMesh = new Mesh(triangleVertices, triangleIndices);
    triangleMesh.setColour(ColourUtils.convertColor(ChartColor.YELLOW));
    drawMesh(triangleMesh);
    drawMesh(rectangleMesh);
    triangleMesh.destroy();
    rectangleMesh.destroy();
    shutDownWindow();
  }

  @Test
  void testBlueSquare() throws InterruptedException {
    setupWindow(VERTEX_COLOUR_SHADER_FILE_NAME, FRAGMENT_COLOUR_SHADER_FILE_NAME);
    Mesh testMesh = new Mesh(rectangleVertices, rectangleIndices);
    testMesh.setColour(ColourUtils.convertColor(ChartColor.BLUE));
    drawMesh(testMesh);
    testMesh.destroy();
    shutDownWindow();
  }

  @Test
  void testPinkTriangle() throws InterruptedException {
    setupWindow(VERTEX_COLOUR_SHADER_FILE_NAME, FRAGMENT_COLOUR_SHADER_FILE_NAME);
    Mesh testMesh = new Mesh(triangleVertices, triangleIndices);
    testMesh.setColour(ColourUtils.convertColor(ChartColor.PINK));
    drawMesh(testMesh);
    testMesh.destroy();
    shutDownWindow();
  }

  @Test
  void testDefaultImage() throws InterruptedException {
    setupWindow(VERTEX_IMAGE_SHADER_FILE_NAME, FRAGMENT_IMAGE_SHADER_FILE_NAME);
    Mesh testMesh = new Mesh(imageRectangleVertices, imageRectangleIndices);
    drawMesh(testMesh);
    testMesh.destroy();
    shutDownWindow();
  }

  @Test
  void testExistingImages() throws InterruptedException {
    for (String testImage : TEST_IMAGES_NAMES) {
      setupWindow(VERTEX_IMAGE_SHADER_FILE_NAME, FRAGMENT_IMAGE_SHADER_FILE_NAME);
      Mesh testMesh = new Mesh(imageRectangleVertices, imageRectangleIndices,
          new Material(TEST_IMAGES_DIRECTORY + testImage));
      drawMesh(testMesh);
      testMesh.destroy();
      shutDownWindow();
    }
  }
}
