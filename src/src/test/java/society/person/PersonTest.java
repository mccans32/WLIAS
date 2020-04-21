package society.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import engine.utils.ColourUtils;
import java.util.Arrays;
import java.util.Random;
import math.Vector3f;
import org.jfree.chart.ChartColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import society.Society;

class PersonTest {
  static final int LOWER_INT_LIMIT = 1;
  static final int UPPER_INT_LIMIT = 50;
  static final int LOOP_ITERATIONS = 5;
  private static final Vector3f[] BASIC_SOCIETY_COLORS = new Vector3f[] {
      ColourUtils.convertColor(ChartColor.DARK_MAGENTA),
      ColourUtils.convertColor(ChartColor.VERY_LIGHT_RED),
      ColourUtils.convertColor(ChartColor.VERY_DARK_GREEN),
      ColourUtils.convertColor(ChartColor.VERY_DARK_CYAN)};
  int[] randomIntArray;
  float[] randomFloatArray;
  float[] randomHealthArray;
  Person person;
  Society[] listOfSocieties = new Society[LOOP_ITERATIONS];

  @BeforeEach
  public void setUp() {
    Society society = new Society(0, BASIC_SOCIETY_COLORS[0]);
    Arrays.fill(listOfSocieties, society);
    randomIntArray = new int[LOOP_ITERATIONS];
    randomFloatArray = new float[LOOP_ITERATIONS];
    for (int i = 0; i < LOOP_ITERATIONS; i++) {
      randomIntArray[i] = generateRandomInt();
      randomFloatArray[i] = generateRandomFloat();
    }
    person = new Person(0, 0.0f, 0.0f, 0);
  }

  private float generateRandomFloat() {
    Random r = new Random();
    return Person.getMinIndex() + (Person.getMaxIndex() - Person.getMinIndex()) * r.nextFloat();
  }

  private int generateRandomInt() {
    Random r = new Random();
    return r.nextInt(UPPER_INT_LIMIT) + LOWER_INT_LIMIT;
  }

  private float generateRandomHealth() {
    Random r = new Random();
    return r.nextInt((int) Person.getMaxHealth()) + Person.getMinHealth();
  }

  @Test
  void healthTest() {
    randomHealthArray = new float[LOOP_ITERATIONS];
    for (int i = 0; i < LOOP_ITERATIONS; i++) {
      randomHealthArray[i] = generateRandomHealth();
    }
    assertEquals(person.getHealth(), 100);
    for (float value : randomHealthArray) {
      person.setHealth(value);
      assertEquals(person.getHealth(), value);
    }
  }

  @Test
  void ageTest() {
    assertEquals(person.getAge(), 0);
    for (int value : randomIntArray) {
      person.setAge(value);
      assertEquals(person.getAge(), value);
    }
  }


  @Test
  void getAggressivenessTest() {
    assertEquals(person.getAggressiveness(), 0.0f);
    for (float value : randomFloatArray) {
      person.setAggressiveness(value);
      assertEquals(person.getAggressiveness(), value);
    }
  }

  @Test
  void getProductivityTest() {
    assertEquals(person.getProductiveness(), 0.0f);
    for (float value : randomFloatArray) {
      person.setProductiveness(value);
      assertEquals(person.getProductiveness(), value);
    }
  }

  @Test
  void fertilityTest() {
    assertEquals(person.getAttractiveness(), 0.0f);
    for (float value : randomFloatArray) {
      person.setAttractiveness(value);
      assertEquals(person.getAttractiveness(), value);
    }
  }

  @Test
  void pathFindingTest() {
    assertEquals(person.getAggressiveness(), 0.0f);
    for (float value : randomFloatArray) {
      person.setAggressiveness(value);
      assertEquals(person.getAggressiveness(), value);
    }
  }
}