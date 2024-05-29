package service;

import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error401Unauthorized;
import model.custom.CreateRequest;
import model.custom.JoinRequest;
import model.custom.LoginRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JoinGameServiceTest {

    @Test
    public void joinGameWithValidGameID() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        JoinGameService joinGameService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        String authToken = null;
        int gameID = 0;
        // Register the new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            assert false : "Error: Failed to log in user";
        }
        // Create a new game using previous authToken
        try {
            createGameService = new CreateGameService(new CreateRequest(authToken, service.Test.gameName));
            gameID = createGameService.createGame();
        } catch (Exception exception) {
            assert false : "Error: Failed to create game";
        }
        // Join a game using previous authToken and gameID
        joinGameService = new JoinGameService(new JoinRequest(authToken, service.Test.white, gameID));
        assertDoesNotThrow(joinGameService::joinGame, "Error: Failed to join game");
    }

    @Test void joinGameWithInvalidGameID() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        JoinGameService joinGameService;
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
        // Join a game using valid authToken and invalid gameID
        joinGameService = new JoinGameService(new JoinRequest(authToken, service.Test.white, service.Test.gameID));
        assertThrows(Error400BadRequest.class, joinGameService::joinGame,"Error: Should not have joined game");
    }

    @Test void joinGameWithInvalidAuthToken() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        JoinGameService joinGameService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        String authToken = null;
        int gameID = 0;
        // Register the new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            assert false : "Error: Failed to log in user";
        }
        // Create a new game using previous authToken
        try {
            createGameService = new CreateGameService(new CreateRequest(authToken, service.Test.gameName));
            gameID = createGameService.createGame();
        } catch (Exception exception) {
            assert false : "Error: Failed to create game";
        }
        // Join a game using valid authToken and invalid gameID
        joinGameService = new JoinGameService(new JoinRequest(service.Test.authToken, service.Test.white, gameID));
        assertThrows(Error401Unauthorized.class, joinGameService::joinGame, "Error: Should not have joined game");
    }
}