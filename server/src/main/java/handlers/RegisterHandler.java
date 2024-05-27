package handlers;

import com.google.gson.Gson;
import dataAccess.exceptions.*;
import model.RegisterRequest;
import model.RegisterResult;
import service.RegisterService;
import spark.*;

public class RegisterHandler implements Route {
    private RegisterService registerService;

    @Override
    public Object handle(Request request, Response response) {
        // Process the http .json input into an object
        Gson gson = new Gson();
        RegisterRequest registerRequest = gson.fromJson(request.body(), RegisterRequest.class);
        registerService = new RegisterService(registerRequest);

        try {
            response.status(200);
            return gson.toJson(new RegisterResult(null, null, registerRequest.username(), registerService.register()));
        } catch (FailureResponse400 exception) {
            response.status(400);
            exception.printStackTrace();
            return gson.toJson(new RegisterResult(null, exception.getMessage(), null, null));
        } catch (FailureResponse403 exception) {
            response.status(403);
            exception.printStackTrace();
            return gson.toJson(new RegisterResult(null, exception.getMessage(), null, null));
        } catch (FailureResponse500 exception) {
            response.status(500);
            exception.printStackTrace();
            return gson.toJson(new RegisterResult(null, exception.getMessage(), null, null));
        }
    }
}

