package service;

import dataaccess.exceptions.Error401Unauthorized;
import model.custom.CreateRequest;
import model.custom.LoginRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateGameServiceTest {
    @Test
    public void createGameWithValidAuthToken() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        String authToken = null;
        // Register the new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            assert false : "Error: Failed to log in user";
        }
        // Create a new game using previous authToken
        createGameService = new CreateGameService(new CreateRequest(authToken, service.Test.gameName));
        assertDoesNotThrow(createGameService::createGame, "Error: Failed to create game");
    }

    @Test
    public void createGameWithInvalidAuthToken() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        // Register the new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        // Log in as user and save authToken
        assertDoesNotThrow(loginService::login, "Error: Failed to log in user");
        // Create a new game using an invalid authToken
        createGameService = new CreateGameService(new CreateRequest(service.Test.authToken, service.Test.gameName));
        assertThrows(Error401Unauthorized.class, createGameService::createGame, "Error: Should not have created game");
    }
}