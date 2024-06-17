package websocket.commands;


import chess.ChessMove;


public class MakeMoveCommand extends UserGameCommand {
    private final int gameID;
    private final ChessMove move;
    private final String playerName;

    public MakeMoveCommand( String authToken, int gameID, ChessMove move, String playerName ) {
        super( authToken );
        this.gameID = gameID;
        this.move = move;
        this.playerName = playerName;
    }


    public int getGameID() {
        return gameID;
    }


    public ChessMove getChessMove() {
        return move;
    }


    public String getPlayerName() {
        return playerName;
    }
}
