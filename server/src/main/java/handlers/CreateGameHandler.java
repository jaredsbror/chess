package handlers;

import com.google.gson.Gson;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.exceptions.Error500Internal;
import model.custom.CreateRequest;
import model.custom.CreateResult;
import service.CreateGameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
    private CreateGameService createGameService;

    @Override
    public Object handle(Request request, Response response) {
        // Process the http .json input into an object
        Gson gson = new Gson();
        CreateRequest draftCreateRequest = gson.fromJson(request.body(), CreateRequest.class);
        CreateRequest createRequest = new CreateRequest(request.headers("authorization"), draftCreateRequest.gameName());
        // If any of the request head or body parameters are null, return Error Message 401
        if (createRequest.gameName() == null) {
            response.status(400);
            return gson.toJson(new CreateResult(null, new Error400BadRequest().getMessage(), null));
        }  else if (createRequest.authToken() == null) {
            response.status(401);
            return gson.toJson(new CreateResult(null, new Error401Unauthorized().getMessage(), null));
        }
        createGameService = new CreateGameService(createRequest);

        try {
            response.status(200);
            return gson.toJson(new CreateResult(null, null, createGameService.createGame()));
        } catch (Error400BadRequest exception) {
            response.status(400);
            return gson.toJson(new CreateResult(null, exception.getMessage(), null));
        } catch (Error401Unauthorized exception) {
            response.status(401);
            return gson.toJson(new CreateResult(null, exception.getMessage(), null));
        } catch (Error500Internal exception) {
            response.status(500);
            return gson.toJson(new CreateResult(null, exception.getMessage(), null));
        }
    }
}
