package game.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.lwjgl.glfw.GLFW.glfwInit;

import engine.Window;
import engine.objects.world.Camera;
import engine.objects.world.TileWorldObject;
import engine.utils.ColourUtils;
import java.util.Random;
import map.MapGeneration;
import math.Vector2f;
import math.Vector3f;
import org.jfree.chart.ChartColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import society.Society;

public class WorldTest {
  private static final int WINDOW_X = 640;
  private static final int WINDOW_Y = 640;
  private static final String TITLE = "test";
  private static final Vector3f DEFAULT_POSITION = new Vector3f(0, 0, 10f);
  private static final Vector3f DEFAULT_ROTATION = new Vector3f(0, 0, 0);
  private static final int[] LAND_MASS_SIZES = new int[] {5, 6, 7, 10};
  private static final Vector3f[] BASIC_SOCIETY_COLORS = new Vector3f[] {
      ColourUtils.convertColor(ChartColor.DARK_MAGENTA),
      ColourUtils.convertColor(ChartColor.VERY_LIGHT_RED),
      ColourUtils.convertColor(ChartColor.VERY_DARK_GREEN),
      ColourUtils.convertColor(ChartColor.VERY_DARK_CYAN)};
  private static final int MAX_SOCIETY_NUMBER = 4;

  private Camera camera;
  private Window window;

  private static Vector2f calcCentre(TileWorldObject tile) {
    return new Vector2f(tile.getPosition().getX(), tile.getPosition().getY());
  }

  private static int genRandomInt(int maxNum) {
    Random r = new Random();
    int randomInt = r.nextInt(maxNum);
    if (randomInt == 0) {
      randomInt = 1;
    }
    return randomInt;
  }

  /**
   * Sets up.
   */
  @BeforeEach
  public void setUp() {
    assumeTrue(glfwInit());
    camera = new Camera(DEFAULT_POSITION, DEFAULT_ROTATION);
    window = new Window(WINDOW_X, WINDOW_Y, TITLE);
    MapGeneration.resetToDefaultValues();
    window.create();
  }

  /**
   * Cleanup.
   */
  @AfterEach
  public void cleanup() {
    assumeTrue(glfwInit());
    window.destroy();
  }

  private void genMap(int landmassSizeX, int landmassSizeY, int amountAridTiles,
                      int amountWaterTiles, int amountPlainTiles, int amountFertileTiles) {
    MapGeneration.setLandMassSizeX(landmassSizeX);
    MapGeneration.setLandMassSizeY(landmassSizeY);
    MapGeneration.setAmountOfAridTiles(amountAridTiles);
    MapGeneration.setAmountOfWaterTiles(amountWaterTiles);
    MapGeneration.setAmountOfPlainTiles(amountPlainTiles);
    MapGeneration.setAmountOfFertileTiles(amountFertileTiles);
    MapGeneration.createMap();
  }

  @Test
  void cameraBorders() {
    assertEquals(camera.getMaxCameraX(), Camera.getMinCameraBorder());
    for (int size : LAND_MASS_SIZES) {
      genMap(size, size, (size * size), 0, 0, 0);
      World.create(window, camera);
      Vector2f botLeft = calcCentre(World.getWorldMap()[World.getWorldMap().length - 1][0]);
      Vector2f topRight = calcCentre(World.getWorldMap()[0][World.getWorldMap().length - 1]);
      assertEquals(camera.getMinCameraX(), botLeft.getX());
      assertEquals(camera.getMaxCameraX(), topRight.getX());
      assertEquals(camera.getMaxCameraY(), topRight.getY());
      assertEquals(camera.getMinCameraY(), botLeft.getY());
    }
  }

  @Test
  void generateSocietiesNoSocietySizeSpecifiedTest() {
    World.create(window, camera);
    assertEquals(World.getSocieties().length, 2);
  }

  @Test
  void generateSocietiesNumberProvided() {
    int numSocieties = genRandomInt(MAX_SOCIETY_NUMBER);
    World.create(window, camera, numSocieties);
    assertEquals(World.getSocieties().length, numSocieties);
  }

  @Test
  void updateSocietiesBordersTest() {
    World.create(window, camera);
    for (Society society : World.getSocieties()) {
      for (TileWorldObject worldTile : society.getTerritory()) {
        assertNull(worldTile.getBorderObject());
      }
    }
    World.update(window, camera);
    for (Society society : World.getSocieties()) {
      for (TileWorldObject worldTile : society.getTerritory()) {
        assertNotNull(worldTile.getBorderObject());
      }
    }
  }
}