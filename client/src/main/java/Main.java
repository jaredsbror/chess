import chess.ChessGame;
import chess.ChessPiece;
import connections.ServerFacade;
import dataaccess.exceptions.Error500Internal;
import handlers.ClearApplicationHandler;
import model.custom.*;
import ui.GameUI;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        ServerFacade serverFacade = new ServerFacade();
        try {
            ClearResult clearResult = serverFacade.clearApplication();
            System.out.println(clearResult);

            RegisterResult registerResult = serverFacade.register( new RegisterRequest( "username", "password", "email@gmail.com" ) );
            System.out.println(registerResult);

            LoginResult loginResult = serverFacade.login( new LoginRequest( "username", "password" ) );
            System.out.println(loginResult);

            CreateResult createResult = serverFacade.createGame( new CreateRequest( registerResult.authToken(), "game" ) );
            System.out.println(createResult);

            JoinResult joinResult = serverFacade.joinGame( new JoinRequest( loginResult.authToken(), "BLACK", createResult.gameID() ) );
            System.out.println(joinResult);


            ListResult listResult = serverFacade.listGames( new ListRequest( loginResult.authToken() ) );
            System.out.println(listResult);
            listResult.games()

        } catch ( Error500Internal | URISyntaxException | IOException e ) {
            throw new RuntimeException( e );
        }
//        serverFacade.initServer();

        GameUI.drawGameBoard();
    }
}