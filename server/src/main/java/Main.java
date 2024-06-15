import chess.ChessGame;
import chess.ChessPiece;
import dataaccess.exceptions.DataAccessException;
import dataaccess.DatabaseManager;
import server.Server;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
        try {
            DatabaseManager.createDatabase();
        } catch ( DataAccessException e ) {
            throw new RuntimeException( e );
        }

        Server spark = new Server();
        System.out.printf("Spark Port: %d", spark.run(8080));
    }
}