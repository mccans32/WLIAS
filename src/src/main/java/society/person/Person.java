package society.person;

import java.util.ArrayList;
import society.Society;
import society.person.dataobjects.Gender;
import society.person.dataobjects.SocietyOpinion;

public class Person {
  private static final float MAX_HEALTH = 100;
  private static final float MIN_HEALTH = 1;
  private static final float MAX_INDEX = 1.0f;
  private static final float MIN_INDEX = -1.0f;
  private static final float DEFAULT_INDEX = (MAX_INDEX + MIN_INDEX) / 2;
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
  private float opinionOfLeader;
  private SocietyOpinion[] opinionsOfSocieties;

  /**
   * Instantiates a new Person.
   *
   * @param id     the id
   * @param gender the gender
   */
  public Person(int id, Gender gender) {
    this.id = id;
    this.health = MAX_HEALTH;
    this.age = 0;
    this.gender = gender;
    this.aggressiveness = DEFAULT_INDEX;
    this.fertility = DEFAULT_INDEX;
    this.pathFinding = DEFAULT_INDEX;;
  }

  /**
   * Instantiates a new Person.
   *
   * @param id             the id
   * @param age            the age
   * @param gender         the gender
   * @param aggressiveness the aggressiveness
   * @param fertility      the fertility
   * @param pathFinding    the path finding
   */
  public Person(int id,
                int age,
                Gender gender,
                float aggressiveness,
                float fertility,
                float pathFinding,
                Society[] listOfSocieties) {
    this.id = id;
    this.health = MAX_HEALTH;
    this.age = age;
    this.gender = gender;
    this.aggressiveness = aggressiveness;
    this.fertility = fertility;
    this.pathFinding = pathFinding;
    setOpinions(listOfSocieties);

  }

  public static float getMaxHealth() {
    return MAX_HEALTH;
  }

  public static float getMinHealth() {
    return MIN_HEALTH;
  }

  public static float getMaxIndex() {
    return MAX_INDEX;
  }

  public static float getMinIndex() {
    return MIN_INDEX;
  }

  public static float getDefaultIndex() {
    return DEFAULT_INDEX;
  }

  public void setOpinions(Society[] listOfSocieties) {
    setOpinionOfLeader();
    setOpinionsOfSocieties(listOfSocieties);
  }

  public float getOpinionOfLeader() {
    return opinionOfLeader;
  }

  public void setOpinionOfLeader() {
    this.opinionOfLeader = calculateOpinionOfLeader();
  }

  private float calculateOpinionOfLeader() {
    return 0;
  }

  public SocietyOpinion[] getOpinionsOfSocieties() {
    return opinionsOfSocieties.clone();
  }

  /**
   * Sets opinions of societies.
   *
   * @param listOfSocieties the list of societies
   */
  public void setOpinionsOfSocieties(Society[] listOfSocieties) {
    this.opinionsOfSocieties = new SocietyOpinion[listOfSocieties.length - 1];
    for (int i = 0; i < listOfSocieties.length - 1; i++) {
      SocietyOpinion opinion = new SocietyOpinion(listOfSocieties[i].getSocietyId(),
              calculateOpinionOfSociety(listOfSocieties[i]));
      this.opinionsOfSocieties[i] = opinion;
    }
  }

  private float calculateOpinionOfSociety(Society listOfSociety) {
    return 0;
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
