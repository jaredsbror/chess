package dataaccess.auth;


import chess.Constants;
import dataaccess.DataAccessException;
import dataaccess.DatabaseUtil;
import dataaccess.game.SQLGameDAO;
import dataaccess.user.SQLUserDAO;
import model.original.AuthData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SQLAuthDAOTest {
    private AuthData authData;
    private String authToken;
    private String username;
    private SQLAuthDAO sqlAuthDAO = new SQLAuthDAO();
    private SQLGameDAO sqlGameDAO = new SQLGameDAO();
    private SQLUserDAO sqlUserDAO = new SQLUserDAO();


    @Test
    @Order( 1 )
    @DisplayName( "Get AuthData from Empty AuthTable Given AuthToken" )
    public void GetAuthDataFromEmptyTableGivenAuthToken() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
        assertDoesNotThrow( () -> {
            authData = sqlAuthDAO.getAuthDataGivenAuthToken( Constants.authToken );
        }, "Error: Failed to get AuthData" );
        assertNull( authData, "Error: Returned AuthData should be null");
    }

    @Test
    @Order( 2 )
    @DisplayName( "Get AuthData from Populated AuthTable Given AuthToken" )
    public void GetAuthDataFromPopulatedTableGivenAuthToken() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
        assertDoesNotThrow( () -> {
            authToken = DatabaseUtil.populateDatabaseWithUser();
            authData = sqlAuthDAO.getAuthDataGivenAuthToken( authToken );
        }, "Error: Failed to get AuthData" );
        assertEquals( authData, new AuthData( authToken, Constants.username ), "Error: Failed to return correct AuthData" );
    }

    @Test
    @Order( 3 )
    @DisplayName( "Create AuthToken with Valid Username" )
    public void createAuthTokenWithValidUsername() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
    }

    @Test
    @Order( 4 )
    @DisplayName( "Create AuthToken with Invalid Username" )
    public void createAuthTokenWithInvalidUsername() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
    }

    @Test
    @Order( 5 )
    @DisplayName( "Verify AuthToken from Empty AuthTable" )
    public void verifyAuthTokenFromEmptyTable() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
    }

    @Test
    @Order( 6 )
    @DisplayName( "Verify AuthToken from Populated AuthTable" )
    public void verifyAuthTokenFromPopulatedTable() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
    }

    @Test
    @Order( 7 )
    @DisplayName( "Delete AuthData from Empty AuthTable Given AuthToken" )
    public void deleteAuthDataFromEmptyTableGivenAuthToken() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
    }

    @Test
    @Order( 8 )
    @DisplayName( "Delete AuthData from Populated AuthTable With AuthToken" )
    public void deleteAuthDataFromPopulatedTableGivenAuthToken() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
    }

    @Test
    @Order( 9 )
    @DisplayName( "Clear Empty AuthTable" )
    public void clearEmptyAuthTable() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
    }

    @Test
    @Order( 10 )
    @DisplayName( "Clear Populated AuthTable" )
    public void clearPopulatedAuthTable() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
    }

    @Test
    @Order( 11 )
    @DisplayName( "Is Empty AuthTable Empty" )
    public void isEmptyTableEmpty() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
    }

    @Test
    @Order( 12 )
    @DisplayName( "Is Populated AuthTable not Empty" )
    public void isPopulatedTableNotEmpty() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
    }
}