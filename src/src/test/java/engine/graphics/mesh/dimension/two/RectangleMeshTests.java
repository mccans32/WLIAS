package engine.graphics.mesh.dimension.two;

import static org.junit.jupiter.api.Assertions.assertEquals;

import engine.graphics.Material;
import engine.graphics.image.Image;
import engine.graphics.model.dimension.two.RectangleModel;
import org.junit.jupiter.api.Test;

public class RectangleMeshTests {
  private static final float[] TEST_HEIGHTS = {0.5f, 0.75f, 0.8f, 1.0f, 1.5f};
  private static final float[] TEST_WIDTHS = {0.2f, 0.8f, 0.6f, 3.0f, 9f};
  private static final String TEST_MATERIAL_PATH = "/images/fertileTile.jpg";

  @Test
  public void testConstructor() {
    RectangleMesh testMesh = new RectangleMesh(new RectangleModel(TEST_WIDTHS[0], TEST_HEIGHTS[0]),
        new Material(new Image(TEST_MATERIAL_PATH)));
    assertEquals(testMesh.getMaterial().getImage().getTexturePath(), TEST_MATERIAL_PATH);
  }

  @Test
  public void testGetters() {
    for (float width : TEST_WIDTHS) {
      for (float height: TEST_HEIGHTS) {
        RectangleMesh testMesh = new RectangleMesh(new RectangleModel(width, height));
        assertEquals(testMesh.getModel().getWidth(), width);
        assertEquals(testMesh.getModel().getHeight(), height);
      }
    }
  }
}
