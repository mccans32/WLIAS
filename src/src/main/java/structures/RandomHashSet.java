package structures;

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

  public HashSet<T> getSet() {
    return set;
  }

  public ArrayList<T> getData() {
    return data;
  }

  /**
   * Gets a random object form the data.
   *
   * @return the random
   */
  public T getRandom() {
    if (set.size() > 0) {
      return data.get((int) (Math.random() * size()));
    }
    return null;
  }

  public int size() {
    return data.size();
  }

  /**
   * Add a new object to the data.
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
   * Adds a new gene sorted by its innovation number.
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
      data.add((T) object);
      set.add((T) object);
    }
  }

  public void clear() {
    set.clear();
    data.clear();
  }

  /**
   * Gets an object form the data form its index.
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

  public int indexOf(T object) {
    return data.indexOf(object);
  }

  /**
   * Remove an object from the data based off of its index.
   *
   * @param index the index
   */
  public void remove(int index) {
    if (index >= 0 && index < size()) {
      set.remove(data.get(index));
      data.remove(index);
    }
  }

  public void remove(T object) {
    set.remove(object);
    data.remove(object);
  }
}
