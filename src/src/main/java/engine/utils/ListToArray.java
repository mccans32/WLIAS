package engine.utils;

import engine.graphics.Vertex3D;
import java.util.List;

public class ListToArray {

  public static Vertex3D[] ListVertex3DToVertex3DArray(List<Vertex3D> vertexList) {
    Vertex3D[] vertexArray = new Vertex3D[vertexList.size()];
    for (int i = 0; i < vertexArray.length; i++) {
      vertexArray[i] = vertexList.get(i);
    }
    return vertexArray;
  }
}
