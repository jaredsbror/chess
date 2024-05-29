package handlers;

import com.google.gson.Gson;
import dataaccess.exceptions.*;
import model.custom.RegisterRequest;
import model.custom.RegisterResult;
import service.RegisterService;
import spark.*;

public class RegisterHandler implements Route {
    private RegisterService registerService;

    @Override
    public Object handle(Request request, Response response) {
        // Process the http .json input into an object
        Gson gson = new Gson();
        RegisterRequest registerRequest = gson.fromJson(request.body(), RegisterRequest.class);
        // Verify that there are no null or empty fields in the registerRequest
        if (registerRequest == null || registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null) {
            response.status(400);
            return gson.toJson(new RegisterResult(null, new Error400BadRequest().getMessage(), null, null));
        } else if (registerRequest.username().isEmpty() || registerRequest.password().isEmpty() || registerRequest.email().isEmpty()) {
            response.status(400);
            return gson.toJson(new RegisterResult(null, new Error400BadRequest().getMessage(), null, null));
        }
        registerService = new RegisterService(registerRequest);

        try {
            response.status(200);
            return gson.toJson(new RegisterResult(null, null, registerRequest.username(), registerService.register()));
        } catch (Error400BadRequest exception) {
            response.status(400);
            exception.printStackTrace();
            return gson.toJson(new RegisterResult(null, exception.getMessage(), null, null));
        } catch (Error403AlreadyTaken exception) {
            response.status(403);
            exception.printStackTrace();
            return gson.toJson(new RegisterResult(null, exception.getMessage(), null, null));
        } catch (Error500Internal exception) {
            response.status(500);
            exception.printStackTrace();
            return gson.toJson(new RegisterResult(null, exception.getMessage(), null, null));
        }
    }
}

