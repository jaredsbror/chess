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
        // Create database
        assertDoesNotThrow( () -> { clearApplicationService = new ClearApplicationService(); }, "Error: Failed to create clearApplicationService");
        // Clear the database
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        // Assert that the database is empty
        assertDoesNotThrow( () -> { assertTrue(clearApplicationService.isDatabaseEmpty(), "Error: Database is not empty"); });
    }

    @Test
    public void clearPopulatedDatabase() {
        // Create database
        assertDoesNotThrow( () -> { clearApplicationService = new ClearApplicationService(); }, "Error: Failed to create clearApplicationService");
        assertDoesNotThrow( () -> { registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password,service.Test.email)); }, "Error: Failed to create registerService");
        // Register new user
        assertDoesNotThrow(registerService::register, "Error: Failed to register new user");
        // Clear the database
        assertDoesNotThrow(clearApplicationService::clearDatabase, "Error: Failed to clear database");
        // Assert that the database is empty
        assertDoesNotThrow( () -> { assertTrue(clearApplicationService.isDatabaseEmpty(), "Error: Database is not empty"); });
    }

}