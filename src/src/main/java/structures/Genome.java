package structures;

public class Genome {
  private RandomHashSet<ConnectionGene> connectionGenes = new RandomHashSet<>();
  private RandomHashSet<NodeGene> nodeGenes = new RandomHashSet<>();

  private Neat neat;

  public Genome(Neat neat) {
    this.neat = neat;
  }

  /**
   * Crossover two genomes, the first is assumed to have he higher score.
   * Takes all the genes of A
   * If there is a genome that is also in B, choose randomly
   * do not take the disjoint genes of B
   * Take excess genes of A if they exist
   *
   * @param g1 The first Genome
   * @param g2 The second Genome
   * @return the genome
   */
  public static Genome crossover(Genome g1, Genome g2) {
    Neat neat = g1.getNeat();

    Genome child = neat.createEmptyGenome();

    int indexG1 = 0;
    int indexG2 = 0;

    while (indexG1 < g1.getConnectionGenes().size() && indexG2 < g2.getConnectionGenes().size()) {
      ConnectionGene gene1 = g1.getConnectionGenes().get(indexG1);
      ConnectionGene gene2 = g2.getConnectionGenes().get(indexG2);

      int innovation1 = gene1.getInnovationNumber();
      int innovation2 = gene2.getInnovationNumber();

      if (innovation1 == innovation2) {
        // Similar Gene
        // Choose to Inherit at random
        if (Math.random() > 0.5) {
          child.getConnectionGenes().add(Neat.getConnection(gene1));
        } else {
          child.getConnectionGenes().add(Neat.getConnection(gene2));
        }
        indexG1++;
        indexG2++;
      } else if (innovation1 > innovation2) {
        // child.getConnectionGenes().add(Neat.getConnection(gene2));
        // Disjoint Gene of B
        indexG2++;
      } else {
        // Disjoint Gene of A
        child.getConnectionGenes().add(Neat.getConnection(gene1));
        indexG1++;
      }
    }

    while (indexG1 < g1.getConnectionGenes().size()) {
      ConnectionGene gene1 = g1.getConnectionGenes().get(indexG1);
      child.getConnectionGenes().add(Neat.getConnection(gene1));
      indexG1++;
    }

    for (ConnectionGene connection : child.getConnectionGenes().getData()) {
      child.getNodeGenes().add(connection.getFromGene());
      child.getNodeGenes().add(connection.getToGene());
    }

    return child;
  }

  /**
   * calculate the distance between this genome and the provided second genome.
   * This genome must have the larger innovation number;
   *
   * @param g2 the g 2
   * @return the double
   */
  public double distance(Genome g2) {
    Genome g1 = this;

    int highestInnovationG1 =
        g1.getConnectionGenes().get(g1.getConnectionGenes().size() - 1).getInnovationNumber();
    int highestInnovationG2 =
        g2.getConnectionGenes().get(g2.getConnectionGenes().size() - 1).getInnovationNumber();

    // Swap
    if (highestInnovationG1 > highestInnovationG2) {
      Genome temp = g1;
      g1 = g2;
      g2 = temp;
    }

    int indexG1 = 0;
    int indexG2 = 0;

    int disjoint = 0;
    int excess;
    double weightDifference = 0;
    int similar = 0;

    while (indexG1 < g1.getConnectionGenes().size() && indexG2 < g2.getConnectionGenes().size()) {
      ConnectionGene gene1 = g1.getConnectionGenes().get(indexG1);
      ConnectionGene gene2 = g2.getConnectionGenes().get(indexG2);

      int innovation1 = gene1.getInnovationNumber();
      int innovation2 = gene2.getInnovationNumber();

      if (innovation1 == innovation2) {
        // Similar Gene
        similar++;
        weightDifference += Math.abs(gene1.getWeight() - gene2.getWeight());
        indexG1++;
        indexG2++;
      } else if (innovation1 > innovation2) {
        // Disjoint Gene of B
        indexG2++;
        disjoint++;
      } else {
        // Disjoint Gene of A
        indexG1++;
        disjoint++;
      }
    }

    weightDifference /= similar;
    excess = g1.getConnectionGenes().size() - indexG1;

    double n = Math.max(g1.getConnectionGenes().size(), g2.getConnectionGenes().size());

    if (n < Neat.GENE_MIN) {
      n = 1;
    }

    return (Neat.C1 * (disjoint / n)) + (Neat.C2 * (excess / n)) + (Neat.C3 * weightDifference);
  }

  /**
   * Mutate the genome.
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
   * Mutate a link between two nodes.
   */
  public void mutateLink() {
    for (int i = 0; i < 100; i++) {
      NodeGene node1 = nodeGenes.getRandom();
      NodeGene node2 = nodeGenes.getRandom();

      if (node1.getX() == node2.getX()) {
        continue;
      }

      ConnectionGene connection;
      if (node1.getX() < node2.getX()) {
        connection = new ConnectionGene(node1, node2);
      } else {
        connection = new ConnectionGene(node2, node1);
      }

      if (connectionGenes.contains(connection)) {
        continue;
      }

      connection = neat.getConnection(connection.getFromGene(), connection.getToGene());
      connection.setWeight((Math.random() * 2 - 1) * Neat.WEIGHT_RANDOM_STRENGTH);

      connectionGenes.addSorted(connection);
      return;
    }
  }

  /**
   * Mutate a link to add a new node.
   */
  public void mutateNode() {
    ConnectionGene connection = getConnectionGenes().getRandom();

    if (connection != null) {
      NodeGene from = connection.getFromGene();
      NodeGene to = connection.getToGene();
      NodeGene middle = neat.getNode();
      middle.setX((from.getX() + to.getX()) / 2);
      middle.setY((from.getY() + to.getY()) / 2);

      ConnectionGene connection1 = neat.getConnection(from, middle);
      ConnectionGene connection2 = neat.getConnection(middle, to);

      connection1.setWeight(1);
      connection2.setWeight(connection.getWeight());
      connection2.setEnabled(connection.isEnabled());

      connectionGenes.remove(connection);
      connectionGenes.add(connection1);
      connectionGenes.add(connection2);
      nodeGenes.add(middle);
    }
  }

  /**
   * Mutate a connection to shift its weight.
   */
  public void mutateWeightShift() {
    ConnectionGene connection = connectionGenes.getRandom();
    if (connection != null) {
      connection.setWeight(connection.getWeight() + (Math.random() * 2 - 1)
          * Neat.WEIGHT_SHIFT_STRENGTH);
    }
  }

  /**
   * Mutate a connection to randomize its weight.
   */
  public void mutateWeightRandom() {
    ConnectionGene connection = connectionGenes.getRandom();
    if (connection != null) {
      connection.setWeight((Math.random() * 2 - 1) * Neat.WEIGHT_RANDOM_STRENGTH);
    }
  }

  /**
   * Mutate a connection to toggle if it is enabled.
   */
  public void mutateLinkToggle() {
    ConnectionGene connection = connectionGenes.getRandom();
    if (connection != null) {
      connection.setEnabled(!connection.isEnabled());
    }
  }

  public RandomHashSet<ConnectionGene> getConnectionGenes() {
    return connectionGenes;
  }

  public RandomHashSet<NodeGene> getNodeGenes() {
    return nodeGenes;
  }

  public Neat getNeat() {
    return neat;
  }
}
