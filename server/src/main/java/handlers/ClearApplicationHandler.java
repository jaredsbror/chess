package handlers;

import com.google.gson.Gson;
import model.ClearResult;
import service.ClearApplicationService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearApplicationHandler implements Route {

    private ClearApplicationService clearApplicationService = new ClearApplicationService();

    @Override
    public Object handle(Request request, Response response) {
        Gson gson = new Gson();

        // Attempt to clear the database.
        // Otherwise, return an error.
        try {
            clearApplicationService.clearDatabase();
            return "{}";
        } catch (Exception exception) {
            response.status(500);
            exception.printStackTrace();
            return gson.toJson(new ClearResult("Error: " + exception));
        }
    }
}
