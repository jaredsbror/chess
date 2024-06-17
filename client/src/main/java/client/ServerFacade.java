package client;


import com.google.gson.Gson;
import connections.HTTPClient;
import connections.WSClient;
import datatypes.ServerMessageObserver;
import model.custom.*;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.net.URISyntaxException;



public class ServerFacade {
    private final HTTPClient httpClient;
    private WSClient wsClient;
    private int statusCode;
    private String statusString;
    private final Gson gson = new Gson();

    public ServerFacade(int port) throws Exception {
        httpClient = new HTTPClient(port);
    }

    public ServerFacade(int port,  ServerMessageObserver serverMessageObserver ) throws Exception {
        httpClient = new HTTPClient(port);
        wsClient = new WSClient( serverMessageObserver );
    }

    public ClearResult clearApplication() throws IOException, URISyntaxException {
        ClearResult clearResult = gson.fromJson( httpClient.submitRequest( HTTPClient.HttpRequestType.CLEAR_APPLICATION, "/db", "DELETE", null, null), ClearResult.class);
        statusCode = httpClient.getResponseCode();
        statusString = httpClient.getResponseString();
        return clearResult;
    }

    public CreateResult createGame( CreateRequest createRequest) throws IOException, URISyntaxException {
        CreateResult createResult = gson.fromJson( httpClient.submitRequest( HTTPClient.HttpRequestType.CREATE_GAME, "/game", "POST", createRequest.authToken(), createRequest), CreateResult.class);
        statusCode = httpClient.getResponseCode();
        statusString = httpClient.getResponseString();
        return createResult;
    }

    public JoinResult joinGame( JoinRequest joinRequest ) throws IOException, URISyntaxException {
        JoinResult joinResult = gson.fromJson( httpClient.submitRequest( HTTPClient.HttpRequestType.JOIN_GAME, "/game", "PUT", joinRequest.authToken(), joinRequest), JoinResult.class);
        statusCode = httpClient.getResponseCode();
        statusString = httpClient.getResponseString();
        return joinResult;
    }

    public ListResult listGames( ListRequest listRequest ) throws IOException, URISyntaxException {
        ListResult listResult = gson.fromJson( httpClient.submitRequest( HTTPClient.HttpRequestType.LIST_GAMES, "/game", "GET", listRequest.authToken(), listRequest), ListResult.class);
        statusCode = httpClient.getResponseCode();
        statusString = httpClient.getResponseString();
        return listResult;
    }

    public LoginResult login( LoginRequest loginRequest ) throws IOException, URISyntaxException {
        LoginResult loginResult = gson.fromJson( httpClient.submitRequest( HTTPClient.HttpRequestType.LOGIN, "/session", "POST", null, loginRequest), LoginResult.class);
        statusCode = httpClient.getResponseCode();
        statusString = httpClient.getResponseString();
        return loginResult;
    }

    public LogoutResult logout( LogoutRequest logoutRequest ) throws IOException, URISyntaxException {
        LogoutResult logoutResult = gson.fromJson( httpClient.submitRequest( HTTPClient.HttpRequestType.LOGOUT, "/session", "DELETE", logoutRequest.authToken(), logoutRequest), LogoutResult.class);
        statusCode = httpClient.getResponseCode();
        statusString = httpClient.getResponseString();
        return logoutResult;
    }

    public RegisterResult register( RegisterRequest registerRequest ) throws IOException, URISyntaxException {
        RegisterResult registerResult = gson.fromJson( httpClient.submitRequest( HTTPClient.HttpRequestType.REGISTER, "/user", "POST", null, registerRequest), RegisterResult.class);
        statusCode = httpClient.getResponseCode();
        statusString = httpClient.getResponseString();
        return registerResult;
    }


}
