package engine.graphics;

import math.Vector2f;

public class Vertex2D {
  private Vector2f position;

  public Vertex2D(Vector2f position) {
    this.position = position;
  }

  public Vector2f getPosition() {
    return position;
  }
}