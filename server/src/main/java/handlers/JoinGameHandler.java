package handlers;

import com.google.gson.Gson;
import dataaccess.exceptions.FailureResponse400;
import dataaccess.exceptions.FailureResponse401;
import dataaccess.exceptions.FailureResponse403;
import dataaccess.exceptions.FailureResponse500;
import model.custom.JoinRequest;
import model.custom.JoinResult;
import service.JoinGameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {
    JoinGameService joinGameService;

    @Override
    public Object handle(Request request, Response response) {
        // Process the http .json input into an object
        Gson gson = new Gson();
        JoinRequest draftJoinRequest = gson.fromJson(request.body(), JoinRequest.class);
        JoinRequest finalJoinRequest = new JoinRequest(request.headers("authorization"), draftJoinRequest.playerColor(), draftJoinRequest.gameID());
        // Verify that playerColor or gameID are not missing (400)
        if (finalJoinRequest.playerColor() == null || finalJoinRequest.gameID() == null) {
            response.status(400);
            return gson.toJson(new JoinResult(null, new FailureResponse400().getMessage()));
        }
        // Verify that the authToken is not missing (401)
        else if (finalJoinRequest.authToken() == null) {
            response.status(401);
            return gson.toJson(new JoinResult(null, new FailureResponse401().getMessage()));
        }
        // Create a new joinGameService
        joinGameService = new JoinGameService(finalJoinRequest);

        try {
            response.status(200);
            joinGameService.joinGame();
            return gson.toJson(new JoinResult(null, null));
        } catch (FailureResponse400 exception) {
            response.status(400);
            exception.printStackTrace();
            return gson.toJson(new JoinResult(null, exception.getMessage()));
        } catch (FailureResponse401 exception) {
            response.status(401);
            exception.printStackTrace();
            return gson.toJson(new JoinResult(null, exception.getMessage()));
        } catch (FailureResponse403 exception) {
            response.status(403);
            exception.printStackTrace();
            return gson.toJson(new JoinResult(null, exception.getMessage()));
        } catch (FailureResponse500 exception) {
            response.status(500);
            exception.printStackTrace();
            return gson.toJson(new JoinResult(null, exception.getMessage()));
        }

    }
}
