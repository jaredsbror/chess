import chess.ChessGame;
import chess.ChessPiece;
import handlers.ClearApplicationHandler;
import model.custom.ClearResult;
import ui.GameUI;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        ServerFacade serverFacade = new ServerFacade( "localhost", "8080" );
        try {
            ClearResult clearResult = serverFacade.clearApplication();
            System.out.println("ClearResult " + (clearResult == null ? "==" : "!=") + " null");
//            System.out.println((clearResult.success() ? "Success -> " : "Failure ->") + clearResult.message());

            ClearApplicationHandler clearApplicationHandler = new ClearApplicationHandler();
        } catch ( Exception exception ) {
            exception.printStackTrace();
            throw new RuntimeException( exception);
        }
//        serverFacade.initServer();

        GameUI.drawGameBoard();
    }
}