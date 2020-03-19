package engine.graphics.text;

import engine.utils.ColourUtils;
import java.awt.Color;
import math.Vector3f;

public class Text {
  private int numColumns = 16;
  private int numRows = 16;
  private Vector3f textColour = ColourUtils.convertColor(Color.BLACK);
  private String fontFile = "/text/defaultFont.png";
  private boolean centreHorizontal = false;
  private boolean centreVertical = false;
  private float fontSize = 1;
  private String text;

  /**
   * Instantiates a new Text.
   *
   * @param text             the text
   * @param fontFile         the font file
   * @param numColumns       the num columns
   * @param numRows          the num rows
   * @param textColour       the text colour
   * @param fontSize         the font size
   * @param centreHorizontal the centre horizontal
   * @param centreVertical   the centre vertical
   */
  public Text(String text, String fontFile, int numColumns, int numRows, Vector3f textColour,
              float fontSize, boolean centreHorizontal, boolean centreVertical) {
    this.text = text;
    this.fontFile = fontFile;
    this.numColumns = numColumns;
    this.numRows = numRows;
    this.textColour = textColour;
    this.fontSize = fontSize;
    this.centreHorizontal = centreHorizontal;
    this.centreVertical = centreVertical;
  }

  public Text(String text) {
    this.text = text;
  }

  /**
   * Instantiates a new Text.
   *
   * @param text       the text
   * @param fontSize   the font size
   * @param textColour the text colour
   */
  public Text(String text, float fontSize, Vector3f textColour) {
    this(text);
    this.fontSize = fontSize;
    this.textColour = textColour;
  }

  /**
   * Instantiates a new Text.
   *
   * @param text             the text
   * @param fontSize         the font size
   * @param centreHorizontal the centre horizontal
   * @param centreVertical   the centre vertical
   */
  public Text(String text, float fontSize, Vector3f textColour, boolean centreHorizontal,
              boolean centreVertical) {
    this(text, fontSize, textColour);
    this.centreHorizontal = centreHorizontal;
    this.centreVertical = centreVertical;
  }

  public int getNumColumns() {
    return numColumns;
  }

  public int getNumRows() {
    return numRows;
  }

  public boolean isCentreHorizontal() {
    return centreHorizontal;
  }

  public void setCentreHorizontal(boolean centreHorizontal) {
    this.centreHorizontal = centreHorizontal;
  }

  public boolean isCentreVertical() {
    return centreVertical;
  }

  public void setCentreVertical(boolean centreVertical) {
    this.centreVertical = centreVertical;
  }

  public Vector3f getTextColour() {
    return textColour;
  }

  public void setTextColour(Vector3f textColour) {
    this.textColour = textColour;
  }

  public String getFontFile() {
    return fontFile;
  }

  public float getFontSize() {
    return fontSize;
  }

  public void setFontSize(float fontSize) {
    this.fontSize = fontSize;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
