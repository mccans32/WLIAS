import game.Game;
import neat.Neat;
import neat.genomes.Genome;
import neat.visual.Frame;

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
    Neat neat = new Neat(2, 2, 0);
    Genome genome = neat.createEmptyGenome();
    new Frame(genome);

    if (!testing) {
      Game game = new Game();
      game.start();
    }

  }
}
