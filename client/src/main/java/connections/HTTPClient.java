package connections;


import com.google.gson.Gson;
import model.custom.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static connections.HTTPClient.HttpRequest.*;


public class HTTPClient {
    private static HttpURLConnection httpURLConnection;
    private static String baseURL = "http://localhost:8080";
    private static String path = "";
    private static String method = "";
    private static Map<String, String> body;

    public enum HttpRequest {
        CLEAR_APPLICATION,
        CREATE_GAME,
        JOIN_GAME,
        LIST_GAMES,
        LOGIN,
        LOGOUT,
        REGISTER
    }

    public static String sendRequest(HttpRequest request, String reqPath, String reqMethod, String authToken, Object requestObject) throws Exception {
        // Specify that we are going to write out data
        httpURLConnection.setDoOutput(true);

        // Set the path
        path = reqPath;

        // Set the request method
        method = reqMethod;
        httpURLConnection.setRequestMethod( method );

        // Write out a header
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        if (authToken != null) httpURLConnection.setRequestProperty("Authorization", authToken);

        // Write out the body
        switch ( request ) {
            case CLEAR_APPLICATION: // NO HEADER, NO BODY
                break;
            case CREATE_GAME: // HEADER, BODY
                // Parse the requestObject
                CreateRequest createRequest = (CreateRequest) requestObject;
                // Create the body
                body = Map.of("gameName", createRequest.gameName());
                addBodyToHTTPRequest();
                break;
            case JOIN_GAME: // HEADER, BODY
                // Parse the requestObject
                JoinRequest joinRequest = ( JoinRequest ) requestObject;
                // Create the body
                body = Map.of();
                addBodyToHTTPRequest();
                break;
            case LIST_GAMES: // HEADER, NO BODY
                break;
            case LOGIN: // NO HEADER, BODY
                // Parse the requestObject
                LoginRequest loginRequest = (LoginRequest) requestObject;
                // Create the body
                body = Map.of();
                addBodyToHTTPRequest();
                break;
            case LOGOUT: // HEADER, NO BODY
                break;
            case REGISTER: // NO HEADER, BODY
                // Parse the requestObject
                RegisterRequest registerRequest = ( RegisterRequest ) requestObject;
                // Create the body
                body = Map.of();
                addBodyToHTTPRequest();
                break;
            default:
                break;
        }
        return processRequest();
    }

    private static void addBodyToHTTPRequest() throws IOException {
        // Add the body to the httpURLConnection
        try (var outputStream = httpURLConnection.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }
    }

    private static String processRequest() throws IOException, URISyntaxException {
        // Specify the desired endpoint
        URI uri = new URI(baseURL + path);
        httpURLConnection = (HttpURLConnection) uri.toURL().openConnection();

        // Make the request
        httpURLConnection.connect();

        // Output the response body
        try ( InputStream respBody = httpURLConnection.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            System.out.println(new Gson().fromJson(inputStreamReader, Map.class));
            return inputStreamReader.toString();
        }
    }
}
