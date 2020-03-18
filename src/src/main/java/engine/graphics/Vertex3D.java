package engine.graphics;

import java.awt.Color;
import java.util.List;
import math.Vector2f;
import math.Vector3f;

public class Vertex3D {

  static final float DEFAULT_Z_VALUE = 0;
  private static final Color DEFAULT_COLOUR = Color.BLACK;
  private Vector3f position;
  private Vector2f textureCoordinates = new Vector2f(0f, 0f);
  private Vector3f colour = new Vector3f(DEFAULT_COLOUR.getRed(), DEFAULT_COLOUR.getGreen(),
      DEFAULT_COLOUR.getBlue());

  /**
   * Instantiates a new Vertex 2 d.
   *
   * @param position           the position
   * @param colour             the colour
   * @param textureCoordinates the texture coordinates
   */
  public Vertex3D(Vector3f position, Vector3f colour, Vector2f textureCoordinates) {
    this.position = position;
    this.colour = colour;
    this.textureCoordinates = textureCoordinates;
  }

  /**
   * Instantiates a new Vertex 3 d.
   *
   * @param position the position
   */
  public Vertex3D(Vector3f position) {
    this.position = position;
  }

  /**
   * Convert vertex 3 d.
   *
   * @param vertex the vertex
   * @return the vertex 3 d
   */
  public static Vertex3D convert(Vertex2D vertex) {
    Vector2f vertexPosition = vertex.getPosition();
    Vector3f vertexColour = vertex.getColour();
    Vector2f vertexTextureCoordinates = vertex.getTextureCoordinates();
    Vector3f newVertexPosition = new Vector3f(
        vertexPosition.getX(),
        vertexPosition.getY(),
        DEFAULT_Z_VALUE);

    return new Vertex3D(newVertexPosition, vertexColour, vertexTextureCoordinates);
  }

  /**
   * List to array vertex 3 d [ ].
   *
   * @param vertexList the vertex list
   * @return the vertex 3 d [ ]
   */
  public static Vertex3D[] listToArray(List<Vertex3D> vertexList) {
    Vertex3D[] vertexArray = new Vertex3D[vertexList.size()];
    for (int i = 0; i < vertexArray.length; i++) {
      vertexArray[i] = vertexList.get(i);
    }
    return vertexArray;
  }

  public Vector3f getPosition() {
    return position;
  }

  public Vector3f getColour() {
    return colour;
  }

  public void setColour(Vector3f colour) {
    this.colour = colour;
  }

  public Vector2f getTextureCoordinates() {
    return textureCoordinates;
  }

}