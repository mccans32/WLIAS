package engine.graphics;

import java.awt.Color;
import math.Vector2f;
import math.Vector3f;

public class Vertex2D {
  private static final Color DEFAULT_COLOUR = Color.BLACK;
  private Vector2f position;
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
  public Vertex2D(Vector2f position, Vector3f colour, Vector2f textureCoordinates) {
    this.position = position;
    this.colour = colour;
    this.textureCoordinates = textureCoordinates;
  }

  public Vertex2D(Vector2f position) {
    this.position = position;
  }

  public Vector2f getPosition() {
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