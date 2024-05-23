package server;

import handlers.*;
import spark.*;

public class Server {

    ClearApplicationHandler clearApplicationHandler = new ClearApplicationHandler();
    CreateGameHandler createGameHandler = new CreateGameHandler();
    JoinGameHandler joinGameHandler = new JoinGameHandler();
    ListGamesHandler listGamesHandler = new ListGamesHandler();
    LoginHandler loginHandler = new LoginHandler();
    LogoutHandler logoutHandler = new LogoutHandler();
    RegisterHandler registerHandler = new RegisterHandler();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        // Clear Application
        Spark.delete("/db", clearApplicationHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
