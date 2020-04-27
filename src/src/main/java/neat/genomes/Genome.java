package neat.genomes;

import java.io.Serializable;
import neat.Neat;

public class Genome implements Serializable {

  //default serialVersion id
  private static final long serialVersionUID = 1L;

  private RandomHashSet<ConnectionGene> connections = new RandomHashSet<>();
  private RandomHashSet<NodeGene> nodes = new RandomHashSet<>();

  private Neat neat;


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

    Genome genome = neat.emptyGenome();

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
      } else if (in1 > in2) {
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
   * @param g2 the other genome
   * @return the distance between this and the second genome
   */
  public double distance(Genome g2) {

    Genome g1 = this;
    int highestInnovationGene1 = 0;
    if (g1.getConnections().size() != 0) {
      highestInnovationGene1
          = g1.getConnections().get(g1.getConnections().size() - 1).getInnovationNumber();
    }

    int highestInnovationGene2 = 0;
    if (g2.getConnections().size() != 0) {
      highestInnovationGene2
          = g2.getConnections().get(g2.getConnections().size() - 1).getInnovationNumber();
    }

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
        //similar gene
        similar++;
        weightDiff += Math.abs(gene1.getWeight() - gene2.getWeight());
        indexG1++;
        indexG2++;
      } else if (in1 > in2) {
        //disjoint gene of b
        disjoint++;
        indexG2++;
      } else {
        //disjoint gene of a
        disjoint++;
        indexG1++;
      }
    }

    weightDiff /= Math.max(1, similar);
    excess = g1.getConnections().size() - indexG1;

    double n = Math.max(g1.getConnections().size(), g2.getConnections().size());
    if (n < 20) {
      n = 1;
    }

    return neat.getC1() * disjoint / n + neat.getC2() * excess / n + neat.getC3() * weightDiff / n;

  }

  /**
   * Mutate.
   */
  public void mutate() {
    if (neat.getProbabilityMutateLink() > Math.random()) {
      mutateLink();
    }
    if (neat.getProbabilityMutateNode() > Math.random()) {
      mutateNode();
    }
    if (neat.getProbabilityMutateWeightShift() > Math.random()) {
      mutateWeightShift();
    }
    if (neat.getProbabilityMutateWeightRandom() > Math.random()) {
      mutateWeightRandom();
    }
    if (neat.getProbabilityMutateToggleLink() > Math.random()) {
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

      if (a == null || b == null) {
        continue;
      }
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
      con.setWeight((Math.random() * 2 - 1) * neat.getWeightRandomStrength());

      connections.addSorted(con);
      return;
    }
  }

  /**
   * Mutates a node.
   */
  public void mutateNode() {
    ConnectionGene con = connections.randomElement();
    if (con == null) {
      return;
    }

    NodeGene from = con.getFrom();
    NodeGene to = con.getTo();

    int replaceIndex = neat.getReplaceIndex(from, to);
    NodeGene middle;
    if (replaceIndex == 0) {
      middle = neat.getNode();
      middle.setX((from.getX() + to.getX()) / 2);
      middle.setY((from.getY() + to.getY()) / 2 + Math.random() * 0.1 - 0.05);
      neat.setReplaceIndex(from, to, middle.getInnovationNumber());
    } else {
      middle = neat.getNode(replaceIndex);
    }

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
   * Mutates a weight by shifting its value.
   */
  public void mutateWeightShift() {
    ConnectionGene con = connections.randomElement();
    if (con != null) {
      con.setWeight(con.getWeight() + (Math.random() * 2 - 1) * neat.getWeightShiftStrength());
    }
  }

  /**
   * Mutates a weight by changing it value random;y.
   */
  public void mutateWeightRandom() {
    ConnectionGene con = connections.randomElement();
    if (con != null) {
      con.setWeight((Math.random() * 2 - 1) * neat.getWeightRandomStrength());
    }
  }

  /**
   * Mutates link ny toggling if it is enabled or disabled.
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