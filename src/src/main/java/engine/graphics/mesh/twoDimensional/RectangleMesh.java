package engine.graphics.mesh.twoDimensional;

import engine.graphics.Material;
import engine.graphics.Vertex3D;
import engine.graphics.mesh.Mesh;
import math.Vector2f;
import math.Vector3f;

public class RectangleMesh extends Mesh {
  private static final float DEFAULT_Z_VALUE = 0f;
  private static final Vector3f DEFAULT_COLOUR = new Vector3f(1, 1, 1);
  private static final int[] DEFAULT_INDICES = {0, 1, 2, 3};
  private float width;
  private float height;

  public RectangleMesh(float width, float height, Material material) {
    super(material);
    this.width = width;
    this.height = height;
    this.vertices = generateVertices();
    this.indices = DEFAULT_INDICES;
  }

  public RectangleMesh(float width, float height) {
    this(width, height, new Material(Material.getDefaultPath()));
  }


  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }

  private Vertex3D[] generateVertices() {
    return new Vertex3D[] {
        // Top Left Vertex
        new Vertex3D(new Vector3f(-width / 2, height / 2, DEFAULT_Z_VALUE), DEFAULT_COLOUR, new Vector2f(0, 0)),
        // Bottom Left Vertex
        new Vertex3D(new Vector3f(-width / 2, -height / 2, DEFAULT_Z_VALUE), DEFAULT_COLOUR, new Vector2f(0, 1)),
        // Top Right Vertex
        new Vertex3D(new Vector3f(width / 2, height / 2, DEFAULT_Z_VALUE), DEFAULT_COLOUR, new Vector2f(1, 0)),
        // Bottom Right Vertex
        new Vertex3D(new Vector3f(width / 2, -height / 2, DEFAULT_Z_VALUE), DEFAULT_COLOUR, new Vector2f(1, 1))
    };
  }
}
