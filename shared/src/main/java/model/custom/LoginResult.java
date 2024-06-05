package model.custom;


public record LoginResult( Boolean success, String message, String username, String authToken ) {
}
