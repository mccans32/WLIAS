package engine.graphics.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ImageTests {
  private String[] imagePaths = new String[] {
      "/images/aridTile.jpg",
      "/images/fertileTile.jpg",
      "/images/plainTile.jpg",
      "images/default_texture.png"};

  @Test
  public void testGetters() {
    Image image;
    for (String path : imagePaths) {
      image = new Image(path);
      assertEquals(path, image.getTexturePath());
    }
  }

}
