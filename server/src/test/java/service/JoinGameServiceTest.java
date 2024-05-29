package service;

import dataaccess.exceptions.FailureResponse400;
import dataaccess.exceptions.FailureResponse401;
import model.custom.CreateRequest;
import model.custom.JoinRequest;
import model.custom.LoginRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

class JoinGameServiceTest {

    @Test
    public void joinGameWithValidGameID() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        JoinGameService joinGameService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
        String authToken = null;
        int gameID = 0;
        // Register the new user
        try {
            registerService.register();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to register new user in JoinGameServiceTest.joinGameWithValidID()";
        }
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to log in user in JoinGameServiceTest.joinGameWithValidID()";
        }
        // Create a new game using previous authToken
        try {
            createGameService = new CreateGameService(new CreateRequest(authToken, service.Test.gameName));
            gameID = createGameService.createGame();
            assert true;
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to create game in JoinGameServiceTest.joinGameWithValidID()";
        }
        // Join a game using previous authToken and gameID
        try {
            joinGameService = new JoinGameService(new JoinRequest(authToken, service.Test.white, gameID));
            joinGameService.joinGame();
            assert true;
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to join game in JoinGameServiceTest.joinGameWithValidID()";
        }
    }

    @Test void joinGameWithInvalidGameID() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        JoinGameService joinGameService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
        String authToken = null;
        int gameID = 0;
        // Register the new user
        try {
            registerService.register();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to register new user in JoinGameServiceTest.joinGameWithInvalidID()";
        }
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to log in user in JoinGameServiceTest.joinGameWithInvalidID()";
        }
        // Create a new game using previous authToken
        try {
            createGameService = new CreateGameService(new CreateRequest(authToken, service.Test.gameName));
            gameID = createGameService.createGame();
            assert true;
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to create game in JoinGameServiceTest.joinGameWithInvalidID()";
        }
        // Join a game using valid authToken and invalid gameID
        try {
            joinGameService = new JoinGameService(new JoinRequest(authToken, service.Test.white, service.Test.gameID));
            joinGameService.joinGame();
            assert false : "Error: Should not have joined game in JoinGameServiceTest.joinGameWithInvalidID()";
        } catch (Exception exception) {
            exception.printStackTrace();
            assert exception instanceof FailureResponse400;
        }
    }

    @Test void joinGameWithInvalidAuthToken() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        JoinGameService joinGameService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
        String authToken = null;
        int gameID = 0;
        // Register the new user
        try {
            registerService.register();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to register new user in JoinGameServiceTest.joinGameWithInvalidAuthToken()";
        }
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to log in user in JoinGameServiceTest.joinGameWithInvalidAuthToken()";
        }
        // Create a new game using previous authToken
        try {
            createGameService = new CreateGameService(new CreateRequest(authToken, service.Test.gameName));
            gameID = createGameService.createGame();
            assert true;
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to create game in JoinGameServiceTest.joinGameWithInvalidAuthToken()";
        }
        // Join a game using valid authToken and invalid gameID
        try {
            joinGameService = new JoinGameService(new JoinRequest(service.Test.authToken, service.Test.white, gameID));
            joinGameService.joinGame();
            assert false : "Error: Should not have joined game in JoinGameServiceTest.joinGameWithInvalidAuthToken()";
        } catch (Exception exception) {
            exception.printStackTrace();
            assert exception instanceof FailureResponse401;
        }
    }
}