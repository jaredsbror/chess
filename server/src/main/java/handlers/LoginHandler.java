package handlers;


import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.exceptions.Error401Unauthorized;
import model.custom.LoginRequest;
import model.custom.LoginResult;
import model.custom.LogoutResult;
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
            return gson.toJson(new LogoutResult(null, new Error401Unauthorized().getMessage()));
        }

        try {
            loginService = new LoginService(loginRequest);
            response.status(200);
            return gson.toJson(new LoginResult(null, null, loginRequest.username(), loginService.login()));
        } catch (Error401Unauthorized exception) {
            response.status(401);
            return gson.toJson(new LoginResult(null, exception.getMessage(), null, null));
        } catch ( DataAccessException exception) {
            response.status(500);
            return gson.toJson(new LoginResult(null, exception.getMessage(), null, null));
        }
    }
}
