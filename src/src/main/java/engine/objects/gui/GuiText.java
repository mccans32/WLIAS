package engine.objects.gui;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Vertex3D;
import engine.graphics.mesh.Mesh;
import engine.utils.ListToArray;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import math.Vector2f;
import math.Vector3f;

public class GuiText {
  private static final float Z_POS = 0f;
  private static final int VERTICES_PER_QUAD = 4;
  private int numColumns;
  private int numRows;
  private String text;
  private Mesh mesh;
  private Vector2f position = new Vector2f(0, 0);
  private Vector2f scale = new Vector2f(1, 1);
  private float edgeX;
  private float offsetX;
  private float offsetY;
  private float edgeY;
  private float fontSize;
  private float width;
  private float height;

  public GuiText(String text, float fontSize, String fontFileName, int numColumns, int numRows,
                 Vector3f textColour, float edgeX, float offsetX, float edgeY, float offsetY) {
    this.fontSize = fontSize;
    this.text = text;
    this.numColumns = numColumns;
    this.numRows = numRows;
    this.edgeX = edgeX;
    this.offsetX = offsetX;
    this.edgeY = edgeY;
    this.offsetY = offsetY;
    reposition();
    Material material = new Material(fontFileName);
    material.setColorOffset(textColour);
    this.setMesh(buildMesh(material, this.numColumns, this.numRows));
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

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;

    Material material = this.getMesh().getMaterial();
    this.setMesh(buildMesh(material, numColumns, numRows));
    create();
  }

  public Mesh getMesh() {
    return mesh;
  }

  public void setMesh(Mesh mesh) {
    this.mesh = mesh;
  }

  private Mesh buildMesh(Material material, int numColumns, int numRows) {
    material.create();
    byte[] chars = text.getBytes(StandardCharsets.ISO_8859_1);
    int numChars = chars.length;

    List<Vector3f> positions = new ArrayList<>();
    List<Vector2f> textCoordinates = new ArrayList<>();
    List<Vertex3D> verticesList = new ArrayList<>();
    List<Integer> indices = new ArrayList<>();

    float titleWidth = (material.getWidth() / (float) numColumns) * fontSize;
    float titleHeight = (material.getHeight() / (float) numRows) * fontSize;
    createVectors(positions, textCoordinates, indices, titleWidth, titleHeight, numChars, chars);
    createVertices(positions, textCoordinates, verticesList);
    Vertex3D[] verticesArray = ListToArray.vertex3DListToArray(verticesList);
    int[] indicesArray = ListToArray.integerListToIntArray(indices);
    return new Mesh(verticesArray, indicesArray, material);
  }

  private void createVertices(List<Vector3f> positions, List<Vector2f> textCoordinates, List<Vertex3D> vertexList) {
    for (int i = 0; i < positions.size(); i++) {
      vertexList.add(new Vertex3D(positions.get(i), new Vector3f(0, 0, 0), textCoordinates.get(i)));
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

  public void setOffsetX(float offsetX) {
    this.offsetX = offsetX;
  }

  public float getOffsetY() {
    return offsetY;
  }

  public void setOffsetY(float offsetY) {
    this.offsetY = offsetY;
  }

  private void createVectors(List<Vector3f> positions, List<Vector2f> textCoordinates, List<Integer> indices, float titleWidth, float titleHeight, int numChars, byte[] chars) {
    for (int i = 0; i < numChars; i++) {
      byte currChar = chars[i];
      int column = currChar % numColumns;
      int row = currChar / numColumns;
      height = titleHeight;
      width += titleWidth;

      // Top Left Vertex
      positions.add(new Vector3f(((float) i * titleWidth), 0.0f, Z_POS));
      textCoordinates.add(new Vector2f((float) column / (float) numColumns, (float) row / (float) numRows));
      indices.add(i * VERTICES_PER_QUAD);

      // Bottom Left Vertex
      positions.add(new Vector3f(((float) i * titleWidth), -titleHeight, Z_POS));
      textCoordinates.add(new Vector2f((float) column / (float) numColumns, (float) (row + 1) / (float) numRows));
      indices.add(i * VERTICES_PER_QUAD + 1);

      // Bottom Right Vertex
      positions.add(new Vector3f(((float) i * titleWidth) + titleWidth, -titleHeight, Z_POS));
      textCoordinates.add(new Vector2f((float) (column + 1) / (float) numColumns, (float) (row + 1) / (float) numRows));
      indices.add(i * VERTICES_PER_QUAD + 2);

      // Top Right Vertex
      positions.add(new Vector3f(((float) i * titleWidth) + titleWidth, 0.0f, Z_POS));
      textCoordinates.add(new Vector2f((float) (column + 1) / (float) numColumns, (float) row / (float) numRows));
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

}
