package handlers;


import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.exceptions.Error403AlreadyTaken;
import model.custom.JoinRequest;
import model.custom.JoinResult;
import service.JoinGameService;
import spark.Request;
import spark.Response;
import spark.Route;


public class JoinGameHandler implements Route {
    JoinGameService joinGameService;


    @Override
    public Object handle( Request request, Response response ) {
        // Process the http .json input into an object
        Gson gson = new Gson();
        JoinRequest draftJoinRequest = gson.fromJson( request.body(), JoinRequest.class );
        JoinRequest finalJoinRequest = new JoinRequest( request.headers( "authorization" ), draftJoinRequest.playerColor(), draftJoinRequest.gameID() );
        // Verify that playerColor or gameID are not missing (400)
        if ( finalJoinRequest.playerColor() == null || finalJoinRequest.gameID() == null ) {
            response.status( 400 );
            return gson.toJson( new JoinResult( null, new Error400BadRequest().getMessage() ) );
        }
        // Verify that the authToken is not missing (401)
        else if ( finalJoinRequest.authToken() == null ) {
            response.status( 401 );
            return gson.toJson( new JoinResult( null, new Error401Unauthorized().getMessage() ) );
        }

        try {
            // Create a new joinGameService
            joinGameService = new JoinGameService( finalJoinRequest );
            joinGameService.joinGame();
            response.status( 200 );
            return gson.toJson( new JoinResult( null, null ) );
        } catch ( Error400BadRequest exception ) {
            response.status( 400 );
            return gson.toJson( new JoinResult( null, exception.getMessage() ) );
        } catch ( Error401Unauthorized exception ) {
            response.status( 401 );
            return gson.toJson( new JoinResult( null, exception.getMessage() ) );
        } catch ( Error403AlreadyTaken exception ) {
            response.status( 403 );
            return gson.toJson( new JoinResult( null, exception.getMessage() ) );
        } catch ( DataAccessException exception ) {
            response.status( 500 );
            return gson.toJson( new JoinResult( null, exception.getMessage() ) );
        }

    }
}
