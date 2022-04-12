import org.junit.Assert;
import org.junit.Test;

/**
 * The test suite for the RomanNumeral class.
 */
public class RomanNumeralTest {

  @Test
  public void test_RomanNumeral_InvalidSymbol() {
    assertThrowsInvalidRomanNumeralException("K");
  }

  @Test
  public void test_RomanNumeral_InvalidSubtractionNotationLength() {
    assertThrowsInvalidRomanNumeralException("VXL");
  }

  @Test
  public void test_RomanNumeral_InvalidSubtractionNotation() {
    assertThrowsInvalidRomanNumeralException("IC");
    assertThrowsInvalidRomanNumeralException("LC");
  }

  private void assertThrowsInvalidRomanNumeralException(String romanNumeralString) {
    Assert.assertThrows(InvalidRomanNumeralException.class, () -> new RomanNumeral(romanNumeralString));
  }

  @Test
  public void test_RomanNumeral_LowerCase() throws InvalidRomanNumeralException {
    RomanNumeral romanNumeral = new RomanNumeral("cxvi");
    int expected = 116;
    int actual = romanNumeral.getDecimalValue();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_RomanNumeral_OneSymbol() throws InvalidRomanNumeralException {
    RomanNumeral romanNumeral = new RomanNumeral("V");
    int expected = 5;
    int actual = romanNumeral.getDecimalValue();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_DecimalNumber_OneSymbol() {
    RomanNumeral romanNumeral = RomanNumeral.createFromDecimal(1);
    String expected = "I";
    String actual = romanNumeral.getRomanNumeralString();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_RomanNumeral_RepeatedSymbol() throws InvalidRomanNumeralException {
    RomanNumeral romanNumeral = new RomanNumeral("MMMMM");
    int expected = 5000;
    int actual = romanNumeral.getDecimalValue();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_DecimalNumber_RepeatedSymbols() {
    RomanNumeral romanNumeral = RomanNumeral.createFromDecimal(30);
    String expected = "XXX";
    String actual = romanNumeral.getRomanNumeralString();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_RomanNumeral_DescendingSymbols() throws InvalidRomanNumeralException {
    RomanNumeral romanNumeral = new RomanNumeral("MDCLXVI");
    int expected = 1666;
    int actual = romanNumeral.getDecimalValue();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_DecimalNumber_DescendingSymbols() {
    RomanNumeral romanNumeral = RomanNumeral.createFromDecimal(1666);
    String expected = "MDCLXVI";
    String actual = romanNumeral.getRomanNumeralString();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_RomanNumeral_OneSubtractionNotation() throws InvalidRomanNumeralException {
    RomanNumeral romanNumeral = new RomanNumeral("IV");
    int expected = 4;
    int actual = romanNumeral.getDecimalValue();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_DecimalNumber_OneSubtractionNotations() {
    RomanNumeral romanNumeral = RomanNumeral.createFromDecimal(400);
    String expected = "CD";
    String actual = romanNumeral.getRomanNumeralString();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_RomanNumeral_MultipleSubtractionNotations() throws InvalidRomanNumeralException {
    RomanNumeral romanNumeral = new RomanNumeral("MCDIV");
    int expected = 1404;
    int actual = romanNumeral.getDecimalValue();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_DecimalNumber_MultipleSubtractionNotations() {
    RomanNumeral romanNumeral = RomanNumeral.createFromDecimal(999);
    String expected = "CMXCIX";
    String actual = romanNumeral.getRomanNumeralString();
    Assert.assertEquals(expected, actual);
  }
}
