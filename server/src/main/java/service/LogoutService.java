package service;


import dataaccess.DataAccessException;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.exceptions.Error401Unauthorized;
import model.custom.LogoutRequest;


public class LogoutService {
    private final SQLAuthDAO sqlAuthDao;
    private final String authToken;


    public LogoutService( LogoutRequest logoutRequest ) throws DataAccessException {
        authToken = logoutRequest.authToken();
        sqlAuthDao = new SQLAuthDAO();
    }


    public void logout() throws Error401Unauthorized, DataAccessException {
        // Verify that the authToken is valid
        if ( !sqlAuthDao.verifyAuthToken( authToken ) ) throw new Error401Unauthorized();
        // Delete the authToken from the database
        sqlAuthDao.deleteAuthDataGivenAuthToken( authToken );
    }
}
