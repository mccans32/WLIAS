package test.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import map.MapGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MapGenerationTest {

  public MapGenerator map;

  @BeforeEach
  public void startUp() {
    map = new MapGenerator(10, 10, 25, 25, 25, 25, 1);
  }

  @Test
  public void mapCreationTest() {
    assertNull(map.getSimulationMap());
    map.createMap();
    assertNotNull(map.getSimulationMap());
  }

  @Test
  public void multipleContinentsMapSizeTest() {
    int randomLimit = generateRandomInt();
    for (int numberOfLandMasses = 1; numberOfLandMasses < randomLimit; numberOfLandMasses++) {
      map = new MapGenerator(10, 10, 25, 25, 25, 25, numberOfLandMasses);
      map.createMap();
      assertEquals(map.mapSizeX,
          ((map.getLandMassSizeX() * numberOfLandMasses) + (map.landMassMaps.size() + 1)));
    }
  }

  private int generateRandomInt() {
    int upperLimit = 5;
    Random randomInt = new Random();
    int randomLimit = randomInt.nextInt(upperLimit);
    if (randomLimit == 0) {
      return 1;
    } else {
      return randomLimit;
    }
  }

  @Test
  public void notEnoughTilesTest() {
    map = new MapGenerator(generateRandomInt(), generateRandomInt(),
        generateRandomInt(), generateRandomInt(),
        generateRandomInt(), generateRandomInt(), generateRandomInt());
    assertThrows(AssertionError.class, () -> map.createMap());
  }
}
