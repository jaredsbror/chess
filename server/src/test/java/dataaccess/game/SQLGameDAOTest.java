package dataaccess.game;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SQLGameDAOTest {

    @Test
    @Order( 1 )
    @DisplayName( "Clear Empty Game Table" )
    void clearEmptyGameTable() {
    }

    @Test
    @Order( 2 )
    @DisplayName( "Clear Populated Game Table" )
    void clearPopulatedGameTable() {
    }


    @Test
    @Order( 3 )
    @DisplayName( "Insert New Game" )
    void insertNewGame() {
    }

    @Test
    @Order( 4 )
    @DisplayName( "Insert Game with Same " )
    void insertGameWithSameName() {
    }


    @Test
    @Order( 5 )
    @DisplayName( "Get GameData with Valid gameID" )
    void getGameDataWithValidGameID() {
    }

    @Test
    @Order( 6 )
    @DisplayName( "Get GameData with Invalid gameID" )
    void getGameDataWithInvalidGameID() {
    }


    @Test
    @Order( 7 )
    @DisplayName( "Update GameData with Valid gameID" )
    void updateGameDataWithValidGameID() {
    }

    @Test
    @Order( 8 )
    @DisplayName( "Update GameData with Invalid gameID" )
    void updateGameDataWithInvalidGameID() {
    }


    @Test
    @Order( 9 )
    @DisplayName( "Get GameData from Empty GameTable" )
    void getGameDataFromEmptyGameTable() {
    }

    @Test
    @Order( 10 )
    @DisplayName( "Get GameData from Populated GameTable" )
    void getGameDataFromPopulatedGameTable() {
    }


    @Test
    @Order( 11 )
    @DisplayName( "Is Empty" )
    void isEmpty() {
    }

    @Test
    @Order( 12 )
    @DisplayName( "Is Not Empty" )
    void isNotEmpty() {
    }
}