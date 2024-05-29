package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.exceptions.Error500Internal;
import model.custom.LogoutRequest;


public class LogoutService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private String authToken;

    public LogoutService(LogoutRequest logoutRequest) {
        authToken = logoutRequest.authToken();
    }

    public void logout() throws Error401Unauthorized, Error500Internal {
        // Verify that the authToken is valid
        if (!memoryAuthDAO.verifyAuthToken(authToken)) throw new Error401Unauthorized();
        // Delete the authToken from the database
        memoryAuthDAO.deleteAuthDataGivenAuthToken(authToken);
    }
}
