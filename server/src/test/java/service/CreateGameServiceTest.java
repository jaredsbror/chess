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
    public void createGameWithValidAuthToken() {

        assertDoesNotThrow( () -> {
            // Create the database and clear it
            registerService = new RegisterService( new RegisterRequest( service.Test.username, service.Test.password, service.Test.email ) );
            loginService = new LoginService( new LoginRequest( service.Test.username, service.Test.password ) );
            clearApplicationService = new ClearApplicationService();
            clearApplicationService.clearDatabase();
            // Register the new user
            registerService.register();
            // Log in as user and save authToken
            authToken = loginService.login();
        } );
        // Create a new game using previous authToken
        assertDoesNotThrow( () -> {
            createGameService = new CreateGameService( new CreateRequest( authToken, service.Test.gameName ) );
            createGameService.createGame();
        }, "Error: Failed to create game" );
    }


    @Test
    public void createGameWithInvalidAuthToken() {
        assertDoesNotThrow( () -> {
            // Create the database and clear it
            registerService = new RegisterService( new RegisterRequest( service.Test.username, service.Test.password, service.Test.email ) );
            loginService = new LoginService( new LoginRequest( service.Test.username, service.Test.password ) );
            clearApplicationService = new ClearApplicationService();
            clearApplicationService.clearDatabase();
            // Register the new user
            registerService.register();
            // Log in as user and save authToken
            authToken = loginService.login();
        } );
        // Create a new game using an invalid authToken
        assertDoesNotThrow( () -> {
            createGameService = new CreateGameService( new CreateRequest( service.Test.authToken, service.Test.gameName ) );
        } );
        assertThrows( Error401Unauthorized.class, createGameService::createGame, "Error: Should not have created game" );
    }
}