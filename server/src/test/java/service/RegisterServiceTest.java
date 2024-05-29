package service;

import dataaccess.exceptions.Error403AlreadyTaken;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

class RegisterServiceTest {


    @Test
    public void registerNewUser() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
        // Register the new user
        try {
            registerService.register();
            assert true;
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to register new user in RegisterServiceTest.registerNewUser()";
        }
    }

    @Test
    public void registerUserAgain() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
        // Register the new user
        try {
            registerService.register();
            assert true;
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to register new user in RegisterServiceTest.registerUserAgain()";
        }
        // Register the user again (403)
        try {
            registerService.register();
            assert false : "Error: Should not have reregistered user in RegisterServiceTest.registerUserAgain()";
        } catch (Exception exception) {
            exception.printStackTrace();
            assert exception instanceof Error403AlreadyTaken;
        }
    }

}