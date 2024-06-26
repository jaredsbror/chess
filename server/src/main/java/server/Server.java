package server;


import dataaccess.DatabaseManager;
import handlers.*;
import spark.Spark;
import websocket.WSServer;


public class Server {

    private final ClearApplicationHandler clearApplicationHandler;
    private final CreateGameHandler createGameHandler;
    private final JoinGameHandler joinGameHandler;
    private final ListGamesHandler listGamesHandler;
    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;
    private final RegisterHandler registerHandler;


    public Server() {
        clearApplicationHandler = new ClearApplicationHandler();
        createGameHandler = new CreateGameHandler();
        joinGameHandler = new JoinGameHandler();
        listGamesHandler = new ListGamesHandler();
        loginHandler = new LoginHandler();
        logoutHandler = new LogoutHandler();
        registerHandler = new RegisterHandler();
    }


    public int run( int desiredPort ) {
        Spark.port( desiredPort );
        try {
            DatabaseManager.createDatabase();
        } catch ( Exception exception ) {
            exception.printStackTrace();
            throw new RuntimeException( exception );
        }

        Spark.staticFiles.location( "web" );

        // Websocket Path
        Spark.webSocket("/ws", WSServer.class);

        // Register your endpoints and handle exceptions here.
        // Clear Application
        Spark.delete( "/db", clearApplicationHandler );

        // Register user
        Spark.post( "/user", registerHandler );

        // Login user
        Spark.post( "/session", loginHandler );

        // Logout user
        Spark.delete( "/session", logoutHandler );

        // Create game
        Spark.post( "/game", createGameHandler );

        // Join game
        Spark.put( "/game", joinGameHandler );

        // List games
        Spark.get( "/game", listGamesHandler );



        Spark.awaitInitialization();
        return Spark.port();
    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
