package dataaccess.game;


import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import model.original.GameData;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class SQLGameDAO implements GameDAO {private Connection connection;
    private String statement = null;
    private static int newGameID = 1;

    // Constructor
    public SQLGameDAO() throws DataAccessException {
        // Verify the connection to the SQL database
//        DatabaseManager.pingDatabase();
//        DatabaseManager.pingTables();
    }

    public void clear() throws DataAccessException {
        statement = "DELETE FROM gameTable";
        DatabaseManager.executeStatementInChess(statement);
    }

    public int insertGame(String gameName) throws DataAccessException {
        // Generate a new gameID
        newGameID++;
        // Add the game to the gameTable and return the gameID
        statement = "INSERT INTO gameTable (gameID, whiteUsername, blackUsername, gameName, game) " +
                "VALUES (" + newGameID + ", null, null, '" + gameName + "', '" + new ChessGame().toString() + "')";
        DatabaseManager.executeStatementInChess( statement );
        return newGameID;
    }

    public GameData getGameData(int gameID) throws DataAccessException {
        statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM gameTable WHERE gameID = " + gameID;
        List<Object> resultList =  DatabaseManager.executeStatementAndReturnSingleRow( statement );
        // If the resultList is empty, return null
        if (resultList.isEmpty())
            return null;
        if (resultList.size() < 5)
            throw new DataAccessException( "Error: Did not receive resultList of size 5 or greater in SQLGameDAO.getData()" );
        // Parse resultList
        String whiteUsername = (String) resultList.get(1);
        String blackUsername = (String) resultList.get(2);
        String gameName = (String) resultList.get(3);
        String gameString = (String) resultList.get(4);
        // Parse game data from 'game'
        ChessGame.TeamColor teamColor = ChessBoard.parseColor( gameString );
        ChessPiece[][] board = ChessBoard.parseBoard( gameString );
        // Return the new gameData object
        return new GameData( gameID, whiteUsername, blackUsername, gameName, new ChessGame(teamColor, board) );
    }

    public void updateGame(int gameID, String username, String playerColor) throws DataAccessException {
        // Update the corresponding game depending on the team color
        if (playerColor.equalsIgnoreCase("white")) {
            statement = "UPDATE gameTable SET whiteUsername = '" + username + "' WHERE gameID = " + gameID;
            DatabaseManager.executeStatementInChess( statement );
        } else if ((playerColor.equalsIgnoreCase("black"))) {
            statement = "UPDATE gameTable SET blackUsername = '" + username + "' WHERE gameID = " + gameID;
            DatabaseManager.executeStatementInChess( statement );
        } else {
            throw new DataAccessException( "Error: Invalid color received in SQLGameDAO.updateGame()" );
        }
    }

    public ArrayList<GameData> getGameArrayList() throws DataAccessException {
        // Get the gameData from the table, but this time with multiple Lists<Object>.
        statement = "SELECT * FROM gameTable";
        // Make sure the gameTable is not empty before continuing
        // Return the final list
        return new ArrayList<>();
    }

    public boolean isEmpty() throws DataAccessException {
        // Get resultList data
        statement = "SELECT * FROM gameTable";
        return DatabaseManager.executeStatementAndReturnEmpty(statement);
    }
}
