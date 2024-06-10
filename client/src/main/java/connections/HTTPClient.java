package connections;


import com.google.gson.Gson;
import dataaccess.exceptions.Error500Internal;
import model.custom.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static connections.HTTPClient.HttpRequest.*;


public class HTTPClient {
    private static HttpURLConnection httpURLConnection;
    private final static String baseURL = "http://localhost:8080";
    private static Map<String, String> body = new HashMap<>();
    private static Integer responseCode = null;
    private static String responseString = null;


    public static int getResponseCode() {
        return responseCode;
    }

    public static String getResponseString() {
        return responseString;
    }

    public enum HttpRequestType {
        CLEAR_APPLICATION,
        CREATE_GAME,
        JOIN_GAME,
        LIST_GAMES,
        LOGIN,
        LOGOUT,
        REGISTER
    }

    public static String submitRequest(HttpRequestType request, String path, String method, String authToken, Object requestObject) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI(baseURL + path);
        httpURLConnection = (HttpURLConnection) uri.toURL().openConnection();
        // Specify that we are going to write out data
        httpURLConnection.setDoOutput(true);
        // Set the request method
        httpURLConnection.setRequestMethod( method );
        // Write out a header
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        if (authToken != null) httpURLConnection.setRequestProperty("Authorization", authToken);

        // Write out the body
        switch ( request ) {
            case CREATE_GAME: // HEADER, BODY
                // Parse the requestObject
                CreateRequest createRequest = (CreateRequest) requestObject;
                if (createRequest == null) throw new Error500Internal( "Error: Null CreateRequest in submitRequest()" );
                // Create the body
                body = Map.of("gameName", createRequest.gameName());
                addBodyToHTTPRequest();
                break;
            case JOIN_GAME: // HEADER, BODY
                // Parse the requestObject
                JoinRequest joinRequest = ( JoinRequest ) requestObject;
                if (joinRequest == null) throw new Error500Internal( "Error: Null JoinRequest in submitRequest()" );
                // Create the body
                body = Map.of("playerColor", joinRequest.playerColor(), "gameID", joinRequest.gameID().toString()); //???
                addBodyToHTTPRequest();
                break;
            case LOGIN: // NO HEADER, BODY
                // Parse the requestObject
                LoginRequest loginRequest = (LoginRequest) requestObject;
                if (loginRequest == null) throw new Error500Internal( "Error: Null LoginRequest in submitRequest()" );
                // Create the body
                body = Map.of("username", loginRequest.username(), "password", loginRequest.password());
                addBodyToHTTPRequest();
                break;
            case REGISTER: // NO HEADER, BODY
                // Parse the requestObject
                RegisterRequest registerRequest = ( RegisterRequest ) requestObject;
                if (registerRequest == null) throw new Error500Internal( "Error: Null RegisterRequest in submitRequest()" );
                // Create the body
                body = Map.of("username", registerRequest.username(), "password", registerRequest.password(), "email", registerRequest.email());
                addBodyToHTTPRequest();
                break;
            default:
                break;
        }
        // Make the request
        httpURLConnection.connect();

        // Get the response code
        responseCode = httpURLConnection.getResponseCode();
        // Get the response body
        try ( InputStream respBody = httpURLConnection.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            System.out.println(new Gson().fromJson(inputStreamReader, Map.class));
            responseString = inputStreamReader.toString();
            return responseString;
        }
    }

    private static void addBodyToHTTPRequest() throws IOException {
        // Add the body to the httpURLConnection
        try (var outputStream = httpURLConnection.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }
    }
}
