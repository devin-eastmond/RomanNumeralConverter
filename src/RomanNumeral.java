import java.util.Map;
import java.util.TreeSet;

/**
 * A class that represents Roman numerals.
 */
public class RomanNumeral {

  /**
   * The value of each valid Roman numeral symbol.
   */
  private final Map<Character, Integer> VALID_SYMBOLS = Map.ofEntries(
          Map.entry('I', 1),
          Map.entry('V', 5),
          Map.entry('X', 10),
          Map.entry('L', 50),
          Map.entry('C', 100),
          Map.entry('D', 500),
          Map.entry('M', 1000)
  );

  /**
   * The value of each Roman numeral symbol including subtraction notation.
   */
  private static final Map<Integer, String> NUMERAL_VALUES = Map.ofEntries(
          Map.entry(1, "I"),
          Map.entry(4, "IV"),
          Map.entry(5, "V"),
          Map.entry(9, "IX"),
          Map.entry(10, "X"),
          Map.entry(40, "XL"),
          Map.entry(45, "VL"),
          Map.entry(50, "L"),
          Map.entry(90, "XC"),
          Map.entry(100, "C"),
          Map.entry(400, "CD"),
          Map.entry(450, "LD"),
          Map.entry(500, "D"),
          Map.entry(900, "CM"),
          Map.entry(1000, "M")
  );

  /**
   * The Roman numeral represented as a string.
   */
  private final String romanNumeralString;

  /**
   * The integer value of the Roman numeral.
   */
  private Integer decimalValue = null;

