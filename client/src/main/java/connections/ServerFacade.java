package connections;


import com.google.gson.Gson;
import dataaccess.exceptions.Error500Internal;
import handlers.ClearApplicationHandler;
import model.custom.*;
import server.Server;

import static connections.HTTPClient.sendRequest;
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
        return submitRequest( "/db", "DELETE", null, null, ClearResult.class );
    }

    public CreateResult createGame( CreateRequest createRequest) throws Error500Internal {
        return submitRequest("/game", "POST", createRequest.authToken(), createRequest, CreateResult.class);
    }

    public JoinResult joinGame( JoinRequest joinRequest ) throws Error500Internal {
        return submitRequest("/game", "PUT", joinRequest.authToken(), joinRequest, JoinResult.class);
    }

    public ListResult listGames( ListRequest listRequest ) throws Error500Internal {
        return submitRequest("/game", "GET", listRequest.authToken(), null, ListResult.class);
    }

    public LoginResult login( LoginRequest loginRequest ) throws Error500Internal {
        return submitRequest("/session", "POST", null, loginRequest, LoginResult.class);
    }

    public LogoutResult logout( LogoutRequest logoutRequest ) throws Error500Internal {
        return submitRequest("/session", "DELETE", logoutRequest.authToken(), null, LogoutResult.class);
    }

    public RegisterResult register( RegisterRequest registerRequest ) throws Error500Internal {
        return submitRequest("/user", "POST", null, registerRequest, RegisterResult.class);
    }


}
