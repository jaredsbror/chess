package Demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// A java class
public class ScannerExample {
    // main
    public static void main(String[] args) throws FileNotFoundException {
        // If there is only one argument...
        if (args.length == 1) {
            var file = new File(args[0]);
            // If the file is valid...
            if (file.exists()) {
                var scanner = new Scanner(file);
                // Print out the file contents
                while (scanner.hasNext()) {
                    var text = scanner.next();
                    System.out.println(text);
                }
            }
        }
    }
}
