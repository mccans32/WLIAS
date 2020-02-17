package test.map.tile;

import map.tiles.*;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {
    public Tile aridTile;
    public Tile waterTile;
    public Tile plainTile;
    public Tile fertileTile;
    public float[] xPositions = {2.0f, 3.0f, 1.0f, 5.0f, 6.0f};
    public float[] yPositions = {7.0f, 1.0f, 2.5f, 3.7f, 8.1f};
    private static final float[] VALUES = new float[]{0.5f, 0.3f, 0.25f, 0.7f};


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
        Tile[] tiles = new Tile[]{fertileTile, aridTile, plainTile, waterTile};
        for (Tile tile : tiles){
            for (int i = 0; i < xPositions.length; i++){
                tile.setPositionX(xPositions[i]);
                tile.setPositionY(yPositions[i]);
                assertEquals(tile.getPositionX(), xPositions[i]);
                assertEquals(tile.getPositionY(), yPositions[i]);
                tile.setPositions(xPositions[i], yPositions[i]);
                assertArrayEquals(tile.getPositions(), new float[]{xPositions[i], yPositions[i]});
            }
        }
    }

    @Test
    public void terrainHealthTest() {
        aridTile = new AridTile();
        waterTile = new WaterTile();
        plainTile = new PlainTile();
        fertileTile = new FertileTile();
        Tile[] tiles = new Tile[]{fertileTile, aridTile, plainTile, waterTile};
        for (Tile tile: tiles){
            for (float value: VALUES) {
                tile.setTerrainHealth(value);
                assertEquals(tile.getTerrainHealth(), value);
            }
        }
    }
}
