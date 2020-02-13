package engine.utils;

import java.awt.Color;
import math.Vector3f;

public class ColourUtils {
  /**
   * Convert color vector 3 f.
   *
   * @param colour the colour
   * @return the vector 3 f
   */
  // Takes in a colour from the java.awt.Color package and returns as RGB in the for Vector3f
  public static Vector3f convertColor(Color colour) {
    float maxColorValue = 255;
    return new Vector3f(colour.getRed() / maxColorValue,
        colour.getGreen() / maxColorValue, colour.getBlue() / maxColorValue);
  }
}
