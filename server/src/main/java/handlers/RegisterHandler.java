package handlers;

import com.google.gson.Gson;
import model.ClearResult;
import model.RegisterRequest;
import service.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class RegisterHandler implements Route {
    private RegisterService registerService;

    @Override
    public Object handle(Request request, Response response) {
        Gson gson = new Gson();
        RegisterRequest registerRequest = gson.fromJson(request.body(), RegisterRequest.class);
        registerService = new RegisterService(registerRequest);

        try {
            registerService.register();
        } catch (Exception exception) {
            response.status(500);
            exception.printStackTrace();
            return gson.toJson(new ClearResult("Error: " + exception));
            // Add custom exceptions probably
            // Add into service method
        }
        return "";
    }
}
