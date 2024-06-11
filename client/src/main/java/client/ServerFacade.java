package client;


import com.google.gson.Gson;
import connections.HTTPClient;
import model.custom.*;

import java.io.IOException;
import java.net.URISyntaxException;

import static connections.HTTPClient.submitRequest;


public class ServerFacade {
    private int statusCode;
    private String statusString;
    private final Gson gson = new Gson();

    public ClearResult clearApplication() throws IOException, URISyntaxException {
        ClearResult clearResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.CLEAR_APPLICATION, "/db", "DELETE", null, null), ClearResult.class);
        statusCode = HTTPClient.getResponseCode();
        statusString = HTTPClient.getResponseString();
        return clearResult;
    }

    public CreateResult createGame( CreateRequest createRequest) throws IOException, URISyntaxException {
        CreateResult createResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.CREATE_GAME, "/game", "POST", createRequest.authToken(), createRequest), CreateResult.class);
        statusCode = HTTPClient.getResponseCode();
        statusString = HTTPClient.getResponseString();
        return createResult;
    }

    public JoinResult joinGame( JoinRequest joinRequest ) throws IOException, URISyntaxException {
        JoinResult joinResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.JOIN_GAME, "/game", "PUT", joinRequest.authToken(), joinRequest), JoinResult.class);
        statusCode = HTTPClient.getResponseCode();
        statusString = HTTPClient.getResponseString();
        return joinResult;
    }

    public ListResult listGames( ListRequest listRequest ) throws IOException, URISyntaxException {
        ListResult listResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.LIST_GAMES, "/game", "GET", listRequest.authToken(), listRequest), ListResult.class);
        statusCode = HTTPClient.getResponseCode();
        statusString = HTTPClient.getResponseString();
        return listResult;
    }

    public LoginResult login( LoginRequest loginRequest ) throws IOException, URISyntaxException {
        LoginResult loginResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.LOGIN, "/session", "POST", null, loginRequest), LoginResult.class);
        statusCode = HTTPClient.getResponseCode();
        statusString = HTTPClient.getResponseString();
        return loginResult;
    }

    public LogoutResult logout( LogoutRequest logoutRequest ) throws IOException, URISyntaxException {
        LogoutResult logoutResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.LOGOUT, "/session", "DELETE", logoutRequest.authToken(), logoutRequest), LogoutResult.class);
        statusCode = HTTPClient.getResponseCode();
        statusString = HTTPClient.getResponseString();
        return logoutResult;
    }

    public RegisterResult register( RegisterRequest registerRequest ) throws IOException, URISyntaxException {
        RegisterResult registerResult = gson.fromJson( submitRequest( HTTPClient.HttpRequestType.REGISTER, "/user", "POST", null, registerRequest), RegisterResult.class);
        statusCode = HTTPClient.getResponseCode();
        statusString = HTTPClient.getResponseString();
        return registerResult;
    }


}
