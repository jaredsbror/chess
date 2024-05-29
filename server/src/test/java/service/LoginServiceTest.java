package service;

import dataaccess.exceptions.Error401Unauthorized;
import model.custom.LoginRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginServiceTest {

    @Test
    public void logInAfterRegistering() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        // Register the new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        // Log in as user
        assertDoesNotThrow(loginService::login, "Error: Failed to log in user in LoginServiceTest.logInAfterRegistering()");
    }

    @Test
    public void logInWithoutRegistering() {
        // Create the database and clear it
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        // Log in the user (403)
        assertThrows(Error401Unauthorized.class, loginService::login, "Error: Should not have reregistered user in RegisterServiceTest.logInWithoutRegistering()");
    }
}