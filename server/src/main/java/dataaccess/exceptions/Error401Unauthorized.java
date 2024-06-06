package dataaccess.exceptions;


public class Error401Unauthorized extends Exception {
    // Default constructor
    public Error401Unauthorized() {
        super( "Error: unauthorized" );
    }
}
