import game.Game;
import structures.Genome;
import structures.Neat;

/**
 * The type Main.
 */
public class Main {
  private static boolean testing = true;

  /**
   * The entry point of application.
   *
   * @param args the input arguments.
   */
  public static void main(String[] args) {
    Neat neat  = new Neat(8, 20, 100);
    Genome genome = neat.createEmptyGenome();
    System.out.println(genome.getNodeGenes().size());
    if (!testing) {
      Game game = new Game();
      game.start();
    }

  }
}
