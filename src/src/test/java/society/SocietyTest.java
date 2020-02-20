package society;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import society.person.Person;
import society.person.dataobjects.Gender;

class SocietyTest {
  static final int SIZE_OF_ARRAYS = 5;
  static final int UPPER_INT_LIMIT = 50;
  private static final int DEFAULT_POPULATION_SIZE = 2;
  Society society;
  int[] differentValues;
  float[] differentIndexValues;

  private int generateRandomInt() {
    Random r = new Random();
    return r.nextInt(UPPER_INT_LIMIT);
  }

  private float generateRandomFloat() {
    Random r = new Random();
    return r.nextFloat();
  }

  @BeforeEach
  public void setUp() {
    society = new Society(10, 0);
    differentValues = new int[SIZE_OF_ARRAYS];
    for (int i = 0; i < SIZE_OF_ARRAYS; i++) {
      differentValues[i] = abs(generateRandomInt());
    }
    differentIndexValues = new float[SIZE_OF_ARRAYS];
    for (int i = 0; i < SIZE_OF_ARRAYS; i++) {
      differentIndexValues[i] = generateRandomFloat();
    }
  }

  @Test
  void getPopulation() {
    assertEquals(society.getPopulation().size(), 10);
    for (int value : differentValues) {
      society = new Society(value, 0);
      assertEquals(society.getPopulation().size(), value);
    }
  }

  @Test
  void setPopulation() {
    assertEquals(society.getPopulation().size(), 10);
    for (int i = 0; i < SIZE_OF_ARRAYS; i++) {
      ArrayList<Person> newPopulation = new ArrayList<Person>();
      for (int j = 0; j < SIZE_OF_ARRAYS; j++) {
        Person newPerson = new Person(j, Gender.MALE);
        newPopulation.add(newPerson);
      }
      society.setPopulation(newPopulation);
      assertArrayEquals(new ArrayList[] {newPopulation},
          new ArrayList[] {society.getPopulation()});
    }
  }

  @Test
  void getSocietyId() {
    assertEquals(society.getSocietyId(), 0);
    for (int value : differentValues) {
      society.setSocietyId(value);
      assertEquals(society.getSocietyId(), value);
    }
  }

  @Test
  void getAverageTechnology() {
    assertEquals(society.getAverageTechnology(), 0.0f);
    for (float indexValue : differentIndexValues) {
      society.getPopulation().get(0).setTechnology(indexValue);
      society.setAverageTechnology(society.getPopulation());
      assertEquals(society.getAverageTechnology(),
          indexValue / society.getPopulation().size());
    }
  }

  @Test
  void getAverageMedicine() {
    assertEquals(society.getAverageMedicine(), 0.0f);
    for (float indexValue : differentIndexValues) {
      society.getPopulation().get(0).setMedicine(indexValue);
      society.setAverageMedicine(society.getPopulation());
      assertEquals(society.getAverageMedicine(),
          indexValue / society.getPopulation().size());
    }
  }

  @Test
  void getAverageAgriculture() {
    assertEquals(society.getAverageAgriculture(), 0.0f);
    for (float indexValue : differentIndexValues) {
      society.getPopulation().get(0).setAgriculture(indexValue);
      society.setAverageAgriculture(society.getPopulation());
      assertEquals(society.getAverageAgriculture(),
          indexValue / society.getPopulation().size());
    }
  }
}