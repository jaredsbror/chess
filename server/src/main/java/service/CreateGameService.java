package service;

import dataAccess.auth.MemoryAuthDAO;
import dataAccess.exceptions.FailureResponse400;
import dataAccess.exceptions.FailureResponse401;
import dataAccess.exceptions.FailureResponse500;
import dataAccess.game.MemoryGameDAO;
import model.CreateRequest;

public class CreateGameService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private String authToken;
    private String gameName;

    public CreateGameService(CreateRequest createRequest) {
        authToken = createRequest.authToken();
        gameName = createRequest.gameName();
    }

    public int createGame() throws FailureResponse400, FailureResponse401, FailureResponse500 {
        // Verify that the user exists
        if (!memoryAuthDAO.verifyAuthToken(authToken)) {
            throw new FailureResponse401();
        }
        // Insert a new game
        return memoryGameDAO.insertGame(gameName);
    }

}
