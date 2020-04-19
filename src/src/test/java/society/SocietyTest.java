package society;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import engine.graphics.Material;
import engine.graphics.image.Image;
import engine.graphics.mesh.Mesh;
import engine.graphics.model.dimension.two.RectangleModel;
import engine.objects.world.TileWorldObject;
import engine.utils.ColourUtils;
import game.world.World;
import java.util.ArrayList;
import java.util.Random;
import map.tiles.AridTile;
import map.tiles.Tile;
import math.Vector3f;
import math.Vector4f;
import org.jfree.chart.ChartColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import society.person.Person;
import society.person.dataobjects.Gender;

class SocietyTest {
  static final int SIZE_OF_ARRAYS = 5;
  static final int UPPER_INT_LIMIT = 50;
  private static final int DEFAULT_ROW = 0;
  private static final int DEFAULT_COLUMN = 0;
  private static final float DEFAULT_FLOAT_INDEX = 0.5f;
  private static final Vector3f[] BASIC_SOCIETY_COLORS = new Vector3f[] {
      ColourUtils.convertColor(ChartColor.DARK_MAGENTA),
      ColourUtils.convertColor(ChartColor.VERY_LIGHT_RED),
      ColourUtils.convertColor(ChartColor.VERY_DARK_GREEN),
      ColourUtils.convertColor(ChartColor.VERY_DARK_CYAN)};
  private static final int DEFAULT_POPULATION_SIZE = 2;
  private static final float BORDER_ALPHA = 0.02f;
  private static RectangleModel tileModel = World.getTileModel();
  Society society;
  int[] differentValues;
  float[] differentIndexValues;
  private TileWorldObject temp;

  private int generateRandomInt() {
    Random r = new Random();
    return r.nextInt(UPPER_INT_LIMIT - DEFAULT_POPULATION_SIZE)
        + DEFAULT_POPULATION_SIZE;
  }

  private int generateRandomInt(int maxInt, int minInt) {
    Random r = new Random();
    return r.nextInt(maxInt) + minInt;
  }

  private float generateRandomFloat() {
    Random r = new Random();
    return r.nextFloat();
  }

  @BeforeEach
  public void setUp() {
    temp = new TileWorldObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0),
        new Vector3f(0, 0, 0), new Mesh(tileModel,
        new Material(new Image("resources/images/default_texture.png"))),
        new AridTile(), 0, 0);
    society = new Society(1, BASIC_SOCIETY_COLORS[0]);
    differentValues = new int[SIZE_OF_ARRAYS];
    for (int i = 0; i < SIZE_OF_ARRAYS; i++) {
      differentValues[i] = abs(generateRandomInt());
    }
    differentIndexValues = new float[SIZE_OF_ARRAYS];
    for (int i = 0; i < SIZE_OF_ARRAYS; i++) {
      differentIndexValues[i] = generateRandomFloat();
    }
  }

  @AfterEach
  public void restart() {
    society = new Society(1, BASIC_SOCIETY_COLORS[0]);
  }

  @Test
  void getPopulation() {
    assertEquals(society.getPopulation().size(), Society.getDefaultPopulationSize());
  }

  @Test
  void setPopulation() {
    assertEquals(society.getPopulation().size(), Society.getDefaultPopulationSize());
    for (int i = 0; i < SIZE_OF_ARRAYS; i++) {
      ArrayList<Person> newPopulation = new ArrayList<>();
      for (int j = 0; j < SIZE_OF_ARRAYS; j++) {
        Person newPerson = new Person();
        newPopulation.add(newPerson);
      }
      society.setPopulation(newPopulation);
      assertArrayEquals(new ArrayList[] {newPopulation},
          new ArrayList[] {society.getPopulation()});
    }
  }

  @Test
  void getSocietyId() {
    assertEquals(society.getSocietyId(), 1);
    for (int value : differentValues) {
      society.setSocietyId(value);
      assertEquals(society.getSocietyId(), value);
    }
  }

  @Test
  void addTerritoryTest() {
    assertEquals(society.getTerritory().size(), 0);
    Material borderMaterial = new Material(new Image("/images/tileBorder.png"),
        new Vector4f(society.getSocietyColor(), BORDER_ALPHA));
    Tile tile = new AridTile();
    society.claimTile(new TileWorldObject(new Vector3f(0, 0, 0),
        new Vector3f(0, 0, 0), new Vector3f(0, 0, 0),
        new Mesh(tileModel, borderMaterial), tile, DEFAULT_ROW, DEFAULT_COLUMN));
    assertEquals(society.getTerritory().size(), 1);
  }

  @Test
  void totalFoodResourceTest() {
    int randomIntOne = generateRandomInt(5, 1);
    int randomIntTwo = generateRandomInt(5, 1);
    assertEquals(society.getTotalFoodResource(), 0);
    temp.setFoodResource(randomIntOne);
    temp.setRawMaterialResource(randomIntTwo);
    society.claimTile(temp);
    society.calculateResources();
    assertEquals(society.getTotalFoodResource(), randomIntOne);
    assertEquals(society.getTotalRawMaterialResource(), randomIntTwo);
  }
}