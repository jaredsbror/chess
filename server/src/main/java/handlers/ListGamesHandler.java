package handlers;


import com.google.gson.Gson;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.Error401Unauthorized;
import model.custom.JoinResult;
import model.custom.ListRequest;
import model.custom.ListResult;
import service.ListGamesService;
import spark.Request;
import spark.Response;
import spark.Route;


public class ListGamesHandler implements Route {
    private ListGamesService listGamesService;


    @Override
    public Object handle( Request request, Response response ) {
        // Process the http .json input into an object
        Gson gson = new Gson();
        ListRequest listRequest = new ListRequest( request.headers( "authorization" ) );
        // Verify that authToken is not missing (401)
        if ( listRequest.authToken() == null ) {
            response.status( 401 );
            return gson.toJson( new ListResult( null, new Error401Unauthorized().getMessage(), null ) );
        }

        // Return the .json array with GameData
        try {
            listGamesService = new ListGamesService( listRequest );
            response.status( 200 );
            return gson.toJson( new ListResult( null, null, listGamesService.getGameList() ) );
        } catch ( Error401Unauthorized exception ) {
            response.status( 401 );
            return gson.toJson( new JoinResult( null, exception.getMessage() ) );
        } catch ( DataAccessException exception ) {
            response.status( 500 );
            return gson.toJson( new JoinResult( null, exception.getMessage() ) );
        }
    }
}
