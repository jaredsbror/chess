package server;

import dataaccess.DatabaseManager;
import dataaccess.exceptions.Error500Internal;
import handlers.*;
import spark.*;

public class Server {

    private final ClearApplicationHandler clearApplicationHandler;
    private final CreateGameHandler createGameHandler;
    private final JoinGameHandler joinGameHandler;
    private final ListGamesHandler listGamesHandler;
    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;
    private final RegisterHandler registerHandler;

    public Server() {
        try {
            clearApplicationHandler = new ClearApplicationHandler();
            createGameHandler = new CreateGameHandler();
            joinGameHandler = new JoinGameHandler();
            listGamesHandler = new ListGamesHandler();
            loginHandler = new LoginHandler();
            logoutHandler = new LogoutHandler();
            registerHandler = new RegisterHandler();
        } catch (Error500Internal e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        try {
            DatabaseManager.createDatabase();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        // Clear Application
        Spark.delete("/db", clearApplicationHandler);

        // Register user
        Spark.post("/user", registerHandler);

        // Login user
        Spark.post("/session", loginHandler);

        // Logout user
        Spark.delete("/session", logoutHandler);

        // Create game
        Spark.post("/game", createGameHandler);

        // Join game
        Spark.put("/game", joinGameHandler);

        // List games
        Spark.get("/game", listGamesHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
