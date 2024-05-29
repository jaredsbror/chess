package dataaccess.exceptions;

public class Error400BadRequest extends Exception {
    // Default constructor
    public Error400BadRequest() {
        super("Error: bad request");
    }
}
