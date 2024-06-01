package service;

import dataaccess.exceptions.Error401Unauthorized;
import model.custom.LoginRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginServiceTest {
    private RegisterService registerService;
    private LoginService loginService;
    private ClearApplicationService clearApplicationService;

    @Test
    public void logInAfterRegistering() {
        assertDoesNotThrow( () -> {
            // Create the database and clear it
            clearApplicationService = new ClearApplicationService();
            clearApplicationService.clearDatabase();
            // Register the new user
            registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
            registerService.register();
            loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        }, "Error: Failed to setup for login" );
        // Log in as user
        assertDoesNotThrow(loginService::login, "Error: Failed to log in user");
    }

    @Test
    public void logInWithoutRegistering() {
        assertDoesNotThrow( () -> {
            // Create the database and clear it
            clearApplicationService = new ClearApplicationService();
            clearApplicationService.clearDatabase();
            loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        }, "Error: Failed to setup for login" );
        // Log in the user (403)
        assertThrows(Error401Unauthorized.class, loginService::login, "Error: Should not have reregistered user");
    }
}