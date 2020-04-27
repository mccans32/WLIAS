package engine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import neat.Neat;

public class ObjectFileIO {

  /**
   * Write a {@link Neat} object to a given path.
   *
   * @param path the path
   * @param neat the neat
   * @throws IOException the io exception
   */
  public static void writeNeatToFile(String path, Neat neat) throws IOException {
    File file = new File(path);

    boolean fileCreated = true;
    if (!file.exists()) {
      fileCreated = file.createNewFile();
    }

    if (fileCreated) {
      FileOutputStream f = new FileOutputStream(new File(path));
      ObjectOutputStream o = new ObjectOutputStream(f);

      //Write Object to File
      o.writeObject(neat);

      o.close();
      f.close();
    }
  }

  /**
   * Read a {@link Neat} object from a file.
   *
   * @param path the path
   * @return the neat
   * @throws IOException            the io exception
   * @throws ClassNotFoundException the class not found exception
   */
  public static Neat readNeatFromFile(String path) throws IOException, ClassNotFoundException {
    FileInputStream fi = new FileInputStream(new File(path));
    ObjectInput oi = new ObjectInputStream(fi);

    // Read the object
    Neat neat = (Neat) oi.readObject();

    oi.close();
    fi.close();

    return neat;
  }
}
