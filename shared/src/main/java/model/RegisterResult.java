package model;

public record RegisterResult(Boolean success, String message, String username, String authToken) {
}
