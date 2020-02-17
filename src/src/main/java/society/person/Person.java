package society.person;

import society.person.gender.Gender;

public class Person {

  static final float MAX_HEALTH = 100;
  static final float MIN_HEALTH = 1;
  static final float MAX_INDEX = 1.0f;
  static final float MIN_INDEX = -1.0f;
  private int id;
  private float health;
  private int age;
  private Gender gender;
  private float aggressiveness;
  private float fertility;
  private float pathFinding;
  private float technology;
  private float agriculture;
  private float medicine;

  /**
   * Instantiates a new Person.
   *
   * @param id             the id
   * @param health         the health
   * @param age            the age
   * @param gender         the gender
   * @param aggressiveness the aggressiveness
   * @param fertility      the fertility
   * @param pathFinding    the path finding
   */
  public Person(int id,
                float health,
                int age,
                Gender gender,
                float aggressiveness,
                float fertility,
                float pathFinding) {
    this.id = id;
    this.health = health;
    this.age = age;
    this.gender = gender;
    this.aggressiveness = aggressiveness;
    this.fertility = fertility;
    this.pathFinding = pathFinding;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public float getHealth() {
    return health;
  }

  public void setHealth(float health) {
    this.health = health;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public float getAggressiveness() {
    return aggressiveness;
  }

  public void setAggressiveness(float aggressiveness) {
    this.aggressiveness = aggressiveness;
  }

  public float getFertility() {
    return fertility;
  }

  public void setFertility(float fertility) {
    this.fertility = fertility;
  }

  public float getPathFinding() {
    return pathFinding;
  }

  public void setPathFinding(float pathFinding) {
    this.pathFinding = pathFinding;
  }

  public float getTechnology() {
    return technology;
  }

  public void setTechnology(float technology) {
    this.technology = technology;
  }

  public float getAgriculture() {
    return agriculture;
  }

  public void setAgriculture(float agriculture) {
    this.agriculture = agriculture;
  }

  public float getMedicine() {
    return medicine;
  }

  public void setMedicine(float medicine) {
    this.medicine = medicine;
  }
}
