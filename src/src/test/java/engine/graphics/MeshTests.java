package engine.graphics;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.lwjgl.glfw.GLFW.glfwInit;

import engine.Window;
import math.Vector3f;
import org.junit.jupiter.api.Test;


/**
 * The type Mesh tests.
 */
public class MeshTests {
  Vertex3D testVertex = new Vertex3D(new Vector3f(-0.5f, 0.5f, 0));
  Vertex3D[] testVertices = {testVertex};
  int[] testIndices = {0, 0, 0};
  Window window;

  @Test
  public void testParams() {
    assumeTrue(glfwInit());
    window = new Window(600, 800, "Test Window");
    window.setVisible(false);
    window.create();
    Mesh testMesh = new Mesh(testVertices, testIndices);
    testMesh.create();
    assertTrue(testMesh.getIbo() != 0);
    assertTrue(testMesh.getPbo() != 0);
    assertTrue(testMesh.getVao() != 0);
    window.destroy();
  }
}
