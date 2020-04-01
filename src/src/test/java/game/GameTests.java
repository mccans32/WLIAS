package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GameTests {

  @Test
  public void testState() {
    assertEquals(Game.getState(), GameState.MAIN_MENU);
    Game.setState(GameState.GAME_MAIN);
    assertEquals(Game.getState(), GameState.GAME_MAIN);
  }
}
