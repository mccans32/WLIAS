package structures;

import java.util.ArrayList;

public class RandomSelector<T> {

  private ArrayList<T> objects = new ArrayList<>();
  private ArrayList<Double> scores = new ArrayList<>();

  private double totalScore = 0;

  /**
   * Add a new object.
   *
   * @param object the object
   * @param score  the score
   */
  public void add(T object, double score) {
    objects.add(object);
    scores.add(score);
    totalScore += score;
  }

  /**
   * Gets a random object based off score.
   *
   * @return the random
   */
  public T getRandom() {
    double v = Math.random() * totalScore;
    double c = 0;
    for (int i = 0; i < objects.size(); i++) {
      c += scores.get(i);
      if (c > v) {
        return objects.get(i);
      }
    }
    return null;
  }

  /**
   * Reset everything.
   */
  public void reset() {
    objects.clear();
    scores.clear();
    totalScore = 0;
  }
}
