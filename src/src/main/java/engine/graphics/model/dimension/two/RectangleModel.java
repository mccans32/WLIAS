package engine.graphics.model.dimension.two;

import engine.graphics.Vertex3D;
import engine.graphics.model.Model;
import math.Vector2f;
import math.Vector3f;

public class RectangleModel extends Model {
  private static final float DEFAULT_Z_VALUE = 0f;
  private static final Vector3f DEFAULT_COLOUR = new Vector3f(1, 1, 1);
  private static final int[] DEFAULT_INDICES = {0, 1, 2, 3};

  /**
   * Instantiates a new Rectangle model.
   *
   * @param width   the width
   * @param height  the height
   * @param indices the indices
   */
  public RectangleModel(float width, float height, int[] indices) {
    super(indices);
    super.setWidth(width);
    super.setHeight(height);
    super.setVertices(generateVertices(width, height));
  }

  public RectangleModel(float width, float height) {
    this(width, height, DEFAULT_INDICES);
  }

  /**
   * Generate vertices vertex 3 d [ ].
   *
   * @param width  the width
   * @param height the height
   * @return the vertex 3 d [ ]
   */
  private Vertex3D[] generateVertices(float width, float height) {
    return new Vertex3D[] {
        // Top Left Vertex
        new Vertex3D(new Vector3f(-width / 2, height / 2, DEFAULT_Z_VALUE), DEFAULT_COLOUR,
            new Vector2f(0, 0)),
        // Bottom Left Vertex
        new Vertex3D(new Vector3f(-width / 2, -height / 2, DEFAULT_Z_VALUE), DEFAULT_COLOUR,
            new Vector2f(0, 1)),
        // Top Right Vertex
        new Vertex3D(new Vector3f(width / 2, height / 2, DEFAULT_Z_VALUE), DEFAULT_COLOUR,
            new Vector2f(1, 0)),
        // Bottom Right Vertex
        new Vertex3D(new Vector3f(width / 2, -height / 2, DEFAULT_Z_VALUE), DEFAULT_COLOUR,
            new Vector2f(1, 1))
    };
  }
}
