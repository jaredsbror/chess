package service;

import model.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearApplicationServiceTest {

    @Test
    public void clearEmptyDatabase() {
        // Create database
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        // Clear the database
        clearApplicationService.clearDatabase();
        // Assert that the database is empty
        assertTrue(clearApplicationService.isDatabaseEmpty(), "Error: Failed to clear database in ClearApplicationServiceTest.clearEmptyDatabase()");
    }

    @Test
    public void clearPopulatedDatabase() {
        // Create database
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        RegisterService registerService = new RegisterService(new RegisterRequest(service.Test.username, service.Test.password,service.Test.email));
        // Register new user
        try {
            registerService.register();
        } catch (Exception exception) {
            exception.printStackTrace();
            assert false : "Error: Failed to register new user in ClearApplicationServiceTest.clearPopulatedDatabase()";
        }
        // Clear the database
        clearApplicationService.clearDatabase();
        // Assert that the database is empty
        assertTrue(clearApplicationService.isDatabaseEmpty(), "Error: Failed to clear database in ClearApplicationServiceTest.clearPopulatedDatabase()");
    }

}