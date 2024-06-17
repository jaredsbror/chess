package websocket.commands;


import chess.ChessMove;


public class MakeMoveCommand extends UserGameCommand {
    private final ChessMove move;

    public MakeMoveCommand( String authToken, int gameID, ChessMove move ) {
        super( authToken, gameID );
        this.move = move;
    }

    public ChessMove getChessMove() {
        return move;
    }
}
