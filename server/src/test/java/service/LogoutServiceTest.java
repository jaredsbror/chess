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
        clearApplicationService.clearDatabase();
        String authToken = null;
        // Register the new user
        assertDoesNotThrow(() -> {
            registerService.register();
        }, "Error: Failed to register new user in LogoutServiceTest.logoutExistingUser()");
        // Log in as user and save authToken
        try {
            authToken = loginService.login();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to log in user in LogoutServiceTest.logoutExistingUser()";
        }
        // Logout user using previous authToken
        try {
            logoutService = new LogoutService(new LogoutRequest(authToken));
            logoutService.logout();
            assert true;
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to logout user in LogoutServiceTest.logoutExistingUser()";
        }
    }


    @Test
    public void logoutNonexistentUser() {
        /// Create the database and clear it
        LogoutService logoutService = new LogoutService(new LogoutRequest(service.Test.authToken));
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
        // Logout user
        assertThrows(Error401Unauthorized.class, logoutService::logout, "Error: Should not logout user in LogoutServiceTest.logoutNonexistentUser()");
    }
}