package service;

import dataaccess.exceptions.FailureResponse401;
import model.CreateRequest;
import model.LoginRequest;
import model.RegisterRequest;
import org.junit.jupiter.api.Test;

class CreateGameServiceTest {
    @Test
    public void createGameWithValidAuthToken() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
        String authToken = null;
        // Register the new user
        try {
            registerService.register();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to register new user in CreateGameServiceTest.createGameWithValidAuthToken()";
        }
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to log in user in CreateGameServiceTest.createGameWithValidAuthToken()";
        }
        // Create a new game using previous authToken
        try {
            createGameService = new CreateGameService(new CreateRequest(authToken, service.Test.gameName));
            createGameService.createGame();
            assert true;
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to create game in CreateGameServiceTest.createGameWithValidAuthToken()";
        }
    }

    @Test
    public void createGameWithInvalidAuthToken() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        CreateGameService createGameService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
        String authToken = null;
        // Register the new user
        try {
            registerService.register();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to register new user in CreateGameServiceTest.createGameWithInvalidAuthToken()";
        }
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to log in user in CreateGameServiceTest.createGameWithInvalidAuthToken()";
        }
        // Create a new game using an invalid authToken
        try {
            createGameService = new CreateGameService(new CreateRequest(service.Test.authToken, service.Test.gameName));
            createGameService.createGame();
            assert false : "Error: Should not have created game in CreateGameServiceTest.createGameWithInvalidAuthToken()";
        } catch (Exception exception) {
            exception.printStackTrace();
            assert exception instanceof FailureResponse401;
        }
    }
}