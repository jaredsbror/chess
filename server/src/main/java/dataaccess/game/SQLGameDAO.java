package dataaccess.game;


import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.original.GameData;

import java.sql.Connection;
import java.util.ArrayList;


public class SQLGameDAO implements GameDAO {private Connection connection;
    private String statement = null;
    private static int newGameID = 1;

    // Constructor
    public SQLGameDAO() throws DataAccessException {
        // Verify the connection to the SQL database
//        DatabaseManager.pingDatabase();
//        DatabaseManager.pingTables();
    }

    public void clear() {
        gameTable.clear();
    }

    public int insertGame(String gameName) {
        // Generate a new gameID
        newGameID++;
        // Add the game to the gameTable and return the gameID
        gameTable.put(newGameID, new GameData(newGameID, null, null, gameName, new ChessGame()));
        return newGameID;
    }

    public GameData getGameData(int gameID) {
        return gameTable.get(gameID);
    }

    public void updateGame(int gameID, String username, String playerColor) {
        // Since records are immutable, first save a copy of the record.
        // Then delete it from the hashmap.
        GameData gameData = gameTable.get(gameID);
        gameTable.remove(gameID);
        // Update the corresponding game depending on the team color
        if (playerColor.equalsIgnoreCase("white")) {
            gameTable.put(gameID, new GameData(gameID, username, gameData.blackUsername(), gameData.gameName(), gameData.game()));
        } else {
            gameTable.put(gameID, new GameData(gameID, gameData.whiteUsername(), username, gameData.gameName(), gameData.game()));
        }
    }

    public ArrayList<GameData> getGameArrayList() {
        // Convert the gameTable map to a list
        // Make sure the gameTable is not empty before continuing
        // Iterate over the gameTable and add each gameData to the new list
        // Return the final list
    }

    public boolean isEmpty() {
    }
}
