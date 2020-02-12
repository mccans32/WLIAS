package engine.graphics;

import engine.utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

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
  private int vertexID;
  private int fragmentID;
  private int programID;

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

    vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
    createShader(vertexID, vertexFile, VERTEX_TYPE_STRING);

    fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
    createShader(fragmentID, fragmentFile, FRAGMENT_TYPE_STRING);

    int[] shaderIDs = {vertexID, fragmentID};
    attachShaders(shaderIDs);
    linkProgram();
    validateProgram();
    deleteShaders(shaderIDs);
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
    GL20.glDeleteProgram(programID);
  }

  private void createShader(int shaderID, String shaderFile, String shaderType) {
    // Set and Compile Shader
    GL20.glShaderSource(shaderID, shaderFile);
    GL20.glCompileShader(shaderID);
    // Check if Compiled
    if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
      System.err.println(shaderType + " Error: " + GL20.glGetShaderInfoLog(vertexID));
    }
  }

  private void attachShaders(int[] shaderIDs) {
    for (int shaderID : shaderIDs) {
      GL20.glAttachShader(programID, shaderID);
    }
  }

  private void deleteShaders(int[] shaderIDs) {
    for (int shaderID : shaderIDs) {
      GL20.glDeleteShader(shaderID);
    }
  }

  private void linkProgram() {
    GL20.glLinkProgram(programID);
    // Check if Linked Properly
    if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
      System.err.println("Program Link Error: " + GL20.glGetShaderInfoLog(vertexID));
    }
  }

  private void validateProgram() {
    GL20.glValidateProgram(programID);
    // Check if Validated Properly
    if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
      System.err.println("Program Validation Error: " + GL20.glGetShaderInfoLog(vertexID));
    }
  }

}
