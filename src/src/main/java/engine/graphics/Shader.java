package engine.graphics;

import engine.utils.FileUtils;
import java.nio.FloatBuffer;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

/**
 * The type Shader.
 */
public class Shader {
  /**
   * The Vertex type string.
   */
  static final String VERTEX_TYPE_STRING = "Vertex";
  /**
   * The Fragment type string.
   */
  static final String FRAGMENT_TYPE_STRING = "Fragment";
  private String vertexFile;
  private String fragmentFile;
  private int programID;
  private int[] shaderIDs;

  /**
   * Instantiates a new Shader.
   *
   * @param vertexPath   the vertex path
   * @param fragmentPath the fragment path
   */
  public Shader(String vertexPath, String fragmentPath) {
    this.vertexFile = FileUtils.loadAsString(vertexPath);
    this.fragmentFile = FileUtils.loadAsString(fragmentPath);
  }

  /**
   * Create.
   */
  public void create() {
    programID = GL20.glCreateProgram();

    int vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
    createShader(vertexID, vertexFile, VERTEX_TYPE_STRING);

    int fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
    createShader(fragmentID, fragmentFile, FRAGMENT_TYPE_STRING);

    shaderIDs = new int[] {vertexID, fragmentID};
    attachShaders();
    linkProgram();
    validateProgram();
  }

  /**
   * Bind.
   */
  public void bind() {
    GL20.glUseProgram(programID);
  }

  /**
   * Unbind.
   */
  public void unbind() {
    GL20.glUseProgram(0);
  }

  /**
   * Destroy.
   */
  public void destroy() {
    for (int shaderID : shaderIDs) {
      GL20.glDetachShader(programID, shaderID);
      GL20.glDeleteShader(shaderID);
    }
    GL20.glDeleteProgram(programID);
  }

  private void createShader(int shaderID, String shaderFile, String shaderType) {
    // Set and Compile Shader
    GL20.glShaderSource(shaderID, shaderFile);
    GL20.glCompileShader(shaderID);
    // Check if Compiled
    if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
      System.err.println(shaderType + " Error: " + GL20.glGetShaderInfoLog(shaderID));
    }
  }

  private void attachShaders() {
    for (int shaderID : shaderIDs) {
      GL20.glAttachShader(programID, shaderID);
    }
  }

  private void linkProgram() {
    GL20.glLinkProgram(programID);
    // Check if Linked Properly
    if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
      System.err.println("Program Link Error: " + GL20.glGetShaderInfoLog(programID));
    }
  }

  private void validateProgram() {
    GL20.glValidateProgram(programID);
    // Check if Validated Properly
    if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
      System.err.println("Program Validation Error: " + GL20.glGetShaderInfoLog(programID));
    }
  }

  public int getUniformLocation(String uniformName) {
    return GL20.glGetUniformLocation(programID, uniformName);
  }

  public void setUniform(String uniformName, int val) {
    GL20.glUniform1i(getUniformLocation(uniformName), val);
  }

  public void setUniform(String uniformName, float val) {
    GL20.glUniform1f(getUniformLocation(uniformName), val);
  }

  public void setUniform(String uniformName, boolean val) {
    GL20.glUniform1i(getUniformLocation(uniformName), (val ? 1 : 0));
  }

  public void setUniform(String uniformName, Vector2f val) {
    GL20.glUniform2f(getUniformLocation(uniformName), val.getX(), val.getY());
  }

  public void setUniform(String uniformName, Vector3f val) {
    GL20.glUniform3f(getUniformLocation(uniformName), val.getX(), val.getY(), val.getZ());
  }

  /**
   * Sets uniform.
   *
   * @param uniformName the uniform name
   * @param val         the val
   */
  public void setUniform(String uniformName, Matrix4f val) {
    FloatBuffer matrixBuffer = MemoryUtil.memAllocFloat(Matrix4f.getSize() * Matrix4f.getSize());
    matrixBuffer.put(val.getAll()).flip();
    GL20.glUniformMatrix4fv(getUniformLocation(uniformName), true, matrixBuffer);
  }

}