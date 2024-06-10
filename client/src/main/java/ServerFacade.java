import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import dataaccess.exceptions.Error500Internal;
import model.custom.*;
import passoff.exception.EndpointNotFoundException;
import passoff.exception.ResponseParseException;
import server.Server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class ServerFacade {

    private static Server server;
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
        var port = server.run(3306);
        System.out.println("Started test HTTP server on " + port);
    }

    public void stopServer() {
        server.stop();
    }

    public ClearResult clearApplication() throws Error500Internal {
        return sendRequest( "/db", "UPDATE", null, null, ClearResult.class );
    }

    public CreateResult createGame( CreateRequest createRequest) throws Error500Internal {
        return sendRequest("/game", "POST", createRequest.authToken(), createRequest, CreateResult.class);
    }

    public JoinResult joinGame( JoinRequest joinRequest ) throws Error500Internal {
        return sendRequest("/game", "PUT", joinRequest.authToken(), joinRequest, JoinResult.class);
    }

    public ListResult listGames( ListRequest listRequest ) throws Error500Internal {
        return sendRequest("/game", "GET", listRequest.authToken(), null, ListResult.class);
    }

    public LoginResult login( LoginRequest loginRequest ) throws Error500Internal {
        return sendRequest("/session", "POST", null, loginRequest, LoginResult.class);
    }

    public LogoutResult logout( LogoutRequest logoutRequest ) throws Error500Internal {
        return sendRequest("/session", "DELETE", logoutRequest.authToken(), null, LogoutResult.class);
    }

    public RegisterResult register( RegisterRequest registerRequest ) throws Error500Internal {
        return sendRequest("/user", "POST", null, registerRequest, RegisterResult.class);
    }

    // Read the server response
    private String readResponse(HttpURLConnection connection) throws IOException {
        InputStream inputStream;
        if (this.statusCode / 100 == 2) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }

        StringBuilder responseBuilder = new StringBuilder();
        try ( InputStreamReader reader = new InputStreamReader(inputStream)
              ) {
            char[] buffer = new char[1024];
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) > 0) {
                responseBuilder.append(buffer, 0, bytesRead);
            }
        }
        return responseBuilder.toString();
    }

    // Handle the server response
    private <T> T handleResponse(HttpURLConnection connection, String endpoint, String method, Class<T> responseClass) throws IOException {
        this.statusCode = connection.getResponseCode();
        if (this.statusCode != 404 && this.statusCode != 405) {
            String response = readResponse(connection);
            if (response.isBlank() || response.equalsIgnoreCase("null")) {
                response = "{}";
            }

            try {
                return this.gson.fromJson(response, responseClass);
            } catch ( JsonParseException e) {
                throw new ResponseParseException(String.format("%s %s: Error parsing response. Expected JSON, got %s", method, endpoint, response), e);
            }
        } else {
            throw new EndpointNotFoundException(String.format("Endpoint %s %s not implemented or returned null", method, endpoint));
        }
    }

    // Send a server request
    private <T> T sendRequest(String endpoint, String method, String authToken, Object requestBody, Class<T> responseClass) throws Error500Internal {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(this.baseUrl + endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Accept", "application/json");
            if (authToken != null) {
                connection.setRequestProperty("Authorization", authToken);
            }

            if (requestBody != null) {
                connection.setDoOutput(true);
                try ( OutputStream outputStream = connection.getOutputStream();
                      OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
                    writer.write(this.gson.toJson(requestBody));
                    writer.flush();
                }
            }

            connection.connect();
            return handleResponse(connection, endpoint, method, responseClass);
        } catch (IOException e) {
            throw new Error500Internal(String.format("Failed to connect to server on %s %s", method, endpoint));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
