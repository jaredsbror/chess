package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.exceptions.Error500Internal;
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

    public int createGame() throws Error400BadRequest, Error401Unauthorized, Error500Internal {
        // Verify that the user exists
        if (!memoryAuthDAO.verifyAuthToken(authToken)) {
            throw new Error401Unauthorized();
        }
        // Insert a new game
        return memoryGameDAO.insertGame(gameName);
    }

}
