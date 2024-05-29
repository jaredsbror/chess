package service;

import model.custom.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClearApplicationServiceTest {

    @Test
    public void clearEmptyDatabase() {
        // Create database
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        // Clear the database
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        // Assert that the database is empty
        assertTrue(clearApplicationService.isDatabaseEmpty(), "Error: Failed to clear database");
    }

    @Test
    public void clearPopulatedDatabase() {
        // Create database
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password,service.Test.email));
        // Register new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        // Clear the database
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        // Assert that the database is empty
        assertTrue(clearApplicationService.isDatabaseEmpty(), "Error: Failed to clear database");
    }

}