package service;

import dataaccess.exceptions.Error401Unauthorized;
import model.custom.LoginRequest;
import model.custom.LogoutRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogoutServiceTest {
    private RegisterService registerService;
    private LoginService loginService;
    private LogoutService logoutService;
    private ClearApplicationService clearApplicationService;
    private String authToken;

    @Test
    public void logoutExistingUser() {
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
            // Logout user using previous authToken
            logoutService = new LogoutService(new LogoutRequest(authToken));
        }, "Error: Failed to setup for logout");
        assertDoesNotThrow(logoutService::logout, "Error: Failed to logout user");
    }


    @Test
    public void logoutNonexistentUser() {
        assertDoesNotThrow( () -> {
                    // Create the database and clear it
                    clearApplicationService = new ClearApplicationService();
                    clearApplicationService.clearDatabase();
                    logoutService = new LogoutService( new LogoutRequest( service.Test.AUTH_TOKEN ) );
        }, "Error: Failed to setup for logout");
        // Logout user
        assertThrows(Error401Unauthorized.class, logoutService::logout, "Error: Should not logout user");
    }
}