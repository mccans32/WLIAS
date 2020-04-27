package neat.genomes;

import java.util.ArrayList;

public class RandomSelector<T> {

  private ArrayList<T> objects = new ArrayList<>();
  private ArrayList<Double> scores = new ArrayList<>();

  private double totalScore = 0;

  /**
   * Add.
   *
   * @param element the element
   * @param score   the score
   */
  public void add(T element, double score) {
    objects.add(element);
    scores.add(score);
    totalScore += score;
  }

  /**
   * Get a random object.
   *
   * @return the t
   */
  public T random() {
    double v = Math.random() * totalScore;
    double c = 0;
    for (int i = 0; i < objects.size(); i++) {
      c += scores.get(i);
      if (c >= v) {
        return objects.get(i);
      }
    }
    return null;
  }

  /**
   * Reset.
   */
  public void reset() {
    objects.clear();
    scores.clear();
    totalScore = 0;
  }

}