package engine.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import engine.graphics.Vertex3D;
import java.util.ArrayList;
import math.Vector3f;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ArrayUtilsTests {
  private static final Vector3f DEFAULT_POSITION = new Vector3f(0f, 0f, 0f);
  private static ArrayList<ArrayList<Integer>> intLists = new ArrayList<>();
  private static ArrayList<ArrayList<Vertex3D>> vertexLists = new ArrayList<>();

  @BeforeAll
  static void initialise() {
    initialiseIntLists();
    initialiseVertexLists();
  }

  private static void initialiseIntLists() {
    ArrayList<Integer> a1 = new ArrayList<>();
    a1.add(1);
    a1.add(2);
    a1.add(3);
    intLists.add(a1);
    ArrayList<Integer> a2 = new ArrayList<>();
    a2.add(4);
    a2.add(5);
    a2.add(6);
    intLists.add(a2);
    ArrayList<Integer> a3 = new ArrayList<>();
    a3.add(-1);
    a3.add(0);
    intLists.add(a3);
    ArrayList<Integer> a4 = new ArrayList<>();
    intLists.add(a4);
  }

  private static void initialiseVertexLists() {
    ArrayList<Vertex3D> a1 = new ArrayList<>();
    a1.add(new Vertex3D(DEFAULT_POSITION));
    a1.add(new Vertex3D(DEFAULT_POSITION));
    a1.add(new Vertex3D(DEFAULT_POSITION));
    vertexLists.add(a1);
  }

  private void testLists(ArrayList<Object> lists, Object[] array) {
    for (ArrayList<?> list : intLists) {
      assertEquals(list.size(), array.length);
      if (list.size() > 0) {
        for (int i = 0; i < list.size(); i++) {
          Object lstElement = list.get(i);
          Object arrayElement = array[i];
          assertEquals(lstElement, arrayElement);
        }
      }
    }
  }


  @Test
  public void testIntegerList() {
    for (ArrayList<Integer> list : intLists) {
      int[] array = ArrayUtils.integerListToIntArray(list);
      assertEquals(list.size(), array.length);
      if (list.size() > 0) {
        for (int i = 0; i < list.size(); i++) {
          int lstElement = list.get(i);
          int arrayElement = array[i];
          assertEquals(lstElement, arrayElement);
        }
      }
    }
  }

  @Test
  public void testVertexList() {
    for (ArrayList<Vertex3D> list : vertexLists) {
      Vertex3D[] array = ArrayUtils.vertex3DListToArray(list);
      assertEquals(list.size(), array.length);
      if (list.size() > 0) {
        for (int i = 0; i < list.size(); i++) {
          Vertex3D lstElement = list.get(i);
          Vertex3D arrayElement = array[i];
          assertEquals(lstElement, arrayElement);
        }
      }
    }
  }
}