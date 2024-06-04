package service;


import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ClearApplicationServiceTest {
    private ClearApplicationService clearApplicationService;
    private RegisterService registerService;

    @Test
    public void clearEmptyDatabase() {
        assertDoesNotThrow( () -> {
            // Create database
            clearApplicationService = new ClearApplicationService();
        }, "Error: Failed to setup to clear the database");
        assertDoesNotThrow( () -> {
            // Clear the database
            clearApplicationService.clearDatabase();
            // Assert that the database is empty
            assertTrue( clearApplicationService.isDatabaseEmpty(), "Error: Database is not empty" );
        }, "Error: Failed to clear database" );
    }


    @Test
    public void clearPopulatedDatabase() {
        assertDoesNotThrow( () -> {
            // Create database
            clearApplicationService = new ClearApplicationService();
            clearApplicationService.clearDatabase();
            // Register new user
            registerService = new RegisterService( new RegisterRequest( service.Test.USERNAME, service.Test.PASSWORD, service.Test.EMAIL ) );
            registerService.register();
        }, "Error: Failed to setup to clear the database");
        assertDoesNotThrow( () -> {
            // Clear the database
            clearApplicationService.clearDatabase();
            // Assert that the database is empty
            assertTrue( clearApplicationService.isDatabaseEmpty(), "Error: Database is not empty" );
        }, "Error: Failed to clear database");
    }

}