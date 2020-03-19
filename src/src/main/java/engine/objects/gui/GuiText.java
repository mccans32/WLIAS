package engine.objects.gui;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Vertex3D;
import engine.graphics.mesh.Mesh;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.text.Text;
import engine.utils.ListUtils;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import math.Vector2f;
import math.Vector3f;

public class GuiText {
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

  /**
   * Instantiates a new Gui text.
   *
   * @param text    the text
   * @param edgeX   the edge x
   * @param offsetX the offset x
   * @param edgeY   the edge y
   * @param offsetY the offset y
   */
  public GuiText(Text text, float edgeX, float offsetX, float edgeY, float offsetY) {
    this.text = text;
    this.edgeX = edgeX;
    this.offsetX = offsetX;
    this.edgeY = edgeY;
    this.offsetY = offsetY;
    reposition();
    this.setMesh(buildMesh());
  }

  /**
   * Calculate title width float.
   *
   * @param text the text
   * @return the float
   */
  public static float calculateTitleWidth(Text text) {
    Material tempMaterial = new Material(text.getFontFile());
    tempMaterial.create();
    float titleWidth = (tempMaterial.getWidth() / (float) text.getNumColumns())
        * text.getFontSize();
    tempMaterial.destroy();
    return titleWidth;
  }

  /**
   * Calculate title height float.
   *
   * @param text the text
   * @return the float
   */
  public static float calculateTitleHeight(Text text) {
    Material tempMaterial = new Material(text.getFontFile());
    tempMaterial.create();
    float titleHeight = (tempMaterial.getHeight() / (float) text.getNumRows()) * text.getFontSize();
    tempMaterial.destroy();
    return titleHeight;
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
    this.mesh.destroy();
    this.setMesh(buildMesh());
    create();
  }

  public Mesh getMesh() {
    return mesh;
  }

  public void setMesh(Mesh mesh) {
    this.mesh = mesh;
  }

  private Mesh buildMesh() {
    Material material = new Material(text.getFontFile());
    material.setColorOffset(text.getTextColour());
    material.create();
    byte[] chars = text.getString().getBytes(StandardCharsets.ISO_8859_1);
    int numChars = chars.length;

    List<Vector3f> positions = new ArrayList<>();
    List<Vector2f> textCoordinates = new ArrayList<>();
    List<Vertex3D> verticesList = new ArrayList<>();
    List<Integer> indices = new ArrayList<>();

    float titleWidth = calculateTitleWidth(text);
    float titleHeight = calculateTitleHeight(text);
    createVectors(positions, textCoordinates, indices, titleWidth, titleHeight, numChars, chars);
    createVertices(positions, textCoordinates, verticesList);
    Vertex3D[] verticesArray = ListUtils.vertex3DListToArray(verticesList);
    int[] indicesArray = ListUtils.integerListToIntArray(indices);
    return new Mesh(verticesArray, indicesArray, material);
  }

  private void createVertices(List<Vector3f> positions, List<Vector2f> textCoordinates,
                              List<Vertex3D> vertexList) {
    for (int i = 0; i < positions.size(); i++) {
      vertexList.add(new Vertex3D(positions.get(i), new Vector3f(0, 0, 0),
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

  private void createVectors(List<Vector3f> positions, List<Vector2f> textCoordinates,
                             List<Integer> indices, float titleWidth, float titleHeight,
                             int numChars, byte[] chars) {
    height = titleHeight;
    width = 0;
    for (int i = 0; i < numChars; i++) {
      byte currChar = chars[i];
      int column = currChar % text.getNumColumns();
      int row = currChar / text.getNumColumns();
      width += titleWidth;

      // Top Left Vertex
      positions.add(new Vector3f(((float) i * titleWidth), 0.0f, Z_POS));
      textCoordinates.add(new Vector2f((float) column / (float) text.getNumColumns(),
          (float) row / (float) text.getNumRows()));
      indices.add(i * VERTICES_PER_QUAD);

      // Bottom Left Vertex
      positions.add(new Vector3f(((float) i * titleWidth), -titleHeight, Z_POS));
      textCoordinates.add(new Vector2f((float) column / (float) text.getNumColumns(),
          (float) (row + 1) / (float) text.getNumRows()));
      indices.add(i * VERTICES_PER_QUAD + 1);

      // Bottom Right Vertex
      positions.add(new Vector3f(((float) i * titleWidth) + titleWidth, -titleHeight, Z_POS));
      textCoordinates.add(new Vector2f((float) (column + 1) / (float) text.getNumColumns(),
          (float) (row + 1) / (float) text.getNumRows()));
      indices.add(i * VERTICES_PER_QUAD + 2);

      // Top Right Vertex
      positions.add(new Vector3f(((float) i * titleWidth) + titleWidth, 0.0f, Z_POS));
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

  public void destroy() {
    this.mesh.destroy();
  }

  public void render(TextRenderer renderer) {
    renderer.renderObject(this);
  }
}
