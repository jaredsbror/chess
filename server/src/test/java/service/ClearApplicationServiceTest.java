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
        });
        // Clear the database
        assertDoesNotThrow( clearApplicationService::clearDatabase, "Error: Failed to clear database" );
        // Assert that the database is empty
        assertDoesNotThrow( () -> {
            assertTrue( clearApplicationService.isDatabaseEmpty(), "Error: Database is not empty" );
        } );
    }


    @Test
    public void clearPopulatedDatabase() {
        assertDoesNotThrow( () -> {
            // Create database
            clearApplicationService = new ClearApplicationService();
            registerService = new RegisterService( new RegisterRequest( service.Test.username, service.Test.password, service.Test.email ) );
            // Register new user
            registerService.register();
        } );
        // Clear the database
        assertDoesNotThrow( clearApplicationService::clearDatabase, "Error: Failed to clear database" );
        // Assert that the database is empty
        assertDoesNotThrow( () -> {
            assertTrue( clearApplicationService.isDatabaseEmpty(), "Error: Database is not empty" );
        } );
    }

}