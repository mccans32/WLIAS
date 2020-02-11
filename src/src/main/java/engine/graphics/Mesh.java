package engine.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

/**
 * The type Mesh.
 */
public class Mesh {

  static final int GRAPHICS_DIMENSION = 2;
  private Vertex2D[] vertices;
  private int[] indices;
  // Vertex Array Object
  private int vao;
  // Position Buffer Object
  private int pbo;
  // Indices Buffer Object
  private int ibo;
  private float[] positionData;

  /**
   * Instantiates a new Mesh.
   *
   * @param vertices the vertices
   * @param indices  the indices
   */
  public Mesh(Vertex2D[] vertices, int[] indices) {
    this.vertices = vertices.clone();
    this.indices = indices.clone();
  }

  /**
   * Get vertices vertex 2 d [ ].
   *
   * @return the vertex 2 d [ ]
   */
  public Vertex2D[] getVertices() {
    return vertices.clone();
  }

  /**
   * Get indices int [ ].
   *
   * @return the int [ ]
   */
  public int[] getIndices() {
    return indices.clone();
  }

  /**
   * Gets vao.
   *
   * @return the vao
   */
  public int getVao() {
    return vao;
  }

  /**
   * Gets pbo.
   *
   * @return the pbo
   */
  public int getPbo() {
    return pbo;
  }

  /**
   * Gets ibo.
   *
   * @return the ibo
   */
  public int getIbo() {
    return ibo;
  }

  /**
   * Create.
   */
  public void create() {
    // Create Vertex Array Object and Bind it
    vao = GL30.glGenVertexArrays();
    GL30.glBindVertexArray(vao);

    initialisePositionBuffer();

    initialiseIndicesBuffer();

  }

  private void initialisePositionBuffer() {
    FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * GRAPHICS_DIMENSION);
    positionData = new float[vertices.length * GRAPHICS_DIMENSION];

    storePositions();

    positionBuffer.put(positionData).flip();

    // Generate Position Buffer Object
    pbo = GL15.glGenBuffers();
    // Bind Buffer and Set Data
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, pbo);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(0, GRAPHICS_DIMENSION, GL11.GL_FLOAT, false, 0, 0);
    // Unbind Buffer
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
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
      positionData[i * GRAPHICS_DIMENSION] = vertices[i].getPosition().getX();
      positionData[i * GRAPHICS_DIMENSION + 1] = vertices[i].getPosition().getY();
    }
  }
}