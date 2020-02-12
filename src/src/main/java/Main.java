import game.Game;
import java.rmi.UnexpectedException;
import java.util.Arrays;
import map.MapGenerator;
import map.Tile;

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
    //TODO move this functionality to the main menu when the functionality is created
    //creates a map object which has all parameters needed.
    MapGenerator map = new MapGenerator(4, 4, 4, 6,
        3, 3, 1);
    // Generates an ordered 2d array which is a representation of the map
    map.createMap();
    // retrieves the ordered map
    Tile[][] tile = map.getMapOfOrderedTiles();
    System.out.println(Arrays.deepToString(tile));
    Game game = new Game();
    game.start();
  }
}
