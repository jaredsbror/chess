package dataaccess.exceptions;


public class Error500Internal extends Exception {
    // Default constructor
    public Error500Internal() {
        super();
    }


    // Constructor that takes in a message
    public Error500Internal( String message ) {
        super( message );
    }
}
