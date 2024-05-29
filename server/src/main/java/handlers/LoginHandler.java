package handlers;

import com.google.gson.Gson;
import dataaccess.exceptions.FailureResponse401;
import dataaccess.exceptions.FailureResponse500;
import model.*;
import service.LoginService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
    private LoginService loginService;

    @Override
    public Object handle(Request request, Response response) {
        // Process the http .json input into an object
        Gson gson = new Gson();
        LoginRequest loginRequest = gson.fromJson(request.body(), LoginRequest.class);
        // If any of the request body parameters are null, return Error Message 401
        if (loginRequest == null || loginRequest.username() == null || loginRequest.password() == null) {
            response.status(401);
            return gson.toJson(new LogoutResult(null, new FailureResponse401().getMessage()));
        }
        loginService = new LoginService(loginRequest);

        try {
            response.status(200);
            return gson.toJson(new LoginResult(null, null, loginRequest.username(), loginService.login()));
        } catch (FailureResponse401 exception) {
            response.status(401);
            exception.printStackTrace();
            return gson.toJson(new LoginResult(null, exception.getMessage(), null, null));
        } catch (FailureResponse500 exception) {
            response.status(500);
            exception.printStackTrace();
            return gson.toJson(new LoginResult(null, exception.getMessage(), null, null));
        }
    }
}
