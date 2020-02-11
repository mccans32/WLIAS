import game.Game;
import java.rmi.UnexpectedException;
import java.util.Arrays;
import map.MapGenerator;

/**
 * The type Main.
 */
public class Main {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @throws UnexpectedException the unexpected exception
   */
  public static void main(String[] args) throws UnexpectedException {
    MapGenerator map = new MapGenerator( 4,4, 4, 6,
        3, 3, 1);
    System.out.println(Arrays.deepToString(map.getMapOfOrderedTiles()));
    Game game = new Game();
    game.start();
  }
}
