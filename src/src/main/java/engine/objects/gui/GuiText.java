package engine.objects.gui;

import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Vertex3D;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import math.Vector2f;
import math.Vector3f;

public class GuiText {
  private static final float Z_POS = 0.0f;
  private static final int VERTICES_PER_QUAD = 4;
  private final int NUM_COLUMNS;
  private final int NUM_ROWS;
  private String text;
  private Mesh mesh;

  public GuiText(String text, String fontFileName, int numColumns, int numRows) {
    this.text = text;
    this.NUM_COLUMNS = numColumns;
    this.NUM_ROWS = numRows;
    Material material = new Material(fontFileName);
    this.setMesh(buildMesh(material, NUM_COLUMNS, NUM_ROWS));
  }

  public void setText(String text) {
    this.text = text;
    Material material = this.getMesh().getMaterial();
    this.getMesh().destroy();
    this.setMesh(buildMesh(material, NUM_COLUMNS, NUM_ROWS));

  }

  public Mesh getMesh() {
    return mesh;
  }

  public void setMesh(Mesh mesh) {
    this.mesh = mesh;
  }

  private Mesh buildMesh(Material material, int numColumns, int numRows) {
    byte[] chars = text.getBytes(StandardCharsets.ISO_8859_1);
    int numChars = chars.length;

    List<Vector3f> positions = new ArrayList<>();
    List<Vector2f> textCoordinates = new ArrayList<>();
    List<Vertex3D> vertexList = new ArrayList<>();
    float[] normals = new float[0];
    List<Integer> indices = new ArrayList<>();

    float titleWidth = (float) material.getWidth() / (float) numColumns;
    float titleHeight = (float) material.getHeight() / (float) numRows;
    createVectors(positions, textCoordinates, indices, titleWidth, titleHeight, numChars, chars);
    createVertex(positions, textCoordinates, vertexList);
    Vertex3D[] vertexArray = (Vertex3D[]) vertexList.toArray();
    int[] indicesArray = new int[indices.size()];
    for (int i = 0; i < indicesArray.length; i++) {
      indicesArray[i] = indices.get(i);
    }
    return new Mesh(vertexArray, indicesArray, material);
  }

  private void createVertex(List<Vector3f> positions, List<Vector2f> textCoordinates, List<Vertex3D> vertexList) {
    for (int i = 0; i < positions.size(); i++) {
      vertexList.add(new Vertex3D(positions.get(i), new Vector3f(0, 0, 0), textCoordinates.get(i)));
    }
  }

  private void createVectors(List<Vector3f> positions, List<Vector2f> textCoordinates, List<Integer> indices, float titleWidth, float titleHeight, int numChars, byte[] chars) {
    for (int i = 0; i < numChars; i++) {
      byte currChar = chars[i];
      int column = currChar % NUM_COLUMNS;
      // Possibly suppose to be rows
      int row = currChar / NUM_COLUMNS;

      // Top Left Vertex
      positions.add(new Vector3f((float) i * titleWidth, 0.0f, Z_POS));
      textCoordinates.add(new Vector2f((float) column / (float) NUM_COLUMNS, (float) row / (float) NUM_ROWS));
      indices.add(i * VERTICES_PER_QUAD);

      // Bottom Left Vertex
      positions.add(new Vector3f((float) i * titleWidth, titleHeight, Z_POS));
      textCoordinates.add(new Vector2f((float) column / (float) NUM_COLUMNS, (float) (row + 1) / (float) NUM_ROWS));
      indices.add(i * VERTICES_PER_QUAD + 1);

      // Bottom Right Vertex
      positions.add(new Vector3f((float) i * titleWidth + titleWidth, titleHeight, Z_POS));
      textCoordinates.add(new Vector2f((float) column + 1 / (float) NUM_COLUMNS, (float) row + 1 / (float) NUM_ROWS));
      indices.add(i * VERTICES_PER_QUAD + 2);

      // Top Right Vertex
      positions.add(new Vector3f((float) i * titleWidth + titleWidth, 0.0f, Z_POS));
      textCoordinates.add(new Vector2f((float) column + 1 / (float) NUM_COLUMNS, (float) row / (float) NUM_ROWS));
      indices.add(i * VERTICES_PER_QUAD + 3);

      // Add indices for left top and bottom right vertices for the second triangle
      indices.add(i * VERTICES_PER_QUAD);
      indices.add(i * VERTICES_PER_QUAD + 2);

    }
  }


}
