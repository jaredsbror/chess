package connections;


import com.google.gson.Gson;
import dataaccess.exceptions.Error500Internal;
import handlers.ClearApplicationHandler;
import model.custom.*;
import server.Server;

import static connections.HTTPClient.submitRequest;


public class ServerFacade {

    private Server server;
    private final String baseUrl;
    private int statusCode;
    private final Gson gson = new Gson();

    public ServerFacade(String host, String port) {
        this.baseUrl = "http://" + host + ":" + port;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void initServer() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started HTTP server on " + port);
    }

    public void stopServer() {
        server.stop();
    }

    public ClearResult clearApplication() throws Error500Internal {
        try {
            return gson.fromJson( submitRequest( HTTPClient.HttpRequestType.CLEAR_APPLICATION, "/db", "DELETE", null, null), ClearResult.class);
        } catch ( Exception e ) {
            throw new Error500Internal( e.getMessage() );
        }
    }

    public CreateResult createGame( CreateRequest createRequest) throws Error500Internal {
        try {
            return gson.fromJson( submitRequest( HTTPClient.HttpRequestType.CREATE_GAME, "/game", "POST", createRequest.authToken(), createRequest), CreateResult.class);
        } catch ( Exception e ) {
            throw new Error500Internal( e.getMessage() );
        }
    }

    public JoinResult joinGame( JoinRequest joinRequest ) throws Error500Internal {
        try {
            return gson.fromJson( submitRequest( HTTPClient.HttpRequestType.JOIN_GAME, "/game", "PUT", joinRequest.authToken(), joinRequest), JoinResult.class);
        } catch ( Exception e ) {
            throw new Error500Internal( e.getMessage() );
        }
    }

    public ListResult listGames( ListRequest listRequest ) throws Error500Internal {
        try {
            return gson.fromJson( submitRequest( HTTPClient.HttpRequestType.LIST_GAMES, "/game", "GET", listRequest.authToken(), listRequest), ListResult.class);
        } catch ( Exception e ) {
            throw new Error500Internal( e.getMessage() );
        }
    }

    public LoginResult login( LoginRequest loginRequest ) throws Error500Internal {
        try {
            return gson.fromJson( submitRequest( HTTPClient.HttpRequestType.LOGIN, "/session", "POST", null, loginRequest), LoginResult.class);
        } catch ( Exception e ) {
            throw new Error500Internal( e.getMessage() );
        }
    }

    public LogoutResult logout( LogoutRequest logoutRequest ) throws Error500Internal {
        try {
            return gson.fromJson( submitRequest( HTTPClient.HttpRequestType.LOGOUT, "/session", "DELETE", logoutRequest.authToken(), logoutRequest), LogoutResult.class);
        } catch ( Exception e ) {
            throw new Error500Internal( e.getMessage() );
        }
    }

    public RegisterResult register( RegisterRequest registerRequest ) throws Error500Internal {
        try {
            return gson.fromJson( submitRequest( HTTPClient.HttpRequestType.REGISTER, "/user", "POST", null, registerRequest), RegisterResult.class);
        } catch ( Exception e ) {
            throw new Error500Internal( e.getMessage() );
        }
    }


}
