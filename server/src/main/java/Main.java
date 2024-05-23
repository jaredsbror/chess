import chess.*;
import server.Server;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);

        Server spark = new Server();
        System.out.printf("Spark Port: %d", spark.run(8080));

    }
}