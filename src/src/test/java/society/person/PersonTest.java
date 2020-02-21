package society.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static society.person.dataobjects.Gender.FEMALE;
import static society.person.dataobjects.Gender.MALE;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import society.Society;

class PersonTest {
  static final int LOWER_INT_LIMIT = 1;
  static final int UPPER_INT_LIMIT = 50;
  static final int LOOP_ITERATIONS = 5;
  int[] randomIntArray;
  float[] randomFloatArray;
  float[] randomHealthArray;
  Person person;
  Society[] listOfSocieties = new Society[LOOP_ITERATIONS];

  @BeforeEach
  public void setUp() {
    Society society = new Society(0,LOWER_INT_LIMIT);
    Arrays.fill(listOfSocieties, society);
    randomIntArray = new int[LOOP_ITERATIONS];
    randomFloatArray = new float[LOOP_ITERATIONS];
    for (int i = 0; i < LOOP_ITERATIONS; i++) {
      randomIntArray[i] = generateRandomInt();
      randomFloatArray[i] = generateRandomFloat();
    }
    person = new Person(1, 0, MALE, 0.0f, 0.0f, 0.0f, listOfSocieties);
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
  void idTest() {
    assertEquals(person.getId(), 1);
    for (int value : randomIntArray) {
      person.setId(value);
      assertEquals(person.getId(), value);
    }
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
  void genderTest() {
    assertEquals(person.getGender(), MALE);
    person.setGender(FEMALE);
    assertEquals(person.getGender(), FEMALE);
  }


  @Test
  void getAggressiveness() {
    assertEquals(person.getAggressiveness(), 0.0f);
    for (float value : randomFloatArray) {
      person.setAggressiveness(value);
      assertEquals(person.getAggressiveness(), value);
    }
  }

  @Test
  void fertilityTest() {
    assertEquals(person.getFertility(), 0.0f);
    for (float value : randomFloatArray) {
      person.setFertility(value);
      assertEquals(person.getFertility(), value);
    }
  }

  @Test
  void pathFindingTest() {
    assertEquals(person.getPathFinding(), 0.0f);
    for (float value : randomFloatArray) {
      person.setPathFinding(value);
      assertEquals(person.getPathFinding(), value);
    }
  }

  @Test
  void technologyTest() {
    assertEquals(person.getTechnology(), 0.0f);
    for (float value : randomFloatArray) {
      person.setTechnology(value);
      assertEquals(person.getTechnology(), value);
    }
  }

  @Test
  void agricultureTest() {
    assertEquals(person.getAgriculture(), 0.0f);
    for (float value : randomFloatArray) {
      person.setAgriculture(value);
      assertEquals(person.getAgriculture(), value);
    }
  }

  @Test
  void medicineTest() {
    assertEquals(person.getMedicine(), 0.0f);
    for (float value : randomFloatArray) {
      person.setMedicine(value);
      assertEquals(person.getMedicine(), value);
    }
  }
}