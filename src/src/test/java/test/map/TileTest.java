package test.map;

import map.Tile;
import map.tiles.AridTile;
import map.tiles.FertileTile;
import map.tiles.PlainTile;
import map.tiles.WaterTile;
import org.junit.jupiter.api.BeforeEach;

public class TileTest {
  Tile aridTile;
  Tile waterTile;
  Tile fertileTile;
  Tile plainTile;

  /*
  Tests will be added here when Tile Logic is implemented
   */
  @BeforeEach
  public void setUp() {
    aridTile = new AridTile();
    waterTile = new WaterTile();
    fertileTile = new FertileTile();
    plainTile = new PlainTile();
  }
}
