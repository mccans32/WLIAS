package engine.graphics.text;

public class Text {
  private static final String DEFAULT_FONT_FILE = "/text/defaultFont.png";
  private String fontFile = DEFAULT_FONT_FILE;
  private boolean centreHorizontal = false;
  private boolean centreVertical = false;
  private float fontSize = 1;
  private String text;

  /**
   * Instantiates a new Text.
   *
   * @param text             the text
   * @param fontFile         the font file
   * @param fontSize         the font size
   * @param centreHorizontal the centre horizontal
   * @param centreVertical   the centre vertical
   */
  public Text(String text, String fontFile, float fontSize, boolean centreHorizontal,
              boolean centreVertical) {
    this.text = text;
    this.fontFile = fontFile;
    this.fontSize = fontSize;
    this.centreHorizontal = centreHorizontal;
    this.centreVertical = centreVertical;
  }

  public Text(String text) {
    this.text = text;
  }

  public Text(String text, float fontSize) {
    this(text);
    this.fontSize = fontSize;
  }

  /**
   * Instantiates a new Text.
   *
   * @param text             the text
   * @param fontSize         the font size
   * @param centreHorizontal the centre horizontal
   * @param centreVertical   the centre vertical
   */
  public Text(String text, float fontSize, boolean centreHorizontal, boolean centreVertical) {
    this(text, fontSize);
    this.centreHorizontal = centreHorizontal;
    this.centreVertical = centreVertical;
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

  public String getFontFile() {
    return fontFile;
  }

  public void setFontFile(String fontFile) {
    this.fontFile = fontFile;
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