  /**
   * Creates a RomanNumeral object from a decimal value.
   *
   * @param decimalValue the decimal value to convert into a Roman numeral.
   * @return the newly created RomanNumeral object.
   */
  public static RomanNumeral createFromDecimal(int decimalValue) {
    String romanNumeral = convertIntegerToRomanNumeral(decimalValue);

    try {
      return new RomanNumeral(romanNumeral);
    } catch (InvalidRomanNumeralException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Converts an integer into a Roman numeral string.
   *
   * @param decimalValue the integer to convert into a Roman numeral.
   * @return the Roman numeral represented as a string.
   */
  private static String convertIntegerToRomanNumeral(int decimalValue) {
    StringBuilder romanNumeral = new StringBuilder();
    int valueReached = 0;
    TreeSet<Integer> values = new TreeSet<>(NUMERAL_VALUES.keySet());

    // In order to get the correct decimal value, we continue to add the highest possible values from the
    //  NUMERAL_VALUES map without exceeding the desired decimal value.
    while (valueReached < decimalValue) {
      for (Integer value : values.descendingSet()) {
        if (valueReached + value <= decimalValue) {
          valueReached += value;
          romanNumeral.append(NUMERAL_VALUES.get(value));
          break; // We must break and start the loop over as we could need to add the same symbol more than once
        }
      }
    }

    return romanNumeral.toString();
  }

  /**
   * A constructor that takes a Roman numeral string and verifies the string is a valid Roman numeral.
   *
   * @param romanNumeralString the Roman numeral represented as a string.
   * @throws InvalidRomanNumeralException if the Roman numeral is not formatted correctly.
   */
  public RomanNumeral(String romanNumeralString) throws InvalidRomanNumeralException {
    this.romanNumeralString = romanNumeralString.toUpperCase();
    verifyRomanNumeralString(this.romanNumeralString);
  }

  /**
   * Verify the Roman numeral is formatted correctly.
   *
   * @param romanNumeralString the Roman numeral represented as a string.
   * @throws InvalidRomanNumeralException if the Roman numeral is not formatted correctly.
   */
  private void verifyRomanNumeralString(String romanNumeralString) throws InvalidRomanNumeralException {
    for (int i = 0; i < romanNumeralString.length(); i++) {
      validateSymbol(romanNumeralString.charAt(i));
      validateSubtractionNotationLength(romanNumeralString, i);
      validateSubtractionNotation(romanNumeralString, i);
    }
  }

  /**
   * Ensures a symbol is a valid Roman numeral symbol.
   *
   * @param symbol the symbol to validate.
   * @throws InvalidRomanNumeralException if the symbol is invalid.
   */
  private void validateSymbol(char symbol) throws InvalidRomanNumeralException {
    if (!VALID_SYMBOLS.containsKey(symbol)) {
      throw new InvalidRomanNumeralException("Invalid Roman numeral string: illegal symbol '" + symbol + "'!");
    }
  }

  /**
   * Ensures the string does not contain subtraction notation beyond two symbols (e.g. "IVX" is an illegal string).
   *
   * @param romanNumeralString the Roman numeral represented as a string.
   * @param index the current index to verify.
   * @throws InvalidRomanNumeralException if subtraction notation is found beyond two symbols.
   */
  private void validateSubtractionNotationLength(String romanNumeralString, int index)
          throws InvalidRomanNumeralException {
    boolean firstSubtractionNotation = getIsSubtractiveNotation(romanNumeralString, index - 1);
    boolean secondSubtractionNotation = getIsSubtractiveNotation(romanNumeralString, index);

    if (firstSubtractionNotation && secondSubtractionNotation) {
      throw new InvalidRomanNumeralException("Roman numeral formatted incorrectly: subtraction notation can only be " +
              "applied to two symbols!");
    }
  }

  /**
   * Ensures the string does not contain illegal subtraction notation (e.g. "DM", "CI", etc.).
   *
   * @param romanNumeralString the Roman numeral represented as a string.
   * @param index the current index to verify.
   * @throws InvalidRomanNumeralException if the subtraction notation is invalid.
   */
  private void validateSubtractionNotation(String romanNumeralString, int index) throws InvalidRomanNumeralException {
    Integer currentSymbolValue = getValueOfSymbol(romanNumeralString, index);
    Integer nextSymbolValue = getValueOfSymbol(romanNumeralString, index + 1);
    if (nextSymbolValue == null) {
      return;
    }
    // In subtraction notation, any given symbol with a value x may not proceed any symbol larger than 10x (e.g. IC).
    //  It also may not proceed any symbol twice its value (e.g. VX).
    if (nextSymbolValue > (10 * currentSymbolValue) || nextSymbolValue == (currentSymbolValue * 2)) {
      char currentSymbol = romanNumeralString.charAt(index);
      char nextSymbol = romanNumeralString.charAt(index + 1);
      throw new InvalidRomanNumeralException("Roman numeral formatted incorrectly: illegal subtraction notation \"" +
              currentSymbol + nextSymbol + "\"!");
    }
  }

  /**
   * Get the Roman numeral represented as a string.
   *
   * @return the Roman numeral represented as a string.
   */
  public String getRomanNumeralString() {
    return romanNumeralString;
  }

  /**
   * Converts the Roman numeral string into a decimal number and returns it.
   *
   * @return the decimal representation of the Roman numeral.
   */
  public Integer getDecimalValue() {
    if (decimalValue == null) {
      decimalValue = convertRomanNumeralToInteger(romanNumeralString);
    }

    return decimalValue;
  }

  /**
   * Converts the Roman numeral (represented as a String) to an integer.
   *
   * @param romanNumeral the Roman numeral represented as a string.
   * @return the integer value of the Roman numeral.
   */
  private int convertRomanNumeralToInteger(String romanNumeral) {
    int totalValue = 0;
    for (int i = 0; i < romanNumeral.length(); i++) {
      int charValue = getValueOfSymbol(romanNumeral, i);
      if (getIsSubtractiveNotation(romanNumeral, i)) {
        totalValue -= charValue;
      } else {
        totalValue += charValue;
      }
    }

    return totalValue;
  }

  /**
   * Computes if the numerals at index and index + 1 form subtractive notation. Subtractive notation is
   * when a symbol A has a value less than the symbol following it (B). For example, IV forms subtractive
   * notation as the value of I, 1, is less than the value of V, 5.
   *
   * @param romanNumeral the Roman numeral represented as a string.
   * @param index the index where the subtractive notation is expected to start.
   * @return true if subtractive notation is found, otherwise false
   */
  private boolean getIsSubtractiveNotation(String romanNumeral, int index) {
    Integer currentSymbolValue = getValueOfSymbol(romanNumeral, index);
    Integer nextSymbolValue = getValueOfSymbol(romanNumeral, index + 1);

    return (currentSymbolValue != null && nextSymbolValue != null && currentSymbolValue < nextSymbolValue);
  }

  /**
   * Returns the integer value of a single char in the Roman numeral string.
   *
   * @param romanNumeral the Roman numeral represented as a string.
   * @param index the index of the char.
   * @return the integer of the char, or null if the index is out of range.
   */
  private Integer getValueOfSymbol(String romanNumeral, int index) {
    if (index >= romanNumeral.length() || index < 0) {
      return null;
    }

    return VALID_SYMBOLS.get(romanNumeral.charAt(index));
  }
}
