package engine.graphics.model;

import engine.graphics.Vertex3D;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import math.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Model {
  static final int POSITION_INDEX = 0;
  static final int POSITION_DIMENSION = 3;
  static final int COLOUR_INDEX = POSITION_INDEX + 1;
  static final int COLOUR_DIMENSION = 3;
  static final int TEXTURE_DIMENSION = 2;
  static final int TEXTURE_INDEX = COLOUR_INDEX + 1;
  private float width = -1;
  private float height = -1;
  private float[] positionData;
  private float[] colourData;
  private float[] textureData;
  private FloatBuffer positionBuffer;
  private FloatBuffer colourBuffer;
  private IntBuffer indicesBuffer;
  private FloatBuffer textureBuffer;
  // Vertex Array Object
  private int vao;
  // Position Buffer Object
  private int pbo;
  // Indices Buffer Object
  private int ibo;
  // Colour Buffer Object
  private int cbo;
  // Texture Buffer Object
  private int tbo;
  private Vertex3D[] vertices;
  private int[] indices;

  public Model(Vertex3D[] vertices, int[] indices) {
    this.vertices = vertices.clone();
    this.indices = indices.clone();
  }

  public Model(int[] indices) {
    this.indices = indices.clone();
  }

  public float getWidth() {
    return width;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public float getHeight() {
    return height;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public Vertex3D[] getVertices() {
    return vertices.clone();
  }

  public void setVertices(Vertex3D[] vertices) {
    this.vertices = vertices.clone();
  }

  public int[] getIndices() {
    return indices.clone();
  }

  public void setIndices(int[] indices) {
    this.indices = indices.clone();
  }

  private void initialisePositionBuffer() {
    pbo = GL15.glGenBuffers();
    positionBuffer = MemoryUtil.memAllocFloat(vertices.length * POSITION_DIMENSION);
    updatePositionBuffer();
  }

  private void initialiseColourBuffer() {
    cbo = GL15.glGenBuffers();
    colourBuffer = MemoryUtil.memAllocFloat(vertices.length * COLOUR_DIMENSION);
    updateColourBuffer();
  }

  private void initialiseTextureBuffer() {
    tbo = GL15.glGenBuffers();
    textureBuffer = MemoryUtil.memAllocFloat(vertices.length * TEXTURE_DIMENSION);
    updateTextureBuffer();
  }

  private void initialiseIndicesBuffer() {
    ibo = GL15.glGenBuffers();
    indicesBuffer = MemoryUtil.memAllocInt(indices.length);
    updateIndexBuffer();
  }

  private void storePositions() {
    for (int i = 0; i < vertices.length; i++) {
      // For 2D Vertices only
      // In the form positionData{i * dimension + offset]
      positionData[i * POSITION_DIMENSION] = vertices[i].getPosition().getX();
      positionData[i * POSITION_DIMENSION + 1] = vertices[i].getPosition().getY();
      positionData[i * POSITION_DIMENSION + 2] = vertices[i].getPosition().getZ();
    }
  }

  private void storeColours() {
    for (int i = 0; i < vertices.length; i++) {
      colourData[i * COLOUR_DIMENSION] = vertices[i].getColour().getX();
      colourData[i * COLOUR_DIMENSION + 1] = vertices[i].getColour().getY();
      colourData[i * COLOUR_DIMENSION + 2] = vertices[i].getColour().getZ();
    }
  }

  private void storeTextures() {
    for (int i = 0; i < vertices.length; i++) {
      textureData[i * TEXTURE_DIMENSION] = vertices[i].getTextureCoordinates().getX();
      textureData[i * TEXTURE_DIMENSION + 1] = vertices[i].getTextureCoordinates().getY();
    }
  }


  private void storeData(int bufferID, FloatBuffer buffer, int index, int size) {
    // Bind Buffer and Set Data
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
    // Unbind Buffer
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
  }

  /**
   * Sets colour.
   *
   * @param colour the colour
   */
  public void setColour(Vector3f colour) {
    for (Vertex3D vertex : vertices) {
      vertex.setColour(colour);
    }
  }

  /**
   * Create function for non-Text Objects.
   */
  public void create() {
    // Create Vertex Array Object and Bind it
    vao = GL30.glGenVertexArrays();
    GL30.glBindVertexArray(vao);
    // Initialise the Buffers
    initialisePositionBuffer();
    initialiseColourBuffer();
    initialiseTextureBuffer();
    initialiseIndicesBuffer();
  }

  /**
   * Destroy.
   */
  public void destroy() {
    GL15.glDeleteBuffers(cbo);
    GL15.glDeleteBuffers(pbo);
    GL15.glDeleteBuffers(ibo);
    GL15.glDeleteBuffers(tbo);
    GL30.glDeleteVertexArrays(vao);
  }

  public int getVao() {
    return vao;
  }

  public int getPbo() {
    return pbo;
  }

  public int getIbo() {
    return ibo;
  }

  public int getCbo() {
    return cbo;
  }

  public int getTbo() {
    return tbo;
  }

  public void updateVertexBuffers(Vertex3D[] vertices) {
    setVertices(vertices);
    updatePositionBuffer();
    updateTextureBuffer();
    updateColourBuffer();
  }

  public void updateIndicesBuffers(int[] indices) {
    setIndices(indices);
    updateIndexBuffer();
  }

  private void updateIndexBuffer() {
    if (indices.length > indicesBuffer.capacity()) {
      indicesBuffer = MemoryUtil.memAllocInt(indices.length);
    }
    indicesBuffer.clear();
    indicesBuffer.put(indices).flip();
    // Bind Buffer and Set Data
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
    // Unbind Buffer
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
  }

  private void updateTextureBuffer() {
    if ((vertices.length * TEXTURE_DIMENSION) > textureBuffer.capacity()) {
      textureBuffer = MemoryUtil.memAllocFloat(vertices.length * TEXTURE_DIMENSION);
    }
    System.out.println(vertices.length * TEXTURE_DIMENSION + ", " + textureBuffer.capacity());
    textureData = new float[vertices.length * TEXTURE_DIMENSION];
    storeTextures();
    textureBuffer.clear();
    textureBuffer.put(textureData).flip();
    storeData(tbo, textureBuffer, TEXTURE_INDEX, TEXTURE_DIMENSION);
  }

  private void updatePositionBuffer() {
    if ((vertices.length * POSITION_DIMENSION) > positionBuffer.capacity()) {
      positionBuffer = MemoryUtil.memAllocFloat(vertices.length * POSITION_DIMENSION);
    }
    positionData = new float[vertices.length * POSITION_DIMENSION];
    positionBuffer.clear();
    storePositions();
    positionBuffer.put(positionData).flip();
    storeData(pbo, positionBuffer, POSITION_INDEX, POSITION_DIMENSION);
  }

  private void updateColourBuffer() {
    if ((vertices.length * COLOUR_DIMENSION) > colourBuffer.capacity()) {
      colourBuffer = MemoryUtil.memAllocFloat(vertices.length * COLOUR_DIMENSION);
    }
    colourData = new float[vertices.length * COLOUR_DIMENSION];
    storeColours();
    colourBuffer.clear();
    colourBuffer.put(colourData).flip();
    storeData(cbo, colourBuffer, COLOUR_INDEX, COLOUR_DIMENSION);
  }
}