package neat.genomes;

import java.util.ArrayList;
import java.util.HashSet;

public class RandomHashSet<T> {

  HashSet<T> set;
  ArrayList<T> data;

  public RandomHashSet() {
    set = new HashSet<>();
    data = new ArrayList<>();
  }

  public boolean contains(T object) {
    return set.contains(object);
  }

  /**
   * Gets a Random element.
   *
   * @return the element
   */
  public T randomElement() {
    if (set.size() > 0) {
      return data.get((int) (Math.random() * size()));
    }
    return null;
  }

  public int size() {
    return data.size();
  }

  /**
   * Adds a new element.
   *
   * @param object the object
   */
  public void add(T object) {
    if (!set.contains(object)) {
      set.add(object);
      data.add(object);
    }
  }

  /**
   * Add a new object sorted by its innovation number.
   *
   * @param object the object
   */
  @SuppressWarnings("unchecked")
  public void addSorted(Gene object) {
    for (int i = 0; i < this.size(); i++) {
      int innovation = ((Gene) data.get(i)).getInnovationNumber();
      if (object.getInnovationNumber() < innovation) {
        data.add(i, (T) object);
        set.add((T) object);
        return;
      }
    }
    data.add((T) object);
    set.add((T) object);
  }

  public void clear() {
    set.clear();
    data.clear();
  }

  /**
   * Gets an element based off of its index.
   *
   * @param index the index
   * @return the t
   */
  public T get(int index) {
    if (index < 0 || index >= size()) {
      return null;
    }
    return data.get(index);
  }

  /**
   * Removes an element given its index.
   *
   * @param index the index
   */
  public void remove(int index) {
    if (index < 0 || index >= size()) {
      return;
    }
    set.remove(data.get(index));
    data.remove(index);
  }

  public void remove(T object) {
    set.remove(object);
    data.remove(object);
  }

  public ArrayList<T> getData() {
    return data;
  }
}