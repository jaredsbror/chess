package server;

import handlers.*;
import spark.*;

public class Server {

    // Handler instances to call on
    private ClearApplicationHandler clearApplicationHandler;
    private CreateGameHandler createGameHandler;
    private JoinGameHandler joinGameHandler;
    private ListGamesHandler listGamesHandler;
    private LoginHandler loginHandler;
    private LogoutHandler logoutHandler;
    private RegisterHandler registerHandler;

    // Constructor
    public Server(ClearApplicationHandler clearApplicationHandler, CreateGameHandler createGameHandler, handlers.JoinGameHandler joinGameHandler, ListGamesHandler listGamesHandler, LoginHandler loginHandler, LogoutHandler logoutHandler, RegisterHandler registerHandler) {
        this.clearApplicationHandler = clearApplicationHandler;
        this.createGameHandler = createGameHandler;
        this.joinGameHandler = joinGameHandler;
        this.listGamesHandler = listGamesHandler;
        this.loginHandler = loginHandler;
        this.logoutHandler = logoutHandler;
        this.registerHandler = registerHandler;
    }

    // Run the server
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    // Get the server port
    public int port() {
        return Spark.port();
    }

    // Stop the server
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
