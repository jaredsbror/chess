package dataaccess.user;


import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.UserDAO;
import model.original.UserData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class SQLUserDAO implements UserDAO {private Connection connection;
    private String statement = null;

    // Constructor
    public SQLUserDAO() throws DataAccessException {
        // Verify the connection to the SQL database
        DatabaseManager.pingDatabase();
        DatabaseManager.pingTables();
    }

    public UserData getUser(String username) throws DataAccessException {
        // Get resultList data
        statement = "SELECT username, password, email FROM userTable WHERE username = '" + username + "'";
        List<Object> resultList = DatabaseManager.executeStatementAndMaybeReturnSingleRow(statement);
        // If the resultList is empty, return null
        if (resultList.isEmpty()) return null;
        if (resultList.size() < 3) {
            throw new DataAccessException( "Error: Did not receive resultList of size 3 or greater in SQLUserDAO.getUser()" );
        }
        // Parse resultList
        String password = (String) resultList.get(1);
        String email = (String) resultList.get(2);
        // Return the new UserData object
        return new UserData( username, password, email );
    };

    public void insertUser(String username, String password, String email) throws DataAccessException {
        // Create  statement
        statement = "INSERT INTO userTable (username, password, email) VALUES ('" + username + "', '" + password + "', '" + email + "')";
        // Execute the statement and return the authToken
        DatabaseManager.executeStatementAndMaybeReturnSingleRow(statement);
    };

    public void clear() throws DataAccessException {
        statement = "DELETE FROM userTable";
        DatabaseManager.executeStatementAndMaybeReturnSingleRow(statement);
    };

    public boolean isEmpty() throws DataAccessException {
        // Get resultList data
        statement = "SELECT * FROM userTable";
        try {
            return DatabaseManager.executeStatementAndReturnEmpty(statement);
        } catch ( SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    };
}
