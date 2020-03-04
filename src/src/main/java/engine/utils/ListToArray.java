package engine.utils;

import engine.graphics.Vertex3D;
import java.util.List;

public class ListToArray {

  /**
   * List vertex 3 d to vertex 3 d array vertex 3 d [ ].
   *
   * @param vertexList the vertex list
   * @return the vertex 3 d [ ]
   */
  public static Vertex3D[] listVertex3DToVertex3DArray(List<Vertex3D> vertexList) {
    Vertex3D[] vertexArray = new Vertex3D[vertexList.size()];
    for (int i = 0; i < vertexArray.length; i++) {
      vertexArray[i] = vertexList.get(i);
    }
    return vertexArray;
  }
}
