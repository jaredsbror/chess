package service;

import dataaccess.exceptions.Error401Unauthorized;
import model.custom.CreateRequest;
import model.custom.ListRequest;
import model.custom.LoginRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class ListGamesServiceTest {

    @Test
    public void listGamesWithValidAuthToken() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        ListGamesService listGamesService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
        String authToken = null;
        // Register the new user
        try {
            registerService.register();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to register new user in ListGamesServiceTest.listGamesWithValidAuthToken";
        }
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to log in user in ListGamesServiceTest.listGamesWithValidAuthToken";
        }
        // Create three new games using previous authToken
        for (int i = 0; i < 3; i++) {
            try {
                createGameService = new CreateGameService(new CreateRequest(authToken, UUID.randomUUID().toString()));
                createGameService.createGame();
            } catch (Exception exception) {
                exception.printStackTrace();
                assert false : "Error: Failed to create game in ListGamesServiceTest.listGamesWithValidAuthToken";
            }
        }
        // List games using valid authToken
        try {
            listGamesService = new ListGamesService(new ListRequest(authToken));
            listGamesService.getGameList();
            assert true;
        } catch (Exception exception) {
            exception.printStackTrace();
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
        clearApplicationService.clearDatabase();
        String authToken = null;
        // Register the new user
        try {
            registerService.register();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to register new user in ListGamesServiceTest.listGamesWithInvalidAuthToken";
        }
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to log in user in ListGamesServiceTest.listGamesWithInvalidAuthToken";
        }
        // Create three new games using previous authToken
        for (int i = 0; i < 3; i++) {
            try {
                createGameService = new CreateGameService(new CreateRequest(authToken, UUID.randomUUID().toString()));
                createGameService.createGame();
            } catch (Exception exception) {
                exception.printStackTrace();
                assert false : "Error: Failed to create game in ListGamesServiceTest.listGamesWithInvalidAuthToken";
            }
        }
        // List games using invalid authToken
        try {
            listGamesService = new ListGamesService(new ListRequest(service.Test.authToken));
            listGamesService.getGameList();
            assert false : "Error: Should not list games in ListGamesServiceTest.listGamesWithInvalidAuthToken";
        } catch (Exception exception) {
            exception.printStackTrace();
            assert exception instanceof Error401Unauthorized;
        }
    }
}