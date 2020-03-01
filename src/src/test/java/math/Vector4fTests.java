package math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Vector4fTests {

  @Test public void testEquals() {
    assertEquals(new Vector4f(0, 1f, 2f, 3f), new Vector4f(0, 1f, 2f, 3f));
    assertEquals(new Vector4f(2, 1.4f, 2.6f, 3.7f), new Vector4f(2, 1.4f, 2.6f, 3.7f));
  }
}
