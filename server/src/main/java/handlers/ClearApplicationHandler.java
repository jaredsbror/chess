package handlers;


import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.custom.ClearResult;
import service.ClearApplicationService;
import spark.Request;
import spark.Response;
import spark.Route;


public class ClearApplicationHandler implements Route {
    private ClearApplicationService clearApplicationService;

    @Override
    public Object handle( Request request, Response response ) {
        Gson gson = new Gson();
        // Attempt to clear the database.
        // Otherwise, return an error.
        try {
            clearApplicationService = new ClearApplicationService();
            clearApplicationService.clearDatabase();
            return "{}";
        } catch ( DataAccessException exception ) {
            response.status( 500 );
            return gson.toJson( new ClearResult( false, "Error: " + exception ) );
        }
    }
}
