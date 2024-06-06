package dataaccess.auth;


import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.original.AuthData;

import java.util.List;
import java.util.UUID;


public class SQLAuthDAO implements AuthDAO {
    private String statement = null;


    // Get AuthData object from authTable given an authToken
    public AuthData getAuthDataGivenAuthToken( String authToken ) throws DataAccessException {
        // Get resultList data
        statement = "SELECT authToken, username FROM authTable WHERE authToken = ?";
        List<Object> resultList = DatabaseManager.executeSingleRowQuery( statement, DatabaseManager.TableSource.AUTHTABLE, authToken );
        // If the resultList is empty, return null
        if ( resultList.isEmpty() ) return null;
        if ( resultList.size() != 2 )
            throw new DataAccessException( "Error: Did not receive resultList of size 2 in SQLAuthDAO.getAuthDataGivenAuthToken() -> Size = " + resultList.size() );
        // Parse resultList
        String username = (String) resultList.get( 1 );
        // Return the new AuthData object
        return new AuthData( authToken, username );
    }


    // Create a new authToken in authTable given a username
    public String createAuthToken( String username ) throws DataAccessException {
        // If username is null, throw an error
        if ( username == null ) throw new DataAccessException( "Error: Null username in createAuthToken()" );
        // Create the authToken and statement
        String authToken = UUID.randomUUID().toString();
        statement = "INSERT into authTable (authToken, username) VALUES (?, ?)";
        // Execute the statement and return the authToken
        DatabaseManager.executeUpdate( statement, authToken, username );
        return authToken;
    }


    // Verify that an authToken exists in the authTable
    public Boolean verifyAuthToken( String authToken ) throws DataAccessException {
        // Get resultList data
        statement = "SELECT authToken, username FROM authTable WHERE authToken = ?";
        // Return whether the resultSet of the request is empty
        return ( !DatabaseManager.executeSingleRowQuery( statement, DatabaseManager.TableSource.AUTHTABLE, authToken ).isEmpty() );
    }


    // Delete authData within authTable given authToken
    public void deleteAuthDataGivenAuthToken( String authToken ) throws DataAccessException {
        statement = "DELETE FROM authTable WHERE authToken = ?";
        DatabaseManager.executeUpdate( statement, authToken );
    }


    // Clear the entire authTable
    public void clear() throws DataAccessException {
        statement = "DELETE FROM authTable";
        DatabaseManager.executeUpdate( statement );
    }


    // Return whether authTable is empty
    public Boolean isEmpty() throws DataAccessException {
        // Get resultList data
        statement = "SELECT * FROM authTable";
        return ( DatabaseManager.executeMultipleRowQuery( statement, DatabaseManager.TableSource.AUTHTABLE ).isEmpty() );
    }

}
