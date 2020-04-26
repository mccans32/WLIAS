import game.Game;
import neat.Client;
import neat.Neat;
import neat.genomes.ConnectionGene;

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
    Neat neat = new Neat(10, 1, 1000);

    double[] in = new double[10];
    for (int i = 0; i < 10; i++) {
      in[i] = Math.random();
    }

    for (int i = 0; i < 100; i++) {
      for (Client c : neat.getClients().getData()) {
        double score = c.calculate(in)[0];
        c.setScore(score);
      }
      neat.evolve();
      //neat.printSpecies();
    }

    for (Client c : neat.getClients().getData()) {
      for (ConnectionGene g : c.getGenome().getConnections().getData()) {
        System.out.print(g.getInnovationNumber() + " ");
      }
      System.out.println();
    }

    if (!testing) {
      Game game = new Game();
      game.start();
    }

  }
}
