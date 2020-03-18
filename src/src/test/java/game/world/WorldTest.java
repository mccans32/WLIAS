package game.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import engine.Window;
import engine.graphics.Vertex3D;
import engine.objects.world.Camera;
import engine.objects.world.TileWorldObject;
import java.util.ArrayList;
import java.util.List;
import map.MapGeneration;
import math.Vector2f;
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

  private static Vector2f calcCentre(TileWorldObject tile) {
    return new Vector2f(tile.getPosition().getX(), tile.getPosition().getY());
  }

  @BeforeEach
  public void setUp() {
    camera = new Camera(DEFAULT_POSITION, DEFAULT_ROTATION);
    window = new Window(WINDOW_X, WINDOW_Y, TITLE);
    genMap(MapGeneration.getDefaultLandmassSizeX(), MapGeneration.getDefaultLandmassSizeY(),
        MapGeneration.getDefaultAmountAridTiles(), MapGeneration.getDefaultAmountWaterTiles(),
        MapGeneration.getDefaultAmountPlainTiles(), MapGeneration.getDefaultAmountFertileTiles());
    window.create();
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
  void createWorldMap() {
    assertNull(World.getWorldMap());
    World.create(window, camera);
    assertNotNull(World.getWorldMap());
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
  void addToVertexListTest() {
    List<Vertex3D> vertexList = new ArrayList<>();
    assertTrue(vertexList.isEmpty());
    World.addToVertexList(vertexList);
    assertFalse(vertexList.isEmpty());
  }
}