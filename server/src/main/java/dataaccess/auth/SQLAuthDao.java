package dataaccess.auth;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.original.AuthData;

public class SQLAuthDao implements AuthDAO {

    // Constructor
    public SQLAuthDao() throws DataAccessException {
        // Verify the connection to the SQL database
        DatabaseManager.pingDatabase();
        DatabaseManager.pingTables();
    }

    public AuthData getAuthDataGivenAuthToken(String authToken) throws DataAccessException {
        var statement = ("SELECT authToken, username FROM authTable");
        DatabaseManager.executeStatementInMySQL(statement);
    }

    public String createAuthToken(String username) {
    }

    // Verify that an authToken exists in the authTable
    public Boolean verifyAuthToken(String authToken) {
    }

    public AuthData deleteAuthDataGivenAuthToken(String authToken) {
    }

    public void clear() {
    }

    public Boolean isEmpty() {
    }

    public String toString() {

    }
}
