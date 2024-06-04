package dataaccess.user;


import dataaccess.DatabaseUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SQLUserDAOTest {
    private String authToken = null;
    private final SQLUserDAO sqlUserDAO = new SQLUserDAO();

    @Test
    @Order( 1 )
    @DisplayName( "Get UserData with Valid Username" )
    void getUserDataWithValidUsername() {
    }

    @Test
    @Order( 2 )
    @DisplayName( "Get UserData with Invalid Username" )
    void getUserDataWithInvalidUsername() {
    }

    @Test
    @Order( 3 )
    @DisplayName( "Insert User" )
    void insertUser() {
    }

    @Test
    @Order( 4 )
    @DisplayName( "Insert Repeated User" )
    void insertRepeatedUser() {
    }

    @Test
    @Order( 5 )
    @DisplayName( "Clear Empty UserTable" )
    void clearEmptyTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
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