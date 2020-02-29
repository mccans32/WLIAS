package engine.objects.gui;

import engine.Window;
import engine.graphics.Mesh;
import engine.graphics.Vertex3D;
import engine.io.Input;
import engine.tools.MousePicker;
import math.Vector2f;
import math.Vector3f;

public class GuiObject {
  private Vector2f defaultPosition;
  private Vector2f position;
  private Vector2f scale;
  private Mesh mesh;
  private float xEdge;
  private float xOffset;
  private float yEdge;
  private float yOffset;

  /**
   * Instantiates a new Gui object.
   *
   * @param position the position
   * @param scale    the scale
   * @param mesh     the mesh.
   */
  public GuiObject(Vector2f position, Vector2f scale, Mesh mesh, float xEdge, float xOffset, float yEdge, float yOffset) {
    this.defaultPosition = position;
    this.position = position;
    this.scale = scale;
    this.mesh = mesh;
    this.xEdge = xEdge;
    this.xOffset = xOffset;
    this.yEdge = yEdge;
    this.yOffset = yOffset;

  }

  public Vector2f[] getNormalisedVertexPositions(Window window) {
    Vertex3D[] vertices = mesh.getVertices();
    // Get Array of X and Y offsets for all vertices
    Vector2f[] vertexPositions = new Vector2f[vertices.length];
    for (int i = 0; i < vertices.length; i++) {
      Vector3f vertexPosition = vertices[i].getPosition();
      vertexPositions[i] = new Vector2f(vertexPosition.getX(), vertexPosition.getY());
    }

    // Add vertex positions to position in order to get their OpenGl coordinates
    for (int i = 0; i < vertexPositions.length; i++) {
      vertexPositions[i] = Vector2f.add(position, vertexPositions[i]);
      vertexPositions[i] = Vector2f.divide(vertexPositions[i], new Vector2f(window.getxSpan(), window.getySpan()));
    }

    return vertexPositions;
  }

  public Boolean isMouseOver(Window window) {
    Vector2f normalisedMouse = MousePicker.getNormalisedDeviceCoordinates(window);
    float normalX = normalisedMouse.getX();
    float normalY = normalisedMouse.getY();
    float minX = 0;
    float maxX = 0;
    float minY = 0;
    float maxY = 0;

    // Calculate min and max values
    Vector2f[] positions = getNormalisedVertexPositions(window);
    for (Vector2f position : positions) {
      if (position.getX() < minX) {
        minX = position.getX();
      }
      if (position.getX() > maxX) {
        maxX = position.getX();
      }
      if (position.getY() < minY) {
        minY = position.getY();
      }
      if (position.getY() > maxY) {
        maxY = position.getY();
      }
    }

    Boolean withinX = ((normalX >= minX) && (normalX <= maxX));
    Boolean withinY = ((normalY >= minY) && (normalY <= maxY));
    // Check if X is within boundaries
    return (withinX && withinY);
  }

  public float getxEdge() {
    return xEdge;
  }

  public float getxOffset() {
    return xOffset;
  }

  public float getyEdge() {
    return yEdge;
  }

  public float getYOffset() {
    return yOffset;
  }

  public Vector2f getPosition() {
    return position;
  }

  public Vector2f getScale() {
    return scale;
  }

  public Mesh getMesh() {
    return mesh;
  }

  public void create() {
    mesh.create();
  }

  public void destroy() {
    mesh.destroy();
  }

  public void reposition(float xSpan, float ySpan) {
    position.setX(xEdge * xSpan + xOffset);
    position.setY(yEdge * ySpan + yOffset);
  }
}
