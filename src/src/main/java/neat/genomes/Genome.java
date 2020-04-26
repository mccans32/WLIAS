package neat.genomes;

import neat.Neat;
import neat.calculations.Calculator;

public class Genome {

  private RandomHashSet<ConnectionGene> connections = new RandomHashSet<>();
  private RandomHashSet<NodeGene> nodes = new RandomHashSet<>();

  private Neat neat;
  private Calculator calculator;


  public Genome(Neat neat) {
    this.neat = neat;
  }

  /**
   * creates a new genome.
   * g1 should have the higher score
   * - take all the genes of a
   * - if there is a genome in a that is also in b, choose randomly
   * - do not take disjoint genes of b
   * - take excess genes of a if they exist
   *
   * @param g1 the first genome
   * @param g2 the second genome
   * @return the child genome
   */
  public static Genome crossOver(Genome g1, Genome g2) {
    Neat neat = g1.getNeat();

    Genome genome = neat.createEmptyGenome();

    int indexG1 = 0;
    int indexG2 = 0;

    while (indexG1 < g1.getConnections().size() && indexG2 < g2.getConnections().size()) {

      ConnectionGene gene1 = g1.getConnections().get(indexG1);
      ConnectionGene gene2 = g2.getConnections().get(indexG2);

      int in1 = gene1.getInnovationNumber();
      int in2 = gene2.getInnovationNumber();

      if (in1 == in2) {

        if (Math.random() > 0.5) {
          genome.getConnections().add(Neat.getConnection(gene1));
        } else {
          genome.getConnections().add(Neat.getConnection(gene2));
        }
        indexG1++;
        indexG2++;
      }
      if (in1 > in2) {
        //genome.getConnections().add(neat.getConnection(gene2));
        //disjoint gene of b
        indexG2++;
      } else {
        //disjoint gene of a
        genome.getConnections().add(Neat.getConnection(gene1));
        indexG1++;
      }
    }

    while (indexG1 < g1.getConnections().size()) {
      ConnectionGene gene1 = g1.getConnections().get(indexG1);
      genome.getConnections().add(Neat.getConnection(gene1));
      indexG1++;
    }

    for (ConnectionGene c : genome.getConnections().getData()) {
      genome.getNodes().add(c.getFrom());
      genome.getNodes().add(c.getTo());
    }

    return genome;
  }

  /**
   * calculated the distance between this genome g1 and a second genome g2.
   * - g1 must have the highest innovation number!
   *
   * @param g2 the second genome
   * @return the distance
   */
  public double distance(Genome g2) {

    Genome g1 = this;

    int highestInnovationGene1 =
        g1.getConnections().get(g1.getConnections().size() - 1).getInnovationNumber();
    int highestInnovationGene2 =
        g2.getConnections().get(g2.getConnections().size() - 1).getInnovationNumber();

    if (highestInnovationGene1 < highestInnovationGene2) {
      Genome g = g1;
      g1 = g2;
      g2 = g;
    }

    int indexG1 = 0;
    int indexG2 = 0;

    int disjoint = 0;
    int excess;
    double weightDiff = 0;
    int similar = 0;

    while (indexG1 < g1.getConnections().size() && indexG2 < g2.getConnections().size()) {

      ConnectionGene gene1 = g1.getConnections().get(indexG1);
      ConnectionGene gene2 = g2.getConnections().get(indexG2);

      int in1 = gene1.getInnovationNumber();
      int in2 = gene2.getInnovationNumber();

      if (in1 == in2) {
        // similar gene
        similar++;
        weightDiff += Math.abs(gene1.getWeight() - gene2.getWeight());
        indexG1++;
        indexG2++;
      }
      disjoint++;
      if (in1 > in2) {
        // disjoint gene of b
        indexG2++;
      } else {
        // disjoint gene of a
        indexG1++;
      }
    }

    weightDiff /= similar;
    excess = g1.getConnections().size() - indexG1;


    double n = Math.max(g1.getConnections().size(), g2.getConnections().size());
    if (n < 20) {
      n = 1;
    }

    return Neat.C1 * disjoint / n + Neat.C2 * excess / n + Neat.C3 * weightDiff / n;

  }

