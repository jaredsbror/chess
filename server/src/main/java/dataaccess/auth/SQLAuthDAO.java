package dataaccess.auth;


import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.original.AuthData;

import java.util.List;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {
    private String statement = null;

    // Constructor
    public SQLAuthDAO() throws DataAccessException {
        // Verify the connection to the SQL database
//        DatabaseManager.pingDatabase();
//        DatabaseManager.pingTables();
    }

    // Get AuthData object from authTable given an authToken
    public AuthData getAuthDataGivenAuthToken(String authToken) throws DataAccessException {
        // Get resultList data
        statement = "SELECT authToken, username FROM authTable WHERE authToken = '" + authToken + "'";
        List<Object> resultList = DatabaseManager.executeStatementAndMaybeReturnSingleRow(statement);
        // If the resultList is empty, return null
        if (resultList.isEmpty()) return null;
        // Parse resultList
        String username = (String) resultList.get(1);
        // Return the new AuthData object
        return new AuthData(authToken, username);
    }

    // Create a new authToken in authTable given a username
    public String createAuthToken(String username) throws DataAccessException {
        // If there already exists an authToken for the user, delete it.
        statement = "DELETE FROM authTable WHERE username = '" + username + "'";
        DatabaseManager.executeStatementInChess( statement );
        // Create the authToken and statement
        String authToken = UUID.randomUUID().toString();
        statement = "INSERT into authTable (authToken, username) VALUES ('" + authToken + "', '" + username + "')";
        // Execute the statement and return the authToken
        DatabaseManager.executeStatementInChess(statement);
        return authToken;
    }

    // Verify that an authToken exists in the authTable
    public Boolean verifyAuthToken(String authToken) throws DataAccessException {
        // Get resultList data
        statement = "SELECT authToken, username FROM authTable WHERE authToken = '" + authToken + "'";
        // Return whether the resultSet of the request is empty
        return (!DatabaseManager.executeStatementAndReturnEmpty(statement));
    }

    // Delete authData within authTable given authToken
    public void deleteAuthDataGivenAuthToken(String authToken) throws DataAccessException {
        statement = "DELETE FROM authTable WHERE authToken = '" + authToken + "'";
        DatabaseManager.executeStatementAndMaybeReturnSingleRow(statement);
    }

    // Clear the entire authTable
    public void clear() throws DataAccessException {
        statement = "DELETE FROM authTable";
        DatabaseManager.executeStatementAndMaybeReturnSingleRow(statement);
    }

    // Return whether authTable is empty
    public Boolean isEmpty() throws DataAccessException {
        // Get resultList data
        statement = "SELECT * FROM authTable";
        return DatabaseManager.executeStatementAndReturnEmpty(statement);
    }

//    public String toString() {
//
//    }
}
