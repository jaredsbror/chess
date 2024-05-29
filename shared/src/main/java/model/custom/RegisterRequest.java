package model.custom;

public record RegisterRequest(String username,
                           String password,
                           String email) {
}
