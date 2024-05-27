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
import model.ListRequest;

import java.util.ArrayList;
import java.util.Objects;

public class ListGamesService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private String authToken;

    public ListGamesService(ListRequest listRequest) {
        authToken = listRequest.authToken();
    }

    public ArrayList<GameData> getGameList(String authToken) throws FailureResponse401, FailureResponse500 {
        // Verify that the authToken already exists
        if (!memoryAuthDAO.verifyUser(authToken)) throw new FailureResponse401();
        // Return the list of games
        return memoryGameDAO.getGameArrayList();
    }
}