  public void generate_calculator() {
    this.calculator = new Calculator(this);
  }

  /**
   * Calculate the output array for this genome.
   *
   * @param ar the ar
   * @return the double [ ]
   */
  public double[] calculate(double... ar) {
    if (calculator != null) {
      return calculator.calculate(ar);
    }
    return null;
  }

  /**
   * Mutate this genome.
   */
  public void mutate() {
    if (Neat.PROBABILITY_MUTATE_LINK > Math.random()) {
      mutateLink();
    }
    if (Neat.PROBABILITY_MUTATE_NODE > Math.random()) {
      mutateNode();
    }
    if (Neat.PROBABILITY_MUTATE_WEIGHT_SHIFT > Math.random()) {
      mutateWeightShift();
    }
    if (Neat.PROBABILITY_MUTATE_WEIGHT_RANDOM > Math.random()) {
      mutateWeightRandom();
    }
    if (Neat.PROBABILITY_MUTATE_TOGGLE_LINK > Math.random()) {
      mutateLinkToggle();
    }
  }

  /**
   * Mutates a link.
   */
  public void mutateLink() {

    for (int i = 0; i < 100; i++) {

      NodeGene a = nodes.randomElement();
      NodeGene b = nodes.randomElement();

      if (a.getX() == b.getX()) {
        continue;
      }

      ConnectionGene con;
      if (a.getX() < b.getX()) {
        con = new ConnectionGene(a, b);
      } else {
        con = new ConnectionGene(b, a);
      }

      if (connections.contains(con)) {
        continue;
      }

      con = neat.getConnection(con.getFrom(), con.getTo());
      con.setWeight((Math.random() * 2 - 1) * Neat.WEIGHT_RANDOM_STRENGTH);

      connections.addSorted(con);
      return;
    }
  }

  /**
   * Mutates a link to add an extra node.
   */
  public void mutateNode() {
    ConnectionGene con = connections.randomElement();
    if (con == null) {
      return;
    }

    NodeGene from = con.getFrom();
    NodeGene to = con.getTo();

    NodeGene middle = neat.getNode();
    middle.setX((from.getX() + to.getX()) / 2);
    middle.setY((from.getY() + to.getY()) / 2 + Math.random() * 0.1 - 0.05);

    ConnectionGene con1 = neat.getConnection(from, middle);
    ConnectionGene con2 = neat.getConnection(middle, to);

    con1.setWeight(1);
    con2.setWeight(con.getWeight());
    con2.setEnabled(con.isEnabled());

    connections.remove(con);
    connections.add(con1);
    connections.add(con2);

    nodes.add(middle);
  }

  /**
   * Mutates a weight to shift.
   */
  public void mutateWeightShift() {
    ConnectionGene con = connections.randomElement();
    if (con != null) {
      con.setWeight(con.getWeight() + (Math.random() * 2 - 1) * Neat.WEIGHT_SHIFT_STRENGTH);
    }
  }

  /**
   * Mutates a weight to a new random value.
   */
  public void mutateWeightRandom() {
    ConnectionGene con = connections.randomElement();
    if (con != null) {
      con.setWeight((Math.random() * 2 - 1) * Neat.WEIGHT_RANDOM_STRENGTH);
    }
  }

  /**
   * Toggled whether a lnk is enabled or disabled.
   */
  public void mutateLinkToggle() {
    ConnectionGene con = connections.randomElement();
    if (con != null) {
      con.setEnabled(!con.isEnabled());
    }
  }

  public RandomHashSet<ConnectionGene> getConnections() {
    return connections;
  }

  public RandomHashSet<NodeGene> getNodes() {
    return nodes;
  }

  public Neat getNeat() {
    return neat;
  }
}