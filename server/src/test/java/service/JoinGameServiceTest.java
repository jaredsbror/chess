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
    RegisterService registerService;
    LoginService loginService;
    CreateGameService createGameService;
    JoinGameService joinGameService;
    ClearApplicationService clearApplicationService;
    String authToken;
    int gameID;


    @Test
    public void setupForJoinGame() {
        assertDoesNotThrow( () -> {
            // Create the database and clear it
            clearApplicationService = new ClearApplicationService();
            clearApplicationService.clearDatabase();
            authToken = null;
            gameID = 0;
            // Register the new user
            registerService = new RegisterService( new RegisterRequest( service.Test.USERNAME, service.Test.PASSWORD, service.Test.EMAIL ) );
            registerService.register();
            // Log in as user and save authToken
            loginService = new LoginService( new LoginRequest( service.Test.USERNAME, service.Test.PASSWORD ) );
            authToken = loginService.login();
            // Create a new game using previous authToken
            createGameService = new CreateGameService( new CreateRequest( authToken, service.Test.GAME_NAME ) );
            gameID = createGameService.createGame();
        }, "Error: Failed to setup for join game" );
    }


    @Test
    public void joinGameWithValidGameID() {
        setupForJoinGame();
        // Join a game using previous authToken and gameID
        assertDoesNotThrow( () -> {
            joinGameService = new JoinGameService( new JoinRequest( authToken, service.Test.WHITE, gameID ) );
        } );
        assertDoesNotThrow( joinGameService::joinGame, "Error: Failed to join game" );
    }


    @Test
    void joinGameWithInvalidGameID() {
        setupForJoinGame();
        // Join a game using valid authToken and invalid gameID
        assertDoesNotThrow( () -> {
            joinGameService = new JoinGameService( new JoinRequest( authToken, service.Test.WHITE, service.Test.GAME_ID ) );
        } );
        assertThrows( Error400BadRequest.class, joinGameService::joinGame, "Error: Should not have joined game" );
    }


    @Test
    void joinGameWithInvalidAuthToken() {
        setupForJoinGame();
        // Join a game using valid authToken and invalid gameID
        assertDoesNotThrow( () -> {
            joinGameService = new JoinGameService( new JoinRequest( service.Test.AUTH_TOKEN, service.Test.WHITE, gameID ) );
        } );
        assertThrows( Error401Unauthorized.class, joinGameService::joinGame, "Error: Should not have joined game" );
    }
}