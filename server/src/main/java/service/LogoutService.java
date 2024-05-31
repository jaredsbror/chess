package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.auth.SQLAuthDao;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.exceptions.Error500Internal;
import model.custom.LogoutRequest;


public class LogoutService {
    private SQLAuthDao sqlAuthDao = new SQLAuthDao();
    private String authToken;

    public LogoutService(LogoutRequest logoutRequest) {
        authToken = logoutRequest.authToken();
    }

    public void logout() throws Error401Unauthorized, Error500Internal {
        // Verify that the authToken is valid
        if (!sqlAuthDao.verifyAuthToken(authToken)) throw new Error401Unauthorized();
        // Delete the authToken from the database
        sqlAuthDao.deleteAuthDataGivenAuthToken(authToken);
    }
}
