package service;

import dataaccess.exceptions.Error403AlreadyTaken;
import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RegisterServiceTest {
    private RegisterService registerService;
    private ClearApplicationService clearApplicationService;

    @Test
    public void setupForRegister() {
        assertDoesNotThrow( () -> {
            // Create the database and clear it
            clearApplicationService = new ClearApplicationService();
            clearApplicationService.clearDatabase();
            registerService = new RegisterService(new RegisterRequest(service.Test.USERNAME, service.Test.PASSWORD, service.Test.EMAIL ));
        } , "Error: Failed to setup to register new user");
    }

    @Test
    public void registerNewUser() {
        setupForRegister();
        // Register the new user
        assertDoesNotThrow(registerService::register,"Error: Failed to register new user");
        assertDoesNotThrow( () -> {
            assertFalse( clearApplicationService.isDatabaseEmpty(), "Error: Database is empty after registering new user." );
        } );
    }

    @Test
    public void registerUserAgain() {
        setupForRegister();
        // Register the new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        assertDoesNotThrow( () -> {
            assertFalse( clearApplicationService.isDatabaseEmpty(), "Error: Database is empty after registering new user." );
        } );
        // Register the user again (403)
        assertThrows(Error403AlreadyTaken.class, registerService::register, "Error: Should not have reregistered user");
    }

}