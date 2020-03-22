package engine.graphics.mesh;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.lwjgl.glfw.GLFW.glfwInit;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Vertex3D;
import engine.graphics.mesh.Mesh;
import engine.graphics.model.Model;
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
    Mesh testMesh = new Mesh(new Model(testVertices, testIndices), new Material());
    testMesh.create();
    assertTrue(testMesh.getModel().getIbo() != 0);
    assertTrue(testMesh.getModel().getPbo() != 0);
    assertTrue(testMesh.getModel().getVao() != 0);
    window.destroy();
  }
}
