
// A java class
public class Main {
    // Main program method
    public static void main(String[] args) {
        // Variable declarations
        int num1 = 10;
        double num2 = 3.1459;
        String text = "Hello";
        // Various print statements
        System.out.print("Hello print\n");      // Does NOT add a newline
        System.out.println("Hello println");    // Adds a newline
        System.out.printf("Number: %d, Pi: %.2f, Text: %s", num1, num2, text);  // Formatting, no newline
        System.out.println(String.format("Number: %d, Pi: %.2f, Text: %s", num1, num2, text));  // Formatting, no newline, REDUNDANT

        // Iterate over 
        for (int i = 1; i <= 5; i++) {

            System.out.println("i = " + i);
        }
    }
}