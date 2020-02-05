import game.Game;
import map.MapGenerator;

/**
 * The type Main.
 */
public class Main {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    new MapGenerator(4,4,0.25,0.25,0.25,0.25);
    Game game = new Game();
    game.start();
  }
}
