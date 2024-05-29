package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.exceptions.FailureResponse401;
import dataaccess.exceptions.FailureResponse500;
import dataaccess.game.MemoryGameDAO;
import model.original.GameData;
import model.custom.ListRequest;

import java.util.ArrayList;

public class ListGamesService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private String authToken;

    public ListGamesService(ListRequest listRequest) {
        authToken = listRequest.authToken();
    }

    public ArrayList<GameData> getGameList() throws FailureResponse401, FailureResponse500 {
        // Verify that the authToken already exists
        if (!memoryAuthDAO.verifyAuthToken(authToken)) throw new FailureResponse401();
        // Return the list of games
        return memoryGameDAO.getGameArrayList();
    }
}
