package service;


import dataaccess.DataAccessException;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.exceptions.Error403AlreadyTaken;
import dataaccess.game.SQLGameDAO;
import model.custom.JoinRequest;
import model.original.AuthData;
import model.original.GameData;


public class JoinGameService {
    private final SQLAuthDAO sqlAuthDAO;
    private final SQLGameDAO sqlGameDAO = new SQLGameDAO();
    private final String authToken;
    private final String playerColor;
    private final int gameID;


    public JoinGameService( JoinRequest joinRequest ) throws DataAccessException {
        authToken = joinRequest.authToken();
        playerColor = joinRequest.playerColor();
        gameID = joinRequest.gameID();
        sqlAuthDAO = new SQLAuthDAO();
    }


    public void joinGame() throws Error400BadRequest, Error401Unauthorized, Error403AlreadyTaken, DataAccessException {
        // Verify that there exists a corresponding AuthData object
        AuthData authData = sqlAuthDAO.getAuthDataGivenAuthToken( authToken );
        if ( authData == null ) throw new Error401Unauthorized();
        // Verify that there exists a corresponding GameData object
        GameData gameData = sqlGameDAO.getGameData( gameID );
        if ( gameData == null ) throw new Error400BadRequest();
        // Verify that the playerColor is not already taken
        if ( playerColor.equalsIgnoreCase( "white" ) ) {
            if ( gameData.whiteUsername() != null ) throw new Error403AlreadyTaken();
        } else if ( playerColor.equalsIgnoreCase( "black" ) ) {
            if ( gameData.blackUsername() != null ) throw new Error403AlreadyTaken();
        } else {
            throw new Error400BadRequest();
        }
        // Update the game with new username on corresponding color team
        sqlGameDAO.updateGame( gameID, authData.username(), playerColor );
    }

}
