package engine.objects.world;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import engine.Window;
import engine.graphics.Mesh;
import engine.graphics.Vertex3D;
import map.tiles.AridTile;
import map.tiles.FertileTile;
import map.tiles.PlainTile;
import map.tiles.Tile;
import map.tiles.WaterTile;
import math.Vector3f;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;

public class TileObjectsTests {
  private static final Vector3f DEFAULT_POSITION = new Vector3f(0, 0, 0);
  private static final Vector3f DEFAULT_ROTATION = new Vector3f(0, 0, 0);
  private static final Vector3f DEFAULT_SCALE = new Vector3f(1, 1, 1);
  private static final Vertex3D TOP_LEFT = new Vertex3D(new Vector3f(-1, 1, 0));
  private static final Vertex3D BOTTOM_LEFT = new Vertex3D(new Vector3f(-1, -1, 0));
  private static final Vertex3D TOP_RIGHT = new Vertex3D(new Vector3f(1, 1, 0));
  private static final Vertex3D BOTTOM_RIGHT = new Vertex3D(new Vector3f(1, -1, 0));
  private static final int[] INDICES = {0, 1, 2, 3};
  private Mesh defaultMesh;
  private TileObject waterTile;
  private TileObject aridTile;
  private TileObject fertileTile;
  private TileObject plainTile;
  private Window window;

  /**
   * Setup the test environment.
   */
  @BeforeEach
  public void setup() {
    assumeTrue(GLFW.glfwInit());
    defaultMesh = new Mesh(
        new Vertex3D[] {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT},
        INDICES);

    window = new Window(100, 100, "Test Window");
    window.setVisible(false);
    window.create();

    waterTile = new TileObject(
        DEFAULT_POSITION, DEFAULT_ROTATION, DEFAULT_SCALE, defaultMesh, new WaterTile());

    fertileTile = new TileObject(
        DEFAULT_POSITION, DEFAULT_ROTATION, DEFAULT_SCALE, defaultMesh, new FertileTile());

    aridTile = new TileObject(
        DEFAULT_POSITION, DEFAULT_ROTATION, DEFAULT_SCALE, defaultMesh, new AridTile());

    plainTile = new TileObject(
        DEFAULT_POSITION, DEFAULT_ROTATION, DEFAULT_SCALE, defaultMesh, new PlainTile());
  }

  @Test
  public void getTile() {
    assertTrue(waterTile.getTile() instanceof WaterTile);
    assertTrue(fertileTile.getTile() instanceof FertileTile);
    assertTrue(aridTile.getTile() instanceof AridTile);
    assertTrue(plainTile.getTile() instanceof PlainTile);
  }
}
