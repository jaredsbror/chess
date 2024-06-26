package handlers;


import com.google.gson.Gson;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.Error401Unauthorized;
import model.custom.LogoutRequest;
import model.custom.LogoutResult;
import service.LogoutService;
import spark.Request;
import spark.Response;
import spark.Route;


public class LogoutHandler implements Route {
    private LogoutService logoutService;


    @Override
    public Object handle( Request request, Response response ) {
        // Process the http .json input into an object
        Gson gson = new Gson();
        LogoutRequest logoutRequest = new LogoutRequest( request.headers( "authorization" ) );
        // If any of the request body parameters are null or empty, return Error Message 401
        if ( logoutRequest.authToken() == null ) {
            response.status( 401 );
            return gson.toJson( new LogoutResult( null, new Error401Unauthorized().getMessage() ) );
        } else if ( logoutRequest.authToken().isEmpty() ) {
            response.status( 401 );
            return gson.toJson( new LogoutResult( null, new Error401Unauthorized().getMessage() ) );
        }

        try {
            logoutService = new LogoutService( logoutRequest );
            response.status( 200 );
            logoutService.logout();
            return gson.toJson( new LogoutResult( null, null ) );
        } catch ( Error401Unauthorized exception ) {
            response.status( 401 );
            return gson.toJson( new LogoutResult( null, exception.getMessage() ) );
        } catch ( DataAccessException exception ) {
            response.status( 500 );
            return gson.toJson( new LogoutResult( null, exception.getMessage() ) );
        }
    }
}
