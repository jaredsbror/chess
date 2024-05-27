package dataAccess.game;

import chess.ChessGame;
import dataAccess.GameDAO;
import model.GameData;
import model.RegisterResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

// Contains and modifies GameData in Chess application
public class MemoryGameDAO implements GameDAO {
    private static Map<Integer, GameData> gameTable = new HashMap<>();

    public void clear() {
        gameTable.clear();
    }

    public int insertGame(String gameName) {
        // Generate a random gameID
        Random random = new Random();
        int gameID = random.nextInt();
        // Make sure that the gameID does not already exist
        while (gameTable.get(gameID) != null) {
            gameID = random.nextInt();
        }
        // Add the game to the gameTable and return the gameID
        gameTable.put(gameID, new GameData(gameID, null, null, gameName, new ChessGame()));
        return gameID;
    }
}
