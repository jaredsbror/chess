package dataaccess.game;


import chess.Constants;
import dataaccess.DatabaseUtil;
import model.original.GameData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SQLGameDAOTest {
    private int gameID = 0;
    private String whiteUsername = null;
    private String blackUsername = null;
    private String gameName = null;
    private GameData gameData = null;
    private String authToken = null;
    private final SQLGameDAO sqlGameDAO = new SQLGameDAO();

    @Test
    @Order( 1 )
    @DisplayName( "Clear Empty Game Table" )
    void clearEmptyGameTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            sqlGameDAO.clear();
            assertTrue( sqlGameDAO.isEmpty(), "Error: GameTable is not empty" );
        });
    }

    @Test
    @Order( 2 )
    @DisplayName( "Clear Populated Game Table" )
    void clearPopulatedGameTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            authToken = DatabaseUtil.populateDatabaseWithUser();
            DatabaseUtil.populateDatabaseWithGame( authToken );
            sqlGameDAO.clear();
            assertTrue( sqlGameDAO.isEmpty(), "Error: GameTable is not empty" );
        });
    }


    @Test
    @Order( 3 )
    @DisplayName( "Insert New Game" )
    void insertNewGame() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
        });
    }

    @Test
    @Order( 4 )
    @DisplayName( "Insert Game with Same " )
    void insertGameWithSameName() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();

        });
    }


    @Test
    @Order( 5 )
    @DisplayName( "Get GameData from Empty GameTable" )
    void getGameDataFromEmptyGameTable() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
        assertDoesNotThrow( () -> {
            GameData gameData = sqlGameDAO.getGameData( Constants.gameIDInt );
            assertNull( gameData, "Error: GameData should be null" );
        });
    }

    @Test
    @Order( 6 )
    @DisplayName( "Get GameData from Populated GameTable" )
    void getGameDataFromPopulatedGameTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            authToken = DatabaseUtil.populateDatabaseWithUser();
            gameID = DatabaseUtil.populateDatabaseWithGame( authToken );
            gameData = sqlGameDAO.getGameData( gameID );
            assertNotNull( gameData, "Error: GameData should not be null" );
        });
    }


    @Test
    @Order( 7 )
    @DisplayName( "Update GameData with Valid gameID" )
    void updateGameDataWithValidGameID() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();

        });
    }

    @Test
    @Order( 8 )
    @DisplayName( "Update GameData with Invalid gameID" )
    void updateGameDataWithInvalidGameID() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();

        });
    }


    @Test
    @Order( 9 )
    @DisplayName( "Get GameDataList from Empty GameTable" )
    void getGameDataListFromEmptyGameTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            List<GameData> gameDataList = sqlGameDAO.getGameArrayList( );
            assertTrue( gameDataList.isEmpty(), "Error: GameDataList should be empty" );
        });
    }

    @Test
    @Order( 10 )
    @DisplayName( "Get GameDataList from Populated GameTable" )
    void getGameDataListFromPopulatedGameTable() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            authToken = DatabaseUtil.populateDatabaseWithUser();
            gameID = DatabaseUtil.populateDatabaseWithGame( authToken );
            List<GameData> gameDataList  = sqlGameDAO.getGameArrayList();
            assertFalse( gameDataList.isEmpty(), "Error: GameDataList should not be empty" );
        });
    }


    @Test
    @Order( 11 )
    @DisplayName( "Is Empty GameTable Empty" )
    public void isEmptyTableEmpty() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            assertTrue( sqlGameDAO.isEmpty(), "Error: GameTable is not empty" );
        });
    }

    @Test
    @Order( 12 )
    @DisplayName( "Is Populated GameTable not Empty" )
    public void isPopulatedTableNotEmpty() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            authToken = DatabaseUtil.populateDatabaseWithUser();
            DatabaseUtil.populateDatabaseWithGame( authToken );
            assertFalse( sqlGameDAO.isEmpty(), "Error: GameTable should not be empty" );
        });
    }
}