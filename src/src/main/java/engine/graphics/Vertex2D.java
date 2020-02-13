package engine.graphics;

import java.awt.Color;
import math.Vector2f;
import math.Vector3f;

public class Vertex2D {
  private final static Color DEFAULT_COLOUR = Color.BLACK;
  private Vector2f position;
  private Vector3f colour = new Vector3f(DEFAULT_COLOUR.getRed(), DEFAULT_COLOUR.getGreen(),
      DEFAULT_COLOUR.getBlue());

  public Vertex2D(Vector2f position) {
    this.position = position;
  }

  public Vertex2D(Vector2f position, Vector3f colour) {
    this.position = position;
    this.colour = colour;
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
}