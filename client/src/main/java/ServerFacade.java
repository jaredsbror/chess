import dataaccess.DataAccessException;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.exceptions.Error403AlreadyTaken;
import dataaccess.exceptions.Error500Internal;
import handlers.*;
import model.custom.*;
import model.original.GameData;
import server.Server;
import service.*;

import java.util.ArrayList;


public class ServerFacade {

    private static Server server;

    public static void init() {
        server = new Server();
        var port = server.run(3306);
        System.out.println("Started test HTTP server on " + port);
    }

    public static void stopServer() {
        server.stop();
    }

    public static ClearResult clearApplication() {
        ClearApplicationHandler clearApplicationHandler = new ClearApplicationHandler();
    }

    public static CreateResult createGame( CreateRequest createRequest) {
        CreateGameHandler createGameHandler = new CreateGameHandler();
    }

    public static JoinResult joinGame( JoinRequest joinRequest ) {

        JoinGameHandler joinGameHandler = new JoinGameHandler();
    }

    public static ArrayList<GameData> listGames( ListRequest listRequest ) {
        ListGamesHandler listGamesHandler = new ListGamesHandler();
    }

    public static LoginResult login( LoginRequest loginRequest ) {
        LoginHandler loginHandler = new LoginHandler();
    }

    public static LogoutResult logout( LogoutRequest logoutRequest ) {
        LogoutHandler logoutHandler = new LogoutHandler();
    }

    public static RegisterResult register( RegisterRequest registerRequest ) {
        RegisterHandler registerHandler = new RegisterHandler();
    }
}
