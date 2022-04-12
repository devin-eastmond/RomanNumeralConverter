/**
 * An exception class specifically for invalid Roman numeral strings.
 */
public class InvalidRomanNumeralException extends Exception {

  /**
   * The constructor.
   *
   * @param message the error message.
   */
  public InvalidRomanNumeralException(String message) {
    super(message);
  }
}
