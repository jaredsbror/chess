/*
Package and Import
When you write code, you normally provide a name for the package that contains the code.
Packages are declared with the package keyword. The package name should match the directory structure of your code.
All code in the same package has special access, called package scope to other code in the package.
Any code that you want available outside the package must be marked with public scope.
Packages allow you to encapsulate, or hide, data and functionality when it is not needed externally,
and expose it publicly when it is appropriate.

To use a package in your source code you must import it using the import keyword.
The following code demonstrates the use of the package keyword to create a package named Demo.
The import keyword provides access to the List class that is part of the standard java.util package.
 */
package Demo;

import java.util.List;

public class Hello {
    public static void main(String[] args) {
        var list = List.of(args);
        // If list is an object that implements the List interface,
        // the toString() method of that object will be called to obtain its string representation,
        // and that string will be inserted at the position of %s in the format string.
        // This allows you to print out the elements of the list in a formatted manner.
        System.out.printf("Args: %s!%n", list);
    }
}
