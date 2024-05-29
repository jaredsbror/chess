package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.exceptions.Error403AlreadyTaken;
import dataaccess.exceptions.Error500Internal;
import dataaccess.game.MemoryGameDAO;
import model.original.AuthData;
import model.original.GameData;
import model.custom.JoinRequest;

public class JoinGameService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private String authToken;
    private String playerColor;
    private int gameID;

    public JoinGameService(JoinRequest joinRequest) {
        authToken = joinRequest.authToken();
        playerColor = joinRequest.playerColor();
        gameID = joinRequest.gameID();
    }

    public void joinGame() throws Error400BadRequest, Error401Unauthorized, Error403AlreadyTaken, Error500Internal {
        // Verify that there exists a corresponding AuthData object
        AuthData authData = memoryAuthDAO.getAuthDataGivenAuthToken(authToken);
        if (authData == null) throw new Error401Unauthorized();
        // Verify that there exists a corresponding GameData object
        GameData gameData = memoryGameDAO.getGameData(gameID);
        if (gameData == null) throw new Error400BadRequest();
        // Verify that the playerColor is not already taken
        if (playerColor.equalsIgnoreCase("white")) {
            if (gameData.whiteUsername() != null) throw new Error403AlreadyTaken();
        } else if (playerColor.equalsIgnoreCase("black")){
            if (gameData.blackUsername() != null) throw new Error403AlreadyTaken();
        } else {
            throw new Error400BadRequest();
        }
        // Update the game with new username on corresponding color team
        memoryGameDAO.updateGame(gameID, authData.username(), playerColor);
    }

}
