package dataaccess.user;


import chess.Constants;
import dataaccess.DatabaseUtil;
import model.original.AuthData;
import model.original.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder( MethodOrderer.OrderAnnotation.class)
class SQLUserDAOTest {
    private String authToken = null;
    private AuthData authData = null;
    private final SQLUserDAO sqlUserDAO = new SQLUserDAO();

    @Test
    @Order( 1 )
    @DisplayName( "Get UserData with Valid Username" )
    void getUserDataWithValidUsername() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            DatabaseUtil.populateDatabaseWithUser();
            UserData userData = sqlUserDAO.getUser( Constants.username );
            assertNotNull( userData, "Error: UserData should not be null" );
            assertEquals( userData, new UserData( Constants.username, Constants.password, Constants.email ), "Error: Did not retrieve correct UserData" );
        });
    }

    @Test
    @Order( 2 )
    @DisplayName( "Get UserData with Invalid Username" )
    void getUserDataWithInvalidUsername() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            DatabaseUtil.populateDatabaseWithUser();
            assertNull( sqlUserDAO.getUser( "oogabooga" ), "Error: Userdata should be null" );
        });
    }

    @Test
    @Order( 3 )
    @DisplayName( "Insert User" )
    void insertUser() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            assertDoesNotThrow( DatabaseUtil::populateDatabaseWithUser, "Error: Should not have thrown error when inserting user");
        });
    }

    @Test
    @Order( 4 )
    @DisplayName( "Insert Repeated User" )
    void insertRepeatedUser() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            DatabaseUtil.populateDatabaseWithUser();
            assertThrows( Exception.class, DatabaseUtil::populateDatabaseWithUser, "Error: Should have thrown error for having duplicate usernames (i.e. primary keys)");
        });
    }

    @Test
    @Order( 5 )
    @DisplayName( "Clear Empty UserTable" )
    void clearEmptyTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            sqlUserDAO.clear();
            assertTrue( sqlUserDAO.isEmpty(), "Error: UserTable is not empty" );
        });
    }

    @Test
    @Order( 6 )
    @DisplayName( "Clear Populated UserTable" )
    void clearPopulatedTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            DatabaseUtil.populateDatabaseWithUser();
            sqlUserDAO.clear();
            assertTrue( sqlUserDAO.isEmpty(), "Error: UserTable is not empty" );
        });
    }

    @Test
    @Order( 7 )
    @DisplayName( "Is Empty UserTable Empty" )
    void isEmptyTableEmpty() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            assertTrue( sqlUserDAO.isEmpty(), "Error: UserTable is not empty" );
        });
    }

    @Test
    @Order( 8 )
    @DisplayName( "Is Populated UserTable Not Empty" )
    void isPopulatedTableNotEmpty() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            DatabaseUtil.populateDatabaseWithUser();
            assertFalse( sqlUserDAO.isEmpty(), "Error: UserTable should not be empty" );
        });
    }
}