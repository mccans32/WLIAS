import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RunTests {

  @Test
  public void testAdd() {
    assertEquals(42, Integer.sum(19, 23));
  }
}
