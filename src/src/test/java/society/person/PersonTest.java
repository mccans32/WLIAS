package society.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static society.person.Person.MAX_HEALTH;
import static society.person.Person.MAX_INDEX;
import static society.person.Person.MIN_HEALTH;
import static society.person.Person.MIN_INDEX;
import static society.person.gender.Gender.FEMALE;
import static society.person.gender.Gender.MALE;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonTest {
  static final int LOWER_INT_LIMIT = 1;
  static final int UPPER_INT_LIMIT = 50;
  static final int LOOP_ITERATIONS = 5;
  static int[] randomIntArray;
  static float[] randomFloatArray;
  static float[] randomHealthArray;
  Person person;

  @BeforeEach
  public void setUp() {
    randomIntArray = new int[LOOP_ITERATIONS];
    randomFloatArray = new float[LOOP_ITERATIONS];
    for (int i = 0; i < LOOP_ITERATIONS; i++) {
      randomIntArray[i] = generateRandomInt();
      randomFloatArray[i] = generateRandomFloat();
    }
    person = new Person(1, MAX_HEALTH, 0, MALE, 0.0f, 0.0f, 0.0f);
  }

  private float generateRandomFloat() {
    Random r = new Random();
    return MIN_INDEX + r.nextFloat() * (MAX_INDEX - MIN_INDEX);
  }

  private int generateRandomInt() {
    Random r = new Random();
    return LOWER_INT_LIMIT + r.nextInt() * (UPPER_INT_LIMIT - LOWER_INT_LIMIT);
  }

  private float generateRandomHealth() {
    Random r = new Random();
    return MIN_HEALTH + r.nextInt() * (MAX_HEALTH - MIN_HEALTH);
  }

  @Test
  void idTest() {
    assertEquals(person.getId(),1);
    for (int value : randomIntArray) {
      person.setId(value);
      assertEquals(person.getId(), value);
    }
  }

  @Test
  void healthTest() {
    randomHealthArray = new float[LOOP_ITERATIONS];
    for(int i = 0; i < LOOP_ITERATIONS; i++){
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