package engine.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import math.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

/**
 * The type Mesh.
 */
public class Mesh {

  static final int POSITION_INDEX = 0;
  static final int POSITION_DIMENSION = 3;
  static final int COLOUR_INDEX = POSITION_INDEX + 1;
  static final int COLOUR_DIMENSION = 3;
  static final int TEXTURE_DIMENSION = 2;
  static final int TEXTURE_INDEX = COLOUR_INDEX + 1;
  static final String DEFAULT_MATERIAL_PATH = "/images/default_texture.png";
  private Vertex3D[] vertices;
  private int[] indices;
  private Material material = new Material(DEFAULT_MATERIAL_PATH);
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

  private float[] positionData;
  private float[] colourData;
  private float[] textureData;

  /**
   * Instantiates a new Mesh.
   *
   * @param vertices the vertices
   * @param indices  the indices
   */
  public Mesh(Vertex3D[] vertices, int[] indices, Material material) {
    this.vertices = vertices.clone();
    this.indices = indices.clone();
    this.material = material;
  }

  public Mesh(Vertex3D[] vertices, int[] indices) {
    this.vertices = vertices.clone();
    this.indices = indices.clone();
  }

  public Mesh(Vertex2D[] vertices, int[] indices, Material material) {
    Vertex3D[] newVertices = new Vertex3D[vertices.length];
    for (int i = 0; i < vertices.length; i++) {
      Vertex3D newVertex = Vertex3D.convert(vertices[i]);
      newVertices[i] = newVertex;
    }

    this.vertices = newVertices.clone();
    this.indices = indices.clone();
    this.material = material;
  }

  public Mesh(Vertex2D[] vertices, int[] indices) {
    Vertex3D[] newVertices = new Vertex3D[vertices.length];
    for (int i = 0; i < vertices.length; i++) {
      Vertex3D newVertex = Vertex3D.convert(vertices[i]);
      newVertices[i] = newVertex;
    }

    this.vertices = newVertices.clone();
    this.indices = indices.clone();
  }

  public Vertex3D[] getVertices() {
    return vertices.clone();
  }

  public int[] getIndices() {
    return indices.clone();
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

  public Material getMaterial() {
    return material;
  }

  /**
   * Create.
   */
  public void create() {
    //Create the Material
    material.create();
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
    material.destroy();
  }

  private void initialisePositionBuffer() {
    FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * POSITION_DIMENSION);
    positionData = new float[vertices.length * POSITION_DIMENSION];

    storePositions();
    positionBuffer.put(positionData).flip();
    pbo = storeData(positionBuffer, POSITION_INDEX, POSITION_DIMENSION);
  }

  private void initialiseColourBuffer() {
    FloatBuffer colourBuffer = MemoryUtil.memAllocFloat(vertices.length * COLOUR_DIMENSION);
    colourData = new float[vertices.length * COLOUR_DIMENSION];

    storeColours();
    colourBuffer.put(colourData).flip();
    cbo = storeData(colourBuffer, COLOUR_INDEX, COLOUR_DIMENSION);
  }

  private void initialiseTextureBuffer() {
    FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * TEXTURE_DIMENSION);
    textureData = new float[vertices.length * TEXTURE_DIMENSION];

    storeTextures();
    textureBuffer.put(textureData).flip();
    tbo = storeData(textureBuffer, TEXTURE_INDEX, TEXTURE_DIMENSION);
  }

  private void initialiseIndicesBuffer() {
    IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
    indicesBuffer.put(indices).flip();
    // Generate Index Buffer Object
    ibo = GL15.glGenBuffers();
    // Bind Buffer and Set Data
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
    // Unbind Buffer
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
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


  private int storeData(FloatBuffer buffer, int index, int size) {
    // Generate Position Buffer Object
    int bufferID = GL15.glGenBuffers();
    // Bind Buffer and Set Data
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
    // Unbind Buffer
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    return bufferID;
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
}