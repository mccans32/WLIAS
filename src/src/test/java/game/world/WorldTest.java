package game.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import engine.Window;
import engine.objects.world.Camera;
import map.MapGeneration;
import math.Vector3f;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorldTest {
  private static final int WINDOW_X = 640;
  private static final int WINDOW_Y = 640;
  private static final String TITLE = "test";
  private static final Vector3f DEFAULT_POSITION = new Vector3f(0, 0, 10f);
  private static final Vector3f DEFAULT_ROTATION = new Vector3f(0, 0, 0);
  private static final int[] LAND_MASS_SIZES = new int[] {5, 6, 7, 10};

  private Camera camera;
  private Window window;

  @BeforeEach
  public void setUp() {
    camera = new Camera(DEFAULT_POSITION, DEFAULT_ROTATION);
    window = new Window(WINDOW_X, WINDOW_Y, TITLE);
    MapGeneration.setLandMassSizeX(LAND_MASS_SIZES[0]);
    MapGeneration.setLandMassSizeY(LAND_MASS_SIZES[0]);
    MapGeneration.setAmountOfAridTiles(LAND_MASS_SIZES[0] * LAND_MASS_SIZES[0]);
    MapGeneration.setAmountOfWaterTiles(0);
    MapGeneration.setAmountOfPlainTiles(0);
    MapGeneration.setAmountOfFertileTiles(0);
    MapGeneration.createMap();
    window.create();
  }

  @Test
  void create() {
    assertNull(World.getWorldMap());
    World.create(window, camera);
    assertNotNull(World.getWorldMap());
  }

  @Test
  void cameraBorders() {
    assertEquals(camera.getMaxCameraX(), Camera.getMinCameraBorder());
    World.create(window, camera);
    assertNotEquals(camera.getMaxCameraX(), Camera.getMinCameraBorder());
  }
}