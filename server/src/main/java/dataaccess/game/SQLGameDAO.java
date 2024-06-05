package dataaccess.game;


import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import model.original.GameData;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class SQLGameDAO implements GameDAO {
    private String statement = null;


    public void clear() throws DataAccessException {
        statement = "DELETE FROM gameTable";
        DatabaseManager.executeUpdate( statement );
    }


    public int insertGame( String gameName ) throws DataAccessException {
        // Generate a new gameID (done within the SQL table)
        // Add the game to the gameTable and return the gameID
        String chessGameString = new ChessGame().toString();
        this.statement = "INSERT INTO gameTable (whiteUsername, blackUsername, gameName, game) " +
                "VALUES (?, ?, ?, ?)";
        BigInteger gameID = (BigInteger) DatabaseManager.executeUpdate( statement, null, null, gameName, chessGameString );
        return gameID.intValue();
    }


    public GameData getGameData( int gameID ) throws DataAccessException {
        statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM gameTable WHERE gameID = ?";
        List<Object> resultList = DatabaseManager.executeSingleRowQuery( statement, DatabaseManager.TableSource.GAMETABLE, gameID );
        // If the resultList is empty, return null
        if ( resultList.isEmpty() )
            return null;
        if ( resultList.size() != 5 )
            throw new DataAccessException( "Error: Did not receive resultList of size 5 in SQLGameDAO.getData() -> Size = " + resultList.size() );

        // Parse resultList
        String whiteUsername = (String) resultList.get( 1 );
        String blackUsername = (String) resultList.get( 2 );
        String gameName = (String) resultList.get( 3 );
        String gameString = (String) resultList.get( 4 );
        // Parse game data from 'game'
        ChessGame.TeamColor teamColor = ChessBoard.parseColor( gameString );
        ChessPiece[][] board = ChessBoard.parseBoard( gameString );
        // Return the new gameData object
        return new GameData( gameID, whiteUsername, blackUsername, gameName, new ChessGame( teamColor, board ) );
    }


    public void updateGame( int gameID, String username, String playerColor ) throws DataAccessException {
        // Update the corresponding game depending on the team color
        if ( playerColor.equalsIgnoreCase( "white" ) ) {
            statement = "UPDATE gameTable SET whiteUsername = ? WHERE gameID = ?";
            DatabaseManager.executeUpdate( statement, username, gameID );
        } else if ( ( playerColor.equalsIgnoreCase( "black" ) ) ) {
            statement = "UPDATE gameTable SET blackUsername = ? WHERE gameID = ?";
            DatabaseManager.executeUpdate( statement, username, gameID );
        } else {
            throw new DataAccessException( "Error: Invalid color received in SQLGameDAO.updateGame()" );
        }
    }


    public ArrayList<GameData> getGameArrayList() throws DataAccessException {
        // Get the gameData from the table, but this time with multiple Lists<Object>.
        statement = "SELECT * FROM gameTable";
        // Return the final list
        List<Object> gameDataObjectLists = DatabaseManager.executeMultipleRowQuery( statement, DatabaseManager.TableSource.GAMETABLE );
        ArrayList<GameData> gameDataList = new ArrayList<>();
        // Convert the List<Object> to List<GameData>
        for ( var list : gameDataObjectLists ) {
            gameDataList.add( (GameData) list );
        }
        return gameDataList;
    }


    public boolean isEmpty() throws DataAccessException {
        // Get resultList data
        statement = "SELECT * FROM gameTable";
        return ( DatabaseManager.executeMultipleRowQuery( statement, DatabaseManager.TableSource.GAMETABLE ).isEmpty() );
    }
}
