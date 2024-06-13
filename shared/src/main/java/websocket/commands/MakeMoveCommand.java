package websocket.commands;


import chess.ChessMove;


public class MakeMoveCommand extends UserGameCommand {
    private final int gameID;
    private final ChessMove chessMove;

    public MakeMoveCommand( String authToken, int gameID, ChessMove chessMove ) {
        super( authToken );
        this.gameID = gameID;
        this.chessMove = chessMove;
    }


    public int getGameID() {
        return gameID;
    }


    public ChessMove getChessMove() {
        return chessMove;
    }
}
