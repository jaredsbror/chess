package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.exceptions.FailureResponse401;
import dataaccess.exceptions.FailureResponse500;
import model.custom.LogoutRequest;


public class LogoutService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private String authToken;

    public LogoutService(LogoutRequest logoutRequest) {
        authToken = logoutRequest.authToken();
    }

    public void logout() throws FailureResponse401, FailureResponse500 {
        // Verify that the authToken is valid
        if (!memoryAuthDAO.verifyAuthToken(authToken)) throw new FailureResponse401();
        // Delete the authToken from the database
        memoryAuthDAO.deleteAuthDataGivenAuthToken(authToken);
    }
}
