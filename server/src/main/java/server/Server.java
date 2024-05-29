package server;

import handlers.*;
import spark.*;

public class Server {

    private ClearApplicationHandler clearApplicationHandler = new ClearApplicationHandler();
    private CreateGameHandler createGameHandler = new CreateGameHandler();
    private JoinGameHandler joinGameHandler = new JoinGameHandler();
    private ListGamesHandler listGamesHandler = new ListGamesHandler();
    private LoginHandler loginHandler = new LoginHandler();
    private LogoutHandler logoutHandler = new LogoutHandler();
    private RegisterHandler registerHandler = new RegisterHandler();


    public int run(int desiredPort) {
        Spark.port(desiredPort);

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
