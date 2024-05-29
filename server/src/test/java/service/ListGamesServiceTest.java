package service;

import dataaccess.exceptions.Error401Unauthorized;
import model.custom.CreateRequest;
import model.custom.ListRequest;
import model.custom.LoginRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ListGamesServiceTest {

    @Test
    public void listGamesWithValidAuthToken() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        ListGamesService listGamesService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        String authToken = null;
        // Register the new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            assert false : "Error: Failed to log in user in ListGamesServiceTest.listGamesWithValidAuthToken";
        }
        // Create three new games using previous authToken
        createGameService = new CreateGameService(new CreateRequest(authToken, UUID.randomUUID().toString()));
        for (int i = 0; i < 3; i++) {
            try {
                createGameService.createGame();
            } catch (Exception exception) {
                assert false : "Error: Failed to create game in ListGamesServiceTest.listGamesWithValidAuthToken";
            }
        }
        // List games using valid authToken
        listGamesService = new ListGamesService(new ListRequest(authToken));
        try {
            listGamesService.getGameList();
            assert true;
        } catch (Exception exception) {
            assert false : "Error: Failed to list games in ListGamesServiceTest.listGamesWithValidAuthToken";
        }
    }

    @Test
    public void listGamesWithInvalidAuthToken() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        ListGamesService listGamesService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        String authToken = null;
        // Register the new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            assert false : "Error: Failed to log in user in ListGamesServiceTest.listGamesWithInvalidAuthToken";
        }
        // Create three new games using previous authToken
        createGameService = new CreateGameService(new CreateRequest(authToken, UUID.randomUUID().toString()));
        for (int i = 0; i < 3; i++) {
            try {
                createGameService.createGame();
            } catch (Exception exception) {
                assert false : "Error: Failed to create game in ListGamesServiceTest.listGamesWithInvalidAuthToken";
            }
        }
        // List games using invalid authToken
        listGamesService = new ListGamesService(new ListRequest(service.Test.authToken));
        try {
            listGamesService.getGameList();
            assert false : "Error: Should not list games in ListGamesServiceTest.listGamesWithInvalidAuthToken";
        } catch (Exception exception) {
            assert exception instanceof Error401Unauthorized;
        }
    }
}