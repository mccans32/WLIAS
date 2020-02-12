package engine.graphics;

import math.Vector2f;
import math.Vector3f;

public class Vertex2D {
  private Vector2f position;
  private Vector3f colour;

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