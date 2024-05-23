package server;

import handlers.*;
import spark.*;

public class Server {

    ClearApplicationHandler clearApplicationHandler;
    CreateGameHandler createGameHandler;
    JoinGameHandler joinGameHandler;
    ListGamesHandler listGamesHandler;
    LoginHandler loginHandler;
    LogoutHandler logoutHandler;
    RegisterHandler registerHandler;

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
