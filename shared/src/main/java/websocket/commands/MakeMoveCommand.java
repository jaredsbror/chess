package websocket.commands;


import chess.ChessMove;


public class MakeMoveCommand extends UserGameCommand {
    private final int gameID;
    private final ChessMove move;

    public MakeMoveCommand( String authToken, int gameID, ChessMove move ) {
        super( authToken );
        this.gameID = gameID;
        this.move = move;
    }


    public int getGameID() {
        return gameID;
    }


    public ChessMove getChessMove() {
        return move;
    }
}