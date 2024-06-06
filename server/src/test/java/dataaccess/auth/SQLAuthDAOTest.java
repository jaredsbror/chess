package dataaccess.auth;


import chess.Constants;
import dataaccess.DataAccessException;
import dataaccess.DatabaseUtil;
import model.original.AuthData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
class SQLAuthDAOTest {
    private final SQLAuthDAO sqlAuthDAO = new SQLAuthDAO();
    private AuthData authData = null;
    private String authToken = null;


    @Test
    @Order( 1 )
    @DisplayName( "Get AuthData from Empty AuthTable Given AuthToken" )
    public void getAuthDataFromEmptyTableGivenAuthToken() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            authData = sqlAuthDAO.getAuthDataGivenAuthToken( Constants.AUTH_TOKEN );
        }, "Error: Failed to get AuthData" );
        assertNull( authData, "Error: Returned AuthData should be null" );
    }


    @Test
    @Order( 2 )
    @DisplayName( "Get AuthData from Populated AuthTable Given AuthToken" )
    public void getAuthDataFromPopulatedTableGivenAuthToken() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            authToken = DatabaseUtil.populateDatabaseWithUser();
            authData = sqlAuthDAO.getAuthDataGivenAuthToken( authToken );
        }, "Error: Failed to get AuthData" );
        assertEquals( authData, new AuthData( authToken, Constants.USERNAME ), "Error: Failed to return correct AuthData" );
    }


    @Test
    @Order( 3 )
    @DisplayName( "Create AuthToken with Valid Username" )
    public void createAuthToken() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            sqlAuthDAO.createAuthToken( Constants.USERNAME );
        }, "Error: Failed to create authToken" );
    }


    @Test
    @Order( 4 )
    @DisplayName( "Create AuthToken with Empty Username" )
    public void createAuthTokenWithInvalidUsername() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
        assertThrows( DataAccessException.class, () -> sqlAuthDAO.createAuthToken( null ), "Error: Should not have created authToken" );
    }


    @Test
    @Order( 5 )
    @DisplayName( "Verify AuthToken from Empty AuthTable" )
    public void verifyAuthTokenFromEmptyTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            assertFalse( sqlAuthDAO.verifyAuthToken( Constants.AUTH_TOKEN ), "Error: Should not have returned true" );
        } );
    }


    @Test
    @Order( 6 )
    @DisplayName( "Verify AuthToken from Populated AuthTable" )
    public void verifyAuthTokenFromPopulatedTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            authToken = DatabaseUtil.populateDatabaseWithUser();
            assertTrue( sqlAuthDAO.verifyAuthToken( authToken ), "Error: Should not have returned false" );
        } );

    }


    @Test
    @Order( 7 )
    @DisplayName( "Delete AuthData from Empty AuthTable Given AuthToken" )
    public void deleteAuthDataFromEmptyTableGivenAuthToken() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            authToken = DatabaseUtil.populateDatabaseWithUser();
            sqlAuthDAO.deleteAuthDataGivenAuthToken( authToken );
            assertTrue( sqlAuthDAO.isEmpty(), "Error: AuthTable is not empty" );
        } );
    }


    @Test
    @Order( 8 )
    @DisplayName( "Delete AuthData from Populated AuthTable With AuthToken" )
    public void deleteAuthDataFromPopulatedTableGivenAuthToken() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            sqlAuthDAO.deleteAuthDataGivenAuthToken( Constants.AUTH_TOKEN );
            assertTrue( sqlAuthDAO.isEmpty(), "Error: AuthTable is not empty" );
        } );
    }


    @Test
    @Order( 9 )
    @DisplayName( "Clear Empty AuthTable" )
    public void clearEmptyAuthTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            sqlAuthDAO.clear();
            assertTrue( sqlAuthDAO.isEmpty(), "Error: AuthTable is not empty" );
        } );
    }


    @Test
    @Order( 10 )
    @DisplayName( "Clear Populated AuthTable" )
    public void clearPopulatedAuthTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            DatabaseUtil.populateDatabaseWithUser();
            sqlAuthDAO.clear();
            assertTrue( sqlAuthDAO.isEmpty(), "Error: AuthTable is not empty" );
        } );
    }


    @Test
    @Order( 11 )
    @DisplayName( "Is Empty AuthTable Empty" )
    public void isEmptyTableEmpty() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            assertTrue( sqlAuthDAO.isEmpty(), "Error: AuthTable is not empty" );
        } );
    }


    @Test
    @Order( 12 )
    @DisplayName( "Is Populated AuthTable not Empty" )
    public void isPopulatedTableNotEmpty() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            DatabaseUtil.populateDatabaseWithUser();
            assertFalse( sqlAuthDAO.isEmpty(), "Error: AuthTable is empty" );
        } );
    }
}