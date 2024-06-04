package service;


import dataaccess.exceptions.Error401Unauthorized;
import model.custom.CreateRequest;
import model.custom.LoginRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CreateGameServiceTest {
    RegisterService registerService;
    LoginService loginService;
    CreateGameService createGameService;
    ClearApplicationService clearApplicationService;
    String authToken;

    @Test
    public void setupForCreateGame() {
        assertDoesNotThrow( () -> {
            // Create the database and clear it
            clearApplicationService = new ClearApplicationService();
            clearApplicationService.clearDatabase();
            // Register the new user
            registerService = new RegisterService( new RegisterRequest( service.Test.USERNAME, service.Test.PASSWORD, service.Test.EMAIL ) );
            registerService.register();
            // Log in as user and save authToken
            loginService = new LoginService( new LoginRequest( service.Test.USERNAME, service.Test.PASSWORD ) );
            authToken = loginService.login();
        }, "Error: Failed to setup for game creation" );
    }

    @Test
    public void createGameWithValidAuthToken() {
        setupForCreateGame();
        // Create a new game using previous authToken
        assertDoesNotThrow( () -> {
            createGameService = new CreateGameService( new CreateRequest( authToken, service.Test.GAME_NAME ) );
            createGameService.createGame();
        }, "Error: Failed to create game" );
    }


    @Test
    public void createGameWithInvalidAuthToken() {
        setupForCreateGame();
        // Create a new game using an invalid authToken
        assertDoesNotThrow( () -> {
            createGameService = new CreateGameService( new CreateRequest( service.Test.AUTH_TOKEN, service.Test.GAME_NAME ) );
        } );
        assertThrows( Error401Unauthorized.class, createGameService::createGame, "Error: Should not have created game" );
    }
}