package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.exceptions.FailureResponse400;
import dataaccess.exceptions.FailureResponse401;
import dataaccess.exceptions.FailureResponse500;
import dataaccess.game.MemoryGameDAO;
import model.custom.CreateRequest;

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
