package dataaccess.game;


import chess.Constants;
import dataaccess.DataAccessException;
import dataaccess.DatabaseUtil;
import model.original.GameData;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder( MethodOrderer.OrderAnnotation.class)
class SQLGameDAOTest {
    private int gameID = 0;
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
            sqlGameDAO.insertGame( Constants.GAME_NAME );
            assertFalse( sqlGameDAO.isEmpty(), "Error: GameTable should not be empty" );
        });
    }

    @Test
    @Order( 4 )
    @DisplayName( "Insert Game with Same Name" )
    void insertGameWithSameName() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            int gameID1 = sqlGameDAO.insertGame( Constants.GAME_NAME );
            int gameID2 = sqlGameDAO.insertGame( Constants.GAME_NAME );
            GameData gameData1 = sqlGameDAO.getGameData( gameID1 );
            GameData gameData2 = sqlGameDAO.getGameData( gameID2 );
            assertEquals( gameData1.gameName(), gameData2.gameName(), "Error: Game names should be equal" );
        });
    }


    @Test
    @Order( 5 )
    @DisplayName( "Get GameData from Empty GameTable" )
    void getGameDataFromEmptyGameTable() {
        assertDoesNotThrow( DatabaseUtil::refreshDatabase );
        assertDoesNotThrow( () -> {
            GameData gameData = sqlGameDAO.getGameData( Constants.GAME_ID_INT );
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
    @DisplayName( "Update GameData with Valid TeamColor" )
    void updateGameDataWithValidTeamColor() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            authToken = DatabaseUtil.populateDatabaseWithUser();
            gameID = DatabaseUtil.populateDatabaseWithGame( authToken );
            assertDoesNotThrow( () -> sqlGameDAO.updateGame( gameID, Constants.USERNAME, "BLACK" ) );
        });
    }

    @Test
    @Order( 8 )
    @DisplayName( "Update GameData with Invalid TeamColor" )
    void updateGameDataWithInvalidTeamColor() {
        assertDoesNotThrow( () -> {
            DatabaseUtil.refreshDatabase();
            authToken = DatabaseUtil.populateDatabaseWithUser();
            gameID = DatabaseUtil.populateDatabaseWithGame( authToken );
            assertThrows( DataAccessException.class, () -> sqlGameDAO.updateGame( gameID, Constants.USERNAME, "oogabooga" ), "Error: Should have returned DataAccessException" );
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