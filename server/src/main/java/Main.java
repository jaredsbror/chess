import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import server.Server;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);

        Server spark = new Server();
        System.out.printf("Spark Port: %d", spark.run(8080));
        ChessGame chessGame = new ChessGame();
        String chessGameString = chessGame.toString();
        ChessGame.TeamColor color = ChessBoard.parseColor( chessGameString );
        ChessPiece[][] board = ChessBoard.parseBoard( chessGameString );

    }
}