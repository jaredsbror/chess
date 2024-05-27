package model;

public record LoginResult(Boolean success, String message, String username, String authToken) {
}
