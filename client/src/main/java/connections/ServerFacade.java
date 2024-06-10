package connections;


import com.google.gson.Gson;
import dataaccess.exceptions.Error500Internal;
import model.custom.*;
import server.Server;

import static connections.HTTPClient.submitRequest;


public class ServerFacade {
    private Server server;
    private int statusCode;
    private String statusString;
    private final Gson gson = new Gson();

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusString() {
        return this.statusString;
    }

    public void initServer() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started HTTP server on " + port);
    }

    public void stopServer() {
        server.stop();
    }

    public ClearResult clearApplication() throws Error500Internal {
        try {
            ClearResult clearResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.CLEAR_APPLICATION, "/db", "DELETE", null, null), ClearResult.class);
            statusCode = HTTPClient.getResponseCode();
            statusString = HTTPClient.getResponseString();
            return clearResult;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new Error500Internal( e.getMessage() );
        }
    }

    public CreateResult createGame( CreateRequest createRequest) throws Error500Internal {
        try {
            CreateResult createResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.CREATE_GAME, "/game", "POST", createRequest.authToken(), createRequest), CreateResult.class);
            statusCode = HTTPClient.getResponseCode();
            statusString = HTTPClient.getResponseString();
            return createResult;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new Error500Internal( e.getMessage() );
        }
    }

    public JoinResult joinGame( JoinRequest joinRequest ) throws Error500Internal {
        try {
            JoinResult joinResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.JOIN_GAME, "/game", "PUT", joinRequest.authToken(), joinRequest), JoinResult.class);
            statusCode = HTTPClient.getResponseCode();
            statusString = HTTPClient.getResponseString();
            return joinResult;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new Error500Internal( e.getMessage() );
        }
    }

    public ListResult listGames( ListRequest listRequest ) throws Error500Internal {
        try {
            ListResult listResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.LIST_GAMES, "/game", "GET", listRequest.authToken(), listRequest), ListResult.class);
            statusCode = HTTPClient.getResponseCode();
            statusString = HTTPClient.getResponseString();
            return listResult;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new Error500Internal( e.getMessage() );
        }
    }

    public LoginResult login( LoginRequest loginRequest ) throws Error500Internal {
        try {
            LoginResult loginResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.LOGIN, "/session", "POST", null, loginRequest), LoginResult.class);
            statusCode = HTTPClient.getResponseCode();
            statusString = HTTPClient.getResponseString();
            return loginResult;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new Error500Internal( e.getMessage() );
        }
    }

    public LogoutResult logout( LogoutRequest logoutRequest ) throws Error500Internal {
        try {
            LogoutResult logoutResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.LOGOUT, "/session", "DELETE", logoutRequest.authToken(), logoutRequest), LogoutResult.class);
            statusCode = HTTPClient.getResponseCode();
            statusString = HTTPClient.getResponseString();
            return logoutResult;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new Error500Internal( e.getMessage() );
        }
    }

    public RegisterResult register( RegisterRequest registerRequest ) throws Error500Internal {
        try {
            RegisterResult registerResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.REGISTER, "/user", "POST", null, registerRequest), RegisterResult.class);
            statusCode = HTTPClient.getResponseCode();
            statusString = HTTPClient.getResponseString();
            return registerResult;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new Error500Internal( e.getMessage() );
        }
    }


}
