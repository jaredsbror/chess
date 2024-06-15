package handlers;


import com.google.gson.Gson;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error403AlreadyTaken;
import model.custom.RegisterRequest;
import model.custom.RegisterResult;
import service.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;


public class RegisterHandler implements Route {
    private RegisterService registerService;


    @Override
    public Object handle( Request request, Response response ) {
        // Process the http .json input into an object
        Gson gson = new Gson();
        RegisterRequest registerRequest = gson.fromJson( request.body(), RegisterRequest.class );
        // Verify that there are no null or empty fields in the registerRequest
        if ( registerRequest == null || registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null ) {
            response.status( 400 );
            return gson.toJson( new RegisterResult( null, new Error400BadRequest().getMessage(), null, null ) );
        } else if ( registerRequest.username().isEmpty() || registerRequest.password().isEmpty() || registerRequest.email().isEmpty() ) {
            response.status( 400 );
            return gson.toJson( new RegisterResult( null, new Error400BadRequest().getMessage(), null, null ) );
        }

        try {
            registerService = new RegisterService( registerRequest );
            response.status( 200 );
            return gson.toJson( new RegisterResult( null, null, registerRequest.username(), registerService.register() ) );
        } catch ( Error400BadRequest exception ) {
            response.status( 400 );
            return gson.toJson( new RegisterResult( null, exception.getMessage(), null, null ) );
        } catch ( Error403AlreadyTaken exception ) {
            response.status( 403 );
            return gson.toJson( new RegisterResult( null, exception.getMessage(), null, null ) );
        } catch ( DataAccessException exception ) {
            response.status( 500 );
            return gson.toJson( new RegisterResult( null, exception.getMessage(), null, null ) );
        }
    }
}

