package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.exceptions.FailureResponse400;
import dataaccess.exceptions.FailureResponse401;
import dataaccess.exceptions.FailureResponse403;
import dataaccess.exceptions.FailureResponse500;
import dataaccess.game.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import model.JoinRequest;

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

    public void joinGame() throws FailureResponse400, FailureResponse401, FailureResponse403, FailureResponse500 {
        // Verify that there exists a corresponding AuthData object
        AuthData authData = memoryAuthDAO.getAuthDataGivenAuthToken(authToken);
        if (authData == null) throw new FailureResponse401();
        // Verify that there exists a corresponding GameData object
        GameData gameData = memoryGameDAO.getGameData(gameID);
        if (gameData == null) throw new FailureResponse400();
        // Verify that the playerColor is not already taken
        if (playerColor.equalsIgnoreCase("white")) {
            if (gameData.whiteUsername() != null) throw new FailureResponse403();
        } else if (playerColor.equalsIgnoreCase("black")){
            if (gameData.blackUsername() != null) throw new FailureResponse403();
        } else {
            throw new FailureResponse400();
        }
        // Update the game with new username on corresponding color team
        memoryGameDAO.updateGame(gameID, authData.username(), playerColor);
    }

}
