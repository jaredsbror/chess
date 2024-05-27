package service;

import dataAccess.auth.MemoryAuthDAO;
import dataAccess.exceptions.FailureResponse401;
import dataAccess.exceptions.FailureResponse500;
import dataAccess.user.MemoryUserDAO;
import model.LoginRequest;
import model.LogoutRequest;
import model.UserData;

import java.util.Objects;

public class LogoutService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private final String authToken;

    public LogoutService(LogoutRequest logoutRequest) {
        authToken = logoutRequest.authToken();
    }

    public void logout() throws FailureResponse401, FailureResponse500 {
        // Verify that the authToken is valid
        if (!memoryAuthDAO.verifyUser(authToken)) throw new FailureResponse401();
        // Delete the authToken from the database
        memoryAuthDAO.deleteAuthDataGivenAuthToken(authToken);
    }
}
