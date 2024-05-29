package dataaccess.exceptions;

public class FailureResponse400 extends Exception {
    // Default constructor
    public FailureResponse400() {
        super("Error: bad request");
    }
}
