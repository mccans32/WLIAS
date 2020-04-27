package neat.genomes;

import java.io.Serializable;

public abstract class Gene implements Serializable {

  //default serialVersion id
  private static final long serialVersionUID = 1L;

  protected int innovationNumber;

  public Gene(int innovationNumber) {
    this.innovationNumber = innovationNumber;
  }

  public Gene() {
  }

  public int getInnovationNumber() {
    return innovationNumber;
  }

  public void setInnovationNumber(int innovationNumber) {
    this.innovationNumber = innovationNumber;
  }
}