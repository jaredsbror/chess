package dataAccess.game;

import chess.ChessGame;
import dataAccess.GameDAO;
import model.GameData;
import model.RegisterResult;

import java.util.*;

// Contains and modifies GameData in Chess application
public class MemoryGameDAO implements GameDAO {
    private static Map<Integer, GameData> gameTable = new HashMap<>();
    private static int newGameID = 1;

    public void clear() {
        gameTable.clear();
    }

    // Verify that a gameID already exists.
    public boolean verifyGameID(int gameID) {
        return gameTable.get(gameID) != null;
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
        ArrayList<GameData> gameDataArrayList = new ArrayList<>();
        // Make sure the gameTable is not empty before continuing
        if (gameTable.isEmpty()) return gameDataArrayList;
        // Iterate over the gameTable and add each gameData to the new list
        for (var gameData: gameTable.entrySet()) {
            gameDataArrayList.add(gameData.getValue());
        }
        // Return the final list
        return gameDataArrayList;
    }
}
