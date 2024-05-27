package service;

import dataAccess.auth.MemoryAuthDAO;
import dataAccess.exceptions.FailureResponse400;
import dataAccess.exceptions.FailureResponse401;
import dataAccess.exceptions.FailureResponse403;
import dataAccess.exceptions.FailureResponse500;
import dataAccess.game.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import model.JoinRequest;

import java.util.Objects;

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
        if (Objects.equals(playerColor, "WHITE") || Objects.equals(playerColor, "white")) {
            if (gameData.whiteUsername() != null) throw new FailureResponse403();
        } else if (Objects.equals(playerColor, "BLACK") || Objects.equals(playerColor, "black")){
            if (gameData.blackUsername() != null) throw new FailureResponse403();
        } else {
            throw new FailureResponse400();
        }
        // Update the game with new username on corresponding color team
        memoryGameDAO.updateGame(gameID, authData.username(), playerColor);
    }

}
