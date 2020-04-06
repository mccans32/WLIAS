package engine.graphics.text;

import engine.utils.ColourUtils;
import java.awt.Color;
import math.Vector3f;
import math.Vector4f;

public class Text {
  private int numColumns = 16;
  private int numRows = 16;
  private Vector4f textColour = new Vector4f(ColourUtils.convertColor(Color.BLACK), 1);
  private String fontFile = "/text/defaultFont.png";
  private boolean centreHorizontal = false;
  private boolean centreVertical = false;
  private boolean shouldWrap = false;
  private float fontSize = 1;
  private String string;

  /**
   * Instantiates a new Text.
   *
   * @param string           the text
   * @param fontFile         the font file
   * @param numColumns       the num columns
   * @param numRows          the num rows
   * @param textColour       the text colour
   * @param fontSize         the font size
   * @param centreHorizontal the centre horizontal
   * @param centreVertical   the centre vertical
   */
  public Text(String string, String fontFile, int numColumns, int numRows, Vector4f textColour,
              float fontSize, boolean centreHorizontal, boolean centreVertical,
              boolean shouldWrap) {
    this.string = string;
    this.fontFile = fontFile;
    this.numColumns = numColumns;
    this.numRows = numRows;
    this.textColour = textColour;
    this.fontSize = fontSize;
    this.centreHorizontal = centreHorizontal;
    this.centreVertical = centreVertical;
    this.shouldWrap = shouldWrap;
  }

  public Text(String string) {
    this.string = string;
  }

  public Text(String string, float fontSize) {
    this(string);
    this.fontSize = fontSize;
  }

  /**
   * Instantiates a new Text.
   *
   * @param string     the string
   * @param fontSize   the font size
   * @param textColour the text colour
   */
  public Text(String string, float fontSize, Vector4f textColour) {
    this(string, fontSize);
    this.textColour = textColour;
  }

  public Text(String string, float fontSize, Vector3f textColour) {
    this(string, fontSize, new Vector4f(textColour, 1));
  }

  /**
   * Instantiates a new Text.
   *
   * @param string           the string
   * @param fontSize         the font size
   * @param centreHorizontal the centre horizontal
   * @param centreVertical   the centre vertical
   */
  public Text(String string, float fontSize, Vector4f textColour, boolean centreHorizontal,
              boolean centreVertical, boolean shouldWrap) {
    this(string, fontSize, textColour);
    this.centreHorizontal = centreHorizontal;
    this.centreVertical = centreVertical;
    this.shouldWrap = shouldWrap;
  }

  public Text(String string, float fontSize, Vector3f textColour, boolean centreHorizontal,
              boolean centreVertical, boolean shouldWrap) {
    this(string, fontSize, new Vector4f(textColour, 1), centreHorizontal, centreVertical,
        shouldWrap);
  }

  public boolean shouldWrap() {
    return shouldWrap;
  }

  public void setShouldWrap(boolean shouldWrap) {
    this.shouldWrap = shouldWrap;
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

  public Vector4f getTextColour() {
    return textColour;
  }

  public void setTextColour(Vector4f textColour) {
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

  public String getString() {
    return string;
  }

  public void setString(String string) {
    this.string = string;
  }

  public Text copy() {
    return new Text(this.string, this.fontFile, this.numColumns, this.numRows, this.textColour,
        this.fontSize, this.centreHorizontal, this.centreVertical, this.shouldWrap);
  }

  public void setAlpha(float alpha) {
    this.textColour.setW(alpha);
  }
}
