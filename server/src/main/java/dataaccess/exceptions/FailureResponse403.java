package dataaccess.exceptions;

public class FailureResponse403 extends Exception {
    // Default constructor
    public FailureResponse403() {
        super("Error: already taken");
    }
}
