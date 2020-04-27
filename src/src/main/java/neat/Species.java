package neat;

import java.util.Comparator;
import neat.genomes.Genome;
import neat.genomes.RandomHashSet;

public class Species {

  private RandomHashSet<Client> clients = new RandomHashSet<>();
  private Client representative;
  private double score;

  /**
   * Instantiates a new Species.
   *
   * @param representative the representative
   */
  public Species(Client representative) {
    this.representative = representative;
    this.representative.setSpecies(this);
    clients.add(representative);
  }

  /**
   * Puts a client into a species if it is similar enough.
   *
   * @param client the client
   * @return the boolean
   */
  public boolean put(Client client) {
    if (client.distance(representative) < representative.getGenome().getNeat().getCP()) {
      client.setSpecies(this);
      clients.add(client);
      return true;
    }
    return false;
  }

  public void forcePut(Client client) {
    client.setSpecies(this);
    clients.add(client);
  }

  /**
   * Go extinct.
   */
  public void goExtinct() {
    for (Client c : clients.getData()) {
      c.setSpecies(null);
    }
  }

  /**
   * Evaluates the score of the species.
   */
  public void evaluateScore() {
    double v = 0;
    // For each client in this species add their score
    for (Client c : clients.getData()) {
      v += c.getScore();
    }
    // Return the average score
    score = v / clients.size();
  }

  /**
   * Reset.
   */
  public void reset() {
    // Select a random client to be a new representative
    representative = clients.randomElement();

    // Reset all of the clients
    for (Client c : clients.getData()) {
      c.setSpecies(null);
    }
    clients.clear();

    // Add the representative
    clients.add(representative);
    representative.setSpecies(this);
    score = 0;
  }

  /**
   * Kill.
   *
   * @param percentage the percentage
   */
  public void kill(double percentage) {
    clients.getData().sort(Comparator.comparingDouble(Client::getScore));

    double amount = percentage * this.clients.size();
    for (int i = 0; i < amount; i++) {
      clients.get(0).setSpecies(null);
      clients.remove(0);
    }
  }

  /**
   * Breeds the species to create a new genome.
   *
   * @return the genome
   */
  public Genome breed() {
    Client c1 = clients.randomElement();
    Client c2 = clients.randomElement();

    if (c1.getScore() > c2.getScore()) {
      return Genome.crossOver(c1.getGenome(), c2.getGenome());
    }
    return Genome.crossOver(c2.getGenome(), c1.getGenome());
  }

  public int size() {
    return clients.size();
  }

  public RandomHashSet<Client> getClients() {
    return clients;
  }

  public Client getRepresentative() {
    return representative;
  }

  public double getScore() {
    return score;
  }
}