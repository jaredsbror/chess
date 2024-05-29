package dataaccess.exceptions;

public class FailureResponse401 extends Exception {
    // Default constructor
    public FailureResponse401() {
        super("Error: unauthorized");
    }
}
