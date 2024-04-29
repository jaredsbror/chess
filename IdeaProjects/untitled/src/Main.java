
/*
In Java, if your classes are within the same package, you don't need import statements
to access other classes within that package. Java allows classes within the same package
to access each other without explicit import statements.

However, if the classes are in different packages, you'll need import statements to use
classes from other packages. Import statements inform the compiler about the classes you
intend to use from other packages.

For example, consider two classes in different packages:

java
Copy code
// MyClass.java
package com.example;

import com.otherpackage.OtherClass;

public class MyClass {
    public static void main(String[] args) {
        OtherClass.printMessage("Hello from MyClass!");
    }
}
java
Copy code
// OtherClass.java
package com.otherpackage;

public class OtherClass {
    public static void printMessage(String message) {
        System.out.println(message);
    }
}
In this example, MyClass is in the com.example package, and OtherClass is in the com.otherpackage package.
To use OtherClass in MyClass, an import statement import com.otherpackage.OtherClass; is required.


If your classes are in the same source folder with no package specified, they are considered to be in the
 default package. In this case, you don't need to include package statements in your classes, and you can
 directly access other classes within the same source folder without any import statements.

Here's an example:

java
Copy code
// MyClass.java
public class MyClass {
    public static void main(String[] args) {
        OtherClass.printMessage("Hello from MyClass!");
    }
}
java
Copy code
// OtherClass.java
public class OtherClass {
    public static void printMessage(String message) {
        System.out.println(message);
    }
}
Both MyClass and OtherClass are in the default package (no package specified). Since they are in the same
source folder and in the default package, MyClass can directly access OtherClass without any import statement.
*/


// A java class
public class Main {

    // Private class variables
    private String name;

    // Constructor
    public Main(String name) {
        this.name = name;
    }

    // Get class name
    public String getName() {
        return name;
    }

    // Print class name
    public void printName() {
        System.out.println("Class Name -> " + getName());
    }

    // Print out a newline
    public void printNewLine() {
        System.out.println();
    }

    // Main program method
    public static void main(String[] args) {

        // Print out the class name of a temporary class object
        Main newMain = new Main("Joshua");
        newMain.printName();
        newMain.printNewLine(); // Newline

        // How to use constants
        // In Java, you don't instantiate classes like Constants that are meant to hold static constants or utility methods.
        // Instead, you access the constants directly using the class name followed by the constant name,
        // like Constants.INT or Constants.NAME.
        System.out.println("Constant int = " + Constants.INT);
        System.out.println("Constant String = " + Constants.NAME);
        newMain.printNewLine(); // Newline

        // Iterate over loop and print out numbers
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
        newMain.printNewLine(); // Newline

        // Variable declarations
        byte myByte = 1;            // Integer, 1 byte. 8 bits
        short myShort = 2;          // Integer, 2 bytes, 16 bits
        int myInt = 3;              // Integer, 4 bytes, 32 bits
        long myLong = 4;            // Integer, 8 bytes, 64 bits
        float myFloat = 3.1459f;    // Float, 4 bytes, 32 bits
        double myDouble = 3.1459;   // Float, 8 bytes, 64 bits
        char myChar = 'j';          // Text, 2 bytes, 16 bits
        String myString = "Hello";  // Text, Flexible size
        boolean myBoolean = true;   // True or false, 1 bit
        // NOTE: var can be used to tell java to automatically determine the appropriate variable type,
        // but it's probably not the best practice
        // Example: var inventor = new String("James Gosling");

        // Various print statements
        System.out.print("Hello print\n");      // NO Newline from printf
        System.out.println("Hello println");    // Newline from println
        System.out.printf("Number: %d, Pi: %.2f, Text: %s%n", myInt, myDouble, myString);  // Formatting, newline with %n
        System.out.println(String.format("Number: %d, Pi: %.2f, Text: %s", myInt, myDouble, myString));  // Formatting, newline with println
        newMain.printNewLine(); // Newline

        // String immutability
        // In Java, the String class represents immutable strings, meaning once a String object is created,
        // its value cannot be changed. However, you can create a new String object with a different value if needed.
        String str1 = new String("Hello");
        String str2 = "Hello";
        // This creates a new String object with the concatenated value. The str object is then reassigned to the new string.
        str1 = str1 + " World";
        System.out.println(str1);

        // String class functions
        System.out.println("Size: " + str1.length());               // Get the length of the string
        System.out.println("str(0): " + str1.charAt(0));            // Get the character at the given index
        System.out.println("Starts with H (true/false): " + str1.startsWith("H"));   // Does the string start with the given substring
        System.out.println("Index of H: " + str1.indexOf("H"));     // Get the index of the given substring
        System.out.println("str(0,5): " + str1.substring(0,5));     // Return the substring at the given index or range of indexes
        // str.format("Number: %d", myInt);                         // 	Create a string from a format template and parameters   // ???
        System.out.println("lowercase: " + str1.toLowerCase());     // Drop all the character case
        //str1.split()                                              // Split the string into an array based on the given substring
        System.out.println("H->J: " + str1.replace('H', 'J'));      // Replace the substring in the string
        newMain.printNewLine(); // Newline

        // Unlike the String class, the StringBuilder class allows you to modify the string it represents.
        // The idea with StringBuilder is that you will build up a string over time, as part of the logic of your program.
        // You can then convert the value of the StringBuilder to a String representation using the toString method
        var nameBuilder = new StringBuilder();
        nameBuilder.append("James");
        nameBuilder.append(" ");
        nameBuilder.append("Gosling");

        var nameBuild = nameBuilder.toString();
        System.out.println(nameBuild);
        newMain.printNewLine(); // Newline

    }
}