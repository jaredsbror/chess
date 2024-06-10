import dataaccess.DataAccessException;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.exceptions.Error403AlreadyTaken;
import dataaccess.exceptions.Error500Internal;
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
        try {
            ClearApplicationService clearApplicationService = new ClearApplicationService();
        } catch ( DataAccessException dataAccessException ) {

        }
    }

    public static CreateResult createGame( CreateRequest createRequest) {
        try {
            CreateGameService createGameService = new CreateGameService(createRequest);
            return createGameService.createGame();
        } catch ( DataAccessException dataAccessException ) {

        } catch ( Error400BadRequest error400BadRequest ) {

        } catch ( Error401Unauthorized error401Unauthorized ) {

        }
    }

    public static JoinResult joinGame( JoinRequest joinRequest ) {
        try {

        } catch ( Error403AlreadyTaken error403AlreadyTaken ) {

        } catch ( Error500Internal error500Internal ) {

        }
            JoinGameService joinGameService = new JoinGameService(joinRequest);
    }

    public static ArrayList<GameData> listGames( ListRequest listRequest ) {
        try {
            ListGamesService listGamesService = new ListGamesService(listRequest);
        } catch ( DataAccessException dataAccessException ) {

        } catch ( Error400BadRequest error400BadRequest ) {

        } catch ( Error401Unauthorized error401Unauthorized ) {

        } catch ( Error403AlreadyTaken error403AlreadyTaken ) {

        } catch ( Error500Internal error500Internal ) {

        }
    }

    public static String login( LoginRequest loginRequest ) {
        try {
            LoginService loginService = new LoginService(loginRequest);
        } catch ( DataAccessException dataAccessException ) {

        } catch ( Error400BadRequest error400BadRequest ) {

        } catch ( Error401Unauthorized error401Unauthorized ) {

        } catch ( Error403AlreadyTaken error403AlreadyTaken ) {

        } catch ( Error500Internal error500Internal ) {

        }
    }

    public static void logout( LogoutRequest logoutRequest ) {
        try {
            LogoutService logoutService = new LogoutService(logoutRequest);
        } catch ( DataAccessException dataAccessException ) {

        } catch ( Error400BadRequest error400BadRequest ) {

        } catch ( Error401Unauthorized error401Unauthorized ) {

        } catch ( Error403AlreadyTaken error403AlreadyTaken ) {

        } catch ( Error500Internal error500Internal ) {

        }
    }

    public static String register( RegisterRequest registerRequest ) {
        try {
            RegisterService registerService = new RegisterService(registerRequest);
            return registerService.register();
        } catch ( DataAccessException dataAccessException ) {

        } catch ( Error400BadRequest error400BadRequest ) {

        } catch ( Error403AlreadyTaken error403AlreadyTaken ) {

        }
    }
}
