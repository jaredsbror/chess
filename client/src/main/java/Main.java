import chess.ChessGame;
import chess.ChessPiece;
import ui.ChessClient;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        ChessClient chessClient = null;
        try {
            chessClient = new ChessClient( 8080);
            chessClient.preLoginUI();
        } catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }
}