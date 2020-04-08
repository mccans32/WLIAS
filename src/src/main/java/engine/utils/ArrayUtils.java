package engine.utils;

import engine.graphics.Vertex3D;
import java.util.List;

public class ArrayUtils {

  /**
   * Vertex 3 d list to array vertex 3 d [ ].
   *
   * @param vertexList the vertex list
   * @return the vertex 3 d [ ]
   */
  public static Vertex3D[] vertex3DListToArray(List<Vertex3D> vertexList) {
    Vertex3D[] vertexArray = new Vertex3D[vertexList.size()];
    for (int i = 0; i < vertexArray.length; i++) {
      vertexArray[i] = vertexList.get(i);
    }
    return vertexArray;
  }

  /**
   * Integer list to int array int [ ].
   *
   * @param integerList the integer list
   * @return the int [ ]
   */
  public static int[] integerListToIntArray(List<Integer> integerList) {
    int[] intArray = new int[integerList.size()];
    for (int i = 0; i < intArray.length; i++) {
      intArray[i] = integerList.get(i);
    }
    return intArray;
  }
}
