package model;

public record LoginResult(boolean success, String message, String username, String authToken) {
}
