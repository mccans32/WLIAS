package engine.objects.gui;

import engine.Window;
import engine.graphics.mesh.Mesh;
import engine.graphics.Vertex3D;
import engine.graphics.mesh.twoDimensional.RectangleMesh;
import engine.tools.MousePicker;
import math.Vector2f;
import math.Vector3f;

public class GuiImage {
  private Vector2f position = new Vector2f(0, 0);
  private Vector2f scale = new Vector2f(1, 1);
  private RectangleMesh mesh;
  private float edgeX;
  private float offsetX;
  private float edgeY;
  private float offsetY;

  /**
   * Instantiates a new Gui object.
   *
   * @param mesh     the mesh.
   */
  public GuiImage(RectangleMesh mesh,
                  float edgeX,
                  float offsetX,
                  float edgeY,
                  float offsetY) {
    this.mesh = mesh;
    this.edgeX = edgeX;
    this.offsetX = offsetX;
    this.edgeY = edgeY;
    this.offsetY = offsetY;
    // reposition to accommodate for window span
    reposition();
  }

  /**
   * Get normalised vertex positions vector 2 f [ ].
   *
   * @return the vector 2 f [ ]
   */
  public Vector2f[] getNormalisedVertexPositions() {
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
      vertexPositions[i] = Vector2f.divide(
          vertexPositions[i],
          new Vector2f(Window.getSpanX(), Window.getSpanY()));
    }

    return vertexPositions;
  }

  /**
   * Is mouse over boolean.
   *
   * @param window the window
   * @return the boolean
   */
  public Boolean isMouseOver(Window window) {
    // Get normalised Mouse Positions
    Vector2f normalisedMouse = MousePicker.getNormalisedDeviceCoordinates(window);
    // Get normalised Vertex Positions
    Vector2f[] guiVertexPositions = getNormalisedVertexPositions();

    // Check if There are a valid amount of vertices
    if (guiVertexPositions.length >= 3) {
      float normalX = normalisedMouse.getX();
      float normalY = normalisedMouse.getY();
      // Set initial values to be that of the first Vertex
      float minX = guiVertexPositions[0].getX();
      float maxX = guiVertexPositions[0].getX();
      float minY = guiVertexPositions[0].getY();
      float maxY = guiVertexPositions[0].getY();

      // Calculate min and max values
      for (int i = 1; i < guiVertexPositions.length; i++) {
        if (guiVertexPositions[i].getX() < minX) {
          minX = guiVertexPositions[i].getX();
        }
        if (guiVertexPositions[i].getX() > maxX) {
          maxX = guiVertexPositions[i].getX();
        }
        if (guiVertexPositions[i].getY() < minY) {
          minY = guiVertexPositions[i].getY();
        }
        if (guiVertexPositions[i].getY() > maxY) {
          maxY = guiVertexPositions[i].getY();
        }
      }

      Boolean withinX = ((normalX >= minX) && (normalX <= maxX));
      Boolean withinY = ((normalY >= minY) && (normalY <= maxY));
      // Check if Mouse is within boundaries
      return (withinX && withinY);
    } else {
      return false;
    }
  }

  public float getEdgeX() {
    return edgeX;
  }

  public float getOffsetX() {
    return offsetX;
  }

  public float getEdgeY() {
    return edgeY;
  }

  public float getOffsetY() {
    return offsetY;
  }

  public Vector2f getPosition() {
    return position;
  }

  public Vector2f getScale() {
    return scale;
  }

  public RectangleMesh getMesh() {
    return mesh;
  }

  public void create() {
    mesh.create();
  }

  public void destroy() {
    mesh.destroy();
  }

  public void reposition() {
    position.setX(edgeX * Window.getSpanX() + offsetX);
    position.setY(edgeY * Window.getSpanY() + offsetY);
  }
}
