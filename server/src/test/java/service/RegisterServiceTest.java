package service;

import dataaccess.exceptions.Error403AlreadyTaken;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegisterServiceTest {


    @Test
    public void registerNewUser() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        // Register the new user
        assertDoesNotThrow(registerService::register,"Error: Failed to register new user");
    }

    @Test
    public void registerUserAgain() {
        // Create the database and clear it
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password, service.Test.email));
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        // Register the new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        // Register the user again (403)
        assertThrows(Error403AlreadyTaken.class, registerService::register, "Error: Should not have reregistered user");
    }

}