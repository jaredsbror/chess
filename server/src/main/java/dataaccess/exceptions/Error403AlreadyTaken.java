package dataaccess.exceptions;

public class Error403AlreadyTaken extends Exception {
    // Default constructor
    public Error403AlreadyTaken() {
        super("Error: already taken");
    }
}
