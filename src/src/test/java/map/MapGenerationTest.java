package map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class MapGenerationTest {

  private static final int UPPER_LIMIT = 5;
  
  @AfterEach
  public void destroy() {
    MapGeneration.resetToDefaultValues();
  }

  private int generateRandomInt() {
    Random randomInt = new Random();
    int randomLimit = randomInt.nextInt(UPPER_LIMIT);
    if (randomLimit == 0) {
      return 1;
    } else {
      return randomLimit;
    }
  }

  @Test
  public void notEnoughTilesTest() {
    MapGeneration.setLandMassSizeX(0);
    MapGeneration.setLandMassSizeY(0);
    MapGeneration.setAmountOfAridTiles(generateRandomInt());
    MapGeneration.setAmountOfFertileTiles(generateRandomInt());
    MapGeneration.setAmountOfPlainTiles(generateRandomInt());
    MapGeneration.setAmountOfWaterTiles(generateRandomInt());
    assertThrows(AssertionError.class, MapGeneration::createMap);
  }
}
