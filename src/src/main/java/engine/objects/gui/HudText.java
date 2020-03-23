package engine.objects.gui;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Vertex3D;
import engine.graphics.image.Image;
import engine.graphics.mesh.Mesh;
import engine.graphics.model.Model;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.text.Text;
import engine.utils.ListUtils;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import math.Vector2f;
import math.Vector3f;

public class HudText {
  private static final float Z_POS = 0f;
  private static final int VERTICES_PER_QUAD = 4;
  private Text text;
  private Mesh mesh;
  private Vector2f position = new Vector2f(0, 0);
  private Vector2f scale = new Vector2f(1, 1);
  private float edgeX;
  private float offsetX;
  private float offsetY;
  private float edgeY;
  private float width;
  private float height;
  private List<Vector3f> positions = new ArrayList<>();
  private List<Vector2f> textCoordinates = new ArrayList<>();
  private List<Vertex3D> verticesList = new ArrayList<>();
  private List<Integer> indices = new ArrayList<>();

  /**
   * Instantiates a new Hud text.
   *
   * @param text    the text
   * @param edgeX   the edge x
   * @param offsetX the offset x
   * @param edgeY   the edge y
   * @param offsetY the offset y
   */
  public HudText(Text text, float edgeX, float offsetX, float edgeY, float offsetY) {
    this.text = text;
    this.edgeX = edgeX;
    this.offsetX = offsetX;
    this.edgeY = edgeY;
    this.offsetY = offsetY;
    reposition();
    this.setMesh(buildMesh());
  }

  /**
   * Calculate char width float.
   *
   * @param text the text
   * @return the float
   */
  public static float calculateCharWidth(Text text) {
    Material tempMaterial = new Material(new Image(text.getFontFile()));
    tempMaterial.create();
    float charWidth = (tempMaterial.getImage().getWidth() / (float) text.getNumColumns())
        * text.getFontSize();
    tempMaterial.destroy();
    return charWidth;
  }

  /**
   * Calculate char height float.
   *
   * @param text the text
   * @return the float
   */
  public static float calculateCharHeight(Text text) {
    Material tempMaterial = new Material(new Image(text.getFontFile()));
    tempMaterial.create();
    float charHeight =
        (tempMaterial.getImage().getHeight() / (float) text.getNumRows()) * text.getFontSize();
    tempMaterial.destroy();
    return charHeight;
  }

  public void setTextColour(Vector3f textColour) {
    this.mesh.getMaterial().setColorOffset(textColour);
  }

  public Vector2f getPosition() {
    return position;
  }

  public void setPosition(Vector2f position) {
    this.position = position;
  }

  public Vector2f getScale() {
    return scale;
  }

  public void setScale(Vector2f scale) {
    this.scale = scale;
  }

  public Text getText() {
    return text;
  }

  /**
   * Sets text.
   *
   * @param text the to render.
   */
  public void setText(Text text) {
    boolean isCentreVertical = this.text.isCentreVertical();
    boolean isCentreHorizontal = this.text.isCentreHorizontal();
    this.text = text;
    this.text.setCentreVertical(isCentreVertical);
    this.text.setCentreHorizontal(isCentreHorizontal);
    // Update the Vertices and the Indices
    positions.clear();
    textCoordinates.clear();
    verticesList.clear();
    indices.clear();
    byte[] chars = text.getString().getBytes(StandardCharsets.ISO_8859_1);
    int numChars = chars.length;
    float charWidth = calculateCharWidth(text);
    float charHeight = calculateCharHeight(text);
    createVectors(charWidth, charHeight, numChars, chars);
    createVertices();
    Vertex3D[] verticesArray = ListUtils.vertex3DListToArray(verticesList);
    int[] indicesArray = ListUtils.integerListToIntArray(indices);
    updateBuffers(verticesArray, indicesArray);
  }

  public Mesh getMesh() {
    return mesh;
  }

  public void setMesh(Mesh mesh) {
    this.mesh = mesh;
  }

