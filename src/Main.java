import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This program allows the user to convert a Roman numeral to a decimal number and vice versa.
 */
public class Main {

    public static void main(String[] args) {
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.println("Would you like to:");
            System.out.println(" (1) convert from Roman numerals to decimal or");
            System.out.println(" (2) convert from decimal to Roman numerals?");
            System.out.print("(1/2): ");

            Scanner scanner = new Scanner(System.in);
            try {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    isValidInput = true;
                    getRomanNumeral();
                } else if (choice == 2) {
                    isValidInput = true;
                    getDecimalNumber();
                } else {
                    printInvalidInput("Input must be 1 or 2!");
                }
            } catch (InputMismatchException e) {
                printInvalidInput("Input must be a number!");
            }
        }
    }

    /**
     * Notify the user that their input was invalid.
     *
     * @param message the error message.
     */
    private static void printInvalidInput(String message) {
        System.out.println("Invalid input: " + message);
    }

    /**
     * Gets a valid Roman numeral from the command line and converts it to a decimal number.
     */
    private static void getRomanNumeral() {
        boolean isValidNumeral = false;
        while (!isValidNumeral) {
            System.out.print("Enter a Roman numeral: ");
            Scanner scanner = new Scanner(System.in);
            String numeralString = scanner.next();
            try {
                RomanNumeral romanNumeral = new RomanNumeral(numeralString);
                System.out.println("The decimal form of " + romanNumeral.getRomanNumeralString() + " is: " +
                        romanNumeral.getDecimalValue());
                isValidNumeral = true;
            } catch (InvalidRomanNumeralException e) {
                printInvalidInput(e.getMessage());
            }
        }
    }

    /**
     * Gets a valid decimal number from the command line and converts it to a Roman numeral.
     */
    private static void getDecimalNumber() {
        boolean isValidDecimal = false;
        while (!isValidDecimal) {
            System.out.print("Enter a decimal number: ");
            try {
                Scanner scanner = new Scanner(System.in);
                int decimalValue = scanner.nextInt();
                isValidDecimal = true;

                RomanNumeral romanNumeral = RomanNumeral.createFromDecimal(decimalValue);
                System.out.println("The Roman numeral form of " + romanNumeral.getDecimalValue() + " is: " +
                        romanNumeral.getRomanNumeralString());
            } catch (InputMismatchException e) {
                printInvalidInput("Input must be an integer!");
            }
        }
    }
}
