package map.tile;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import map.tiles.AridTile;
import map.tiles.FertileTile;
import map.tiles.PlainTile;
import map.tiles.Tile;
import map.tiles.WaterTile;
import org.junit.jupiter.api.Test;

public class TileTest {
  private static final float[] VALUES = new float[] {0.5f, 0.3f, 0.25f, 0.7f};
  public Tile aridTile;
  public Tile waterTile;
  public Tile plainTile;
  public Tile fertileTile;
  public float[] positionsX = {2.0f, 3.0f, 1.0f, 5.0f, 6.0f};
  public float[] positionsY = {7.0f, 1.0f, 2.5f, 3.7f, 8.1f};

  @Test
  public void aridTileTest() {
    assertNull(aridTile);
    aridTile = new AridTile();
    assertNotNull(aridTile);
  }

  @Test
  public void waterTileTest() {
    assertNull(waterTile);
    waterTile = new WaterTile();
    assertNotNull(waterTile);
  }

  @Test
  public void plainTileTest() {
    assertNull(plainTile);
    plainTile = new PlainTile();
    assertNotNull(plainTile);
  }

  @Test
  public void fertileTileTest() {
    assertNull(fertileTile);
    fertileTile = new FertileTile();
    assertNotNull(fertileTile);
  }

  @Test
  public void positionsTest() {
    aridTile = new AridTile();
    waterTile = new WaterTile();
    plainTile = new PlainTile();
    fertileTile = new FertileTile();
    Tile[] tiles = new Tile[] {fertileTile, aridTile, plainTile, waterTile};
    for (Tile tile : tiles) {
      for (int i = 0; i < positionsX.length; i++) {
        tile.setPositionX(positionsX[i]);
        tile.setPositionY(positionsY[i]);
        assertEquals(tile.getPositionX(), positionsX[i]);
        assertEquals(tile.getPositionY(), positionsY[i]);
        tile.setPositions(positionsX[i], positionsY[i]);
        assertArrayEquals(tile.getPositions(), new float[] {positionsX[i], positionsY[i]});
      }
    }
  }

  @Test
  public void terrainHealthTest() {
    aridTile = new AridTile();
    waterTile = new WaterTile();
    plainTile = new PlainTile();
    fertileTile = new FertileTile();
    Tile[] tiles = new Tile[] {fertileTile, aridTile, plainTile, waterTile};
    for (Tile tile : tiles) {
      for (float value : VALUES) {
        tile.setTerrainHealth(value);
        assertEquals(tile.getTerrainHealth(), value);
      }
    }
  }
}
