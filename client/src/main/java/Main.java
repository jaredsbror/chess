import chess.ChessGame;
import chess.ChessPiece;
import ui.ClientUI;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        ClientUI clientUI = null;
        try {
            clientUI = new ClientUI( 8080);
        } catch ( Exception e ) {
            throw new RuntimeException( e );
        }
        clientUI.preLoginUI();
    }
}