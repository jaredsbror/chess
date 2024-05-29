package service;

import dataaccess.exceptions.Error401Unauthorized;
import model.custom.LoginRequest;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

class LoginServiceTest {

    @Test
    public void logInAfterRegistering() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
        // Register the new user
        try {
            registerService.register();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to register new user in LoginServiceTest.logInAfterRegistering()";
        }
        // Log in as user
        try {
            loginService.login();
            assert true;
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to log in user in LoginServiceTest.logInAfterRegistering()";
        }
    }

    @Test
    public void logInWithoutRegistering() {
        // Create the database and clear it
        LoginService loginService = new LoginService(new LoginRequest(service.Test.username, service.Test.password));
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
        // Log in the user (403)
        try {
            loginService.login();
            assert false : "Error: Should not have reregistered user in RegisterServiceTest.logInWithoutRegistering()";
        } catch (Exception exception) {
            exception.printStackTrace();
            assert exception instanceof Error401Unauthorized;
        }
    }
}