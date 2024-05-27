package handlers;

import com.google.gson.Gson;
import dataAccess.exceptions.FailureResponse401;
import dataAccess.exceptions.FailureResponse500;
import model.LogoutRequest;
import model.LogoutResult;
import service.LogoutService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    private LogoutService logoutService;

    @Override
    public Object handle(Request request, Response response) {
        // Process the http .json input into an object
        Gson gson = new Gson();
        LogoutRequest logoutRequest = gson.fromJson(request.body(), LogoutRequest.class);
        // If any of the request body parameters are null, return Error Message 401
        if (logoutRequest == null) {
            response.status(401);
            return gson.toJson(new LogoutResult(null, new FailureResponse401().getMessage()));
        }
        logoutService = new LogoutService(logoutRequest);

        try {
            response.status(200);
            logoutService.logout();
            return gson.toJson(new LogoutResult(null, null));
        } catch (FailureResponse401 exception) {
            response.status(401);
            exception.printStackTrace();
            return gson.toJson(new LogoutResult(null, exception.getMessage()));
        } catch (FailureResponse500 exception) {
            response.status(500);
            exception.printStackTrace();
            return gson.toJson(new LogoutResult(null, exception.getMessage()));
        }
    }
}
