package handlers;


import com.google.gson.Gson;
import dataaccess.exceptions.DataAccessException;
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
            return gson.toJson( new ClearResult( null, null ));
        } catch ( DataAccessException exception ) {
            response.status( 500 );
            return gson.toJson( new ClearResult(null, "Error: " + exception ) );
        }
    }

    // Method to clear the database
    public void clearDatabase() throws DataAccessException {
        clearApplicationService = new ClearApplicationService();
        clearApplicationService.clearDatabase();
    }
}
