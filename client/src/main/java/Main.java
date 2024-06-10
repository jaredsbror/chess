import chess.ChessGame;
import chess.ChessPiece;
import connections.ServerFacade;
import handlers.ClearApplicationHandler;
import model.custom.ClearResult;
import model.custom.RegisterRequest;
import model.custom.RegisterResult;
import ui.GameUI;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        ServerFacade serverFacade = new ServerFacade();
        try {
            RegisterResult registerResult = serverFacade.register( new RegisterRequest( "username", "password", "email@gmail.com" ) );
            System.out.println(registerResult);



            ClearResult clearResult = serverFacade.clearApplication();
//            System.out.println("ClearResult " + (clearResult == null ? "==" : "!=") + " null");
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