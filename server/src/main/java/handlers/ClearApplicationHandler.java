package handlers;

import com.google.gson.Gson;
import model.ClearResult;
import service.ClearApplicationService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearApplicationHandler implements Route {

    ClearApplicationService clearApplicationService = new ClearApplicationService();

    @Override
    public Object handle(Request request, Response response) {
        Gson gson = new Gson();

        try {
            clearApplicationService.clearDatabase();
            return "{}";
        } catch (Exception exception) {
            response.status(500);
            return gson.toJson(new ClearResult("Error: ???"));
        }
    }
}
