package service;

import chess.ChessGame;
import dataAccess.auth.MemoryAuthDAO;
import dataAccess.exceptions.FailureResponse400;
import dataAccess.exceptions.FailureResponse401;
import dataAccess.exceptions.FailureResponse500;
import dataAccess.game.MemoryGameDAO;
import dataAccess.user.MemoryUserDAO;
import model.CreateRequest;
import model.GameData;
import model.LoginRequest;
import model.UserData;

import java.util.Objects;
import java.util.UUID;

public class CreateGameService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private final String authToken;
    private final String gameName;

    public CreateGameService(CreateRequest createRequest) {
        authToken = createRequest.authToken();
        gameName = createRequest.gameName();
    }

    public int createGame() throws FailureResponse400, FailureResponse401, FailureResponse500 {
        // Verify that the user exists
        if (!memoryAuthDAO.verifyUser(authToken)) throw new FailureResponse401();
        // Insert a new game
        return memoryGameDAO.insertGame(gameName);
    }

}
