package service;

import dataaccess.exceptions.Error401Unauthorized;
import model.custom.LoginRequest;
import model.custom.LogoutRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogoutServiceTest {

    @Test
    public void logoutExistingUser() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        LogoutService logoutService;
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        String authToken = null;
        // Register the new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            assert false : "Error: Failed to log in user";
        }
        // Logout user using previous authToken
        logoutService = new LogoutService(new LogoutRequest(authToken));
        assertDoesNotThrow(logoutService::logout, "Error: Failed to logout user");
    }


    @Test
    public void logoutNonexistentUser() {
        /// Create the database and clear it
        LogoutService logoutService = new LogoutService(new LogoutRequest(service.Test.authToken));
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        // Logout user
        assertThrows(Error401Unauthorized.class, logoutService::logout, "Error: Should not logout user");
    }
}