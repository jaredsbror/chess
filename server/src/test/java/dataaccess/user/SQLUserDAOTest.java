package dataaccess.user;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SQLUserDAOTest {

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
    @DisplayName( "Clear Empty Table" )
    void clearEmptyTable() {
    }

    @Test
    @Order( 6 )
    @DisplayName( "Clear Populated Table" )
    void clearPopulatedTable() {
    }

    @Test
    @Order( 7 )
    @DisplayName( "Is Empty" )
    void isEmpty() {
    }

    @Test
    @Order( 8 )
    @DisplayName( "Is Not Empty" )
    void isNotEmpty() {
    }
}