package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.exceptions.Error500Internal;
import model.custom.ClearResult;
import service.ClearApplicationService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearApplicationHandler implements Route {

    private final ClearApplicationService clearApplicationService;

    public ClearApplicationHandler() throws Error500Internal {
        clearApplicationService = new ClearApplicationService();
    }

    @Override
    public Object handle(Request request, Response response) {
        Gson gson = new Gson();

        // Attempt to clear the database.
        // Otherwise, return an error.
        try {
            clearApplicationService.clearDatabase();
            return "{}";
        } catch (DataAccessException exception) {
            response.status(500);
            return gson.toJson(new ClearResult("Error: " + exception));
        }
    }
}
