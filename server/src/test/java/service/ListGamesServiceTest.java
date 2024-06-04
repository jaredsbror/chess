package service;

import dataaccess.exceptions.Error401Unauthorized;
import model.custom.CreateRequest;
import model.custom.ListRequest;
import model.custom.LoginRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ListGamesServiceTest {
    private RegisterService registerService;
    private LoginService loginService;
    private CreateGameService createGameService;
    private ListGamesService listGamesService;
    private ClearApplicationService clearApplicationService;
    private String authToken;

    @Test
    public void setupForListGame() {
        assertDoesNotThrow( () -> {
            // Create the database and clear it
            clearApplicationService = new ClearApplicationService();
            clearApplicationService.clearDatabase();
            authToken = null;
            // Register the new user
            registerService = new RegisterService(new RegisterRequest(service.Test.USERNAME, service.Test.PASSWORD, service.Test.EMAIL ));
            registerService.register();
            // Log in as user and save authToken
            loginService = new LoginService(new LoginRequest(service.Test.USERNAME, service.Test.PASSWORD ));
            authToken = loginService.login();
            // Create three new games using previous authToken
            createGameService = new CreateGameService(new CreateRequest(authToken, UUID.randomUUID().toString()));
            for (int i = 0; i < 3; i++) {
                createGameService.createGame();
            }
        }, "Error: Failed to setup for list games" );
    }

    @Test
    public void listGamesWithValidAuthToken() {
        setupForListGame();
        assertDoesNotThrow( () -> {
            // List games using valid authToken
            listGamesService = new ListGamesService(new ListRequest(authToken));
            listGamesService.getGameList();
        }, "Error: Failed to list games" );
    }

    @Test
    public void listGamesWithInvalidAuthToken() {
        setupForListGame();
        // List games using invalid authToken
        assertDoesNotThrow( () -> {
            listGamesService = new ListGamesService(new ListRequest(service.Test.AUTH_TOKEN ));
        } );
        assertThrows( Error401Unauthorized.class, listGamesService::getGameList, "Error: Should not list games");
    }
}