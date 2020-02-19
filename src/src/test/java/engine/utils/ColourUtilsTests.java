package test.engine.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import engine.utils.ColourUtils;
import java.awt.Color;
import math.Vector3f;
import org.jfree.chart.ChartColor;
import org.junit.jupiter.api.Test;

public class ColourUtilsTests {
  private Color[] testColors = {Color.RED, ChartColor.BLUE, Color.GREEN};
  private Vector3f[] testColourResults = {new Vector3f(1f, 0f, 0f),
      new Vector3f(0f, 0f, 1f), new Vector3f(0f, 1f, 0f)};

  @Test
  public void testConvertColor() {
    for (int i = 0; i <  testColors.length; i++) {
      Vector3f conversion = ColourUtils.convertColor(testColors[i]);
      Vector3f assumption = testColourResults[i];
      // Test Red Value
      assertEquals(conversion.getX(), assumption.getX());
      // Test Blue Value
      assertEquals(conversion.getY(), assumption.getY());
      // Test Green Value
      assertEquals(conversion.getZ(), assumption.getZ());
    }
  }
}
