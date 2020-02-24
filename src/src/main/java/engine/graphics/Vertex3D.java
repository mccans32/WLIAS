package engine.graphics;

import java.awt.Color;
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

  public Vertex3D(Vector3f position) {
    this.position = position;
  }

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