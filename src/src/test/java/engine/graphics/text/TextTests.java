package engine.graphics.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TextTests {
  private static final String[] TEST_STRINGS = {"", " ", "\n", "test", "test ", "test string"};
  private static final float[] TEST_FONT_SIZES = {1, 1.5f, 0, -1f};
  private Text testText;

  @BeforeEach
  public void setup() {
    testText = new Text(TEST_STRINGS[0], TEST_FONT_SIZES[0]);
  }

  @Test
  public void testSettersAndGetters() {
    assertEquals(testText.getFontSize(), 1f);
    for (float size: TEST_FONT_SIZES) {
      testText.setFontSize(size);
      assertEquals(testText.getFontSize(), size);
    }
    for (String string: TEST_STRINGS) {
      testText.setString(string);
      assertEquals(testText.getString(), string);
    }
  }
}
