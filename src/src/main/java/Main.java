import game.Game;
import java.rmi.UnexpectedException;
import java.util.Arrays;
import map.MapGenerator;
import map.tiles.Tile;

/**
 * The type Main.
 */
public class Main {

  /**
   * The entry point of application.
   *
   * @param args the input arguments.
   */
  public static void main(String[] args) {
    Game game = new Game();
    game.start();
  }
}
