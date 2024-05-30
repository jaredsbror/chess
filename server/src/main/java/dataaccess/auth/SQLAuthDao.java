package dataaccess.auth;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.original.AuthData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class SQLAuthDao implements AuthDAO {

    private Connection connection;
    private String statement;

    public SQLAuthDao() throws DataAccessException {
        try {
            var conn = DriverManager.getConnection(DatabaseManager.getConnectionUrl(), DatabaseManager.getUSER(), DatabaseManager.getPASSWORD());
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public AuthData getAuthDataGivenAuthToken(String authToken) {
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