  private Mesh buildMesh() {
    Material material = new Material(new Image(text.getFontFile()));
    material.setColorOffset(text.getTextColour());
    material.create();
    byte[] chars = text.getString().getBytes(StandardCharsets.ISO_8859_1);
    int numChars = chars.length;

    float charWidth = calculateCharWidth(text);
    float charHeight = calculateCharHeight(text);
    createVectors(charWidth, charHeight, numChars, chars);
    createVertices();
    Vertex3D[] verticesArray = ListUtils.vertex3DListToArray(verticesList);
    int[] indicesArray = ListUtils.integerListToIntArray(indices);
    return new Mesh(new Model(verticesArray, indicesArray), material);
  }

  private void createVertices() {
    for (int i = 0; i < positions.size(); i++) {
      verticesList.add(new Vertex3D(positions.get(i), new Vector3f(0, 0, 0),
          textCoordinates.get(i)));
    }
  }

  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }

  public float getOffsetX() {
    return offsetX;
  }

  public void setOffsetX(float offsetX) {
    this.offsetX = offsetX;
  }

  public float getEdgeX() {
    return edgeX;
  }

  public void setEdgeX(float edgeX) {
    this.edgeX = edgeX;
  }

  public float getEdgeY() {
    return edgeY;
  }

  public void setEdgeY(float edgeY) {
    this.edgeY = edgeY;
  }

  public float getOffsetY() {
    return offsetY;
  }

  public void setOffsetY(float offsetY) {
    this.offsetY = offsetY;
  }

  private void createVectors(float charWidth, float charHeight, int numChars, byte[] chars) {
    height = charHeight;
    width = 0;
    for (int i = 0; i < numChars; i++) {
      byte currChar = chars[i];
      int column = currChar % text.getNumColumns();
      int row = currChar / text.getNumColumns();
      width += charWidth;

      // Top Left Vertex
      positions.add(new Vector3f(((float) i * charWidth), 0.0f, Z_POS));
      textCoordinates.add(new Vector2f((float) column / (float) text.getNumColumns(),
          (float) row / (float) text.getNumRows()));
      indices.add(i * VERTICES_PER_QUAD);

      // Bottom Left Vertex
      positions.add(new Vector3f(((float) i * charWidth), -charHeight, Z_POS));
      textCoordinates.add(new Vector2f((float) column / (float) text.getNumColumns(),
          (float) (row + 1) / (float) text.getNumRows()));
      indices.add(i * VERTICES_PER_QUAD + 1);

      // Bottom Right Vertex
      positions.add(new Vector3f(((float) i * charWidth) + charWidth, -charHeight, Z_POS));
      textCoordinates.add(new Vector2f((float) (column + 1) / (float) text.getNumColumns(),
          (float) (row + 1) / (float) text.getNumRows()));
      indices.add(i * VERTICES_PER_QUAD + 2);

      // Top Right Vertex
      positions.add(new Vector3f(((float) i * charWidth) + charWidth, 0.0f, Z_POS));
      textCoordinates.add(new Vector2f((float) (column + 1) / (float) text.getNumColumns(),
          (float) row / (float) text.getNumRows()));
      indices.add(i * VERTICES_PER_QUAD + 3);

      // Add indices for left top and bottom right vertices for the second triangle
      indices.add(i * VERTICES_PER_QUAD);
      indices.add(i * VERTICES_PER_QUAD + 2);
    }
  }

  public void reposition() {
    position.setX(edgeX * Window.getSpanX() + offsetX);
    position.setY(edgeY * Window.getSpanY() + offsetY);
  }

  public void create() {
    this.mesh.createText();
  }

  public void updateBuffers(Vertex3D[] vertices, int[] indices) {
    mesh.getModel().updateVertexBuffers(vertices);
    mesh.getModel().updateIndicesBuffers(indices);
  }

  public void destroy() {
    this.mesh.destroy();
  }

  public void render(TextRenderer renderer) {
    renderer.renderObject(this);
  }

}
