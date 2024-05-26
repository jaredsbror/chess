package dataAccess.exceptions;

public class FailureResponse500 extends Exception {
    // Default constructor
    public FailureResponse500() {
        super();
    }

    // Constructor that takes in a message
    public FailureResponse500(String message) {
        super(message);
    }
}
