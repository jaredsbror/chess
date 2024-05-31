package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.auth.SQLAuthDao;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.exceptions.Error500Internal;
import dataaccess.game.MemoryGameDAO;
import model.original.GameData;
import model.custom.ListRequest;

import java.util.ArrayList;

public class ListGamesService {
    private SQLAuthDao sqlAuthDao = new SQLAuthDao();
    private MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private String authToken;

    public ListGamesService(ListRequest listRequest) {
        authToken = listRequest.authToken();
    }

    public ArrayList<GameData> getGameList() throws Error401Unauthorized, Error500Internal {
        // Verify that the authToken already exists
        if (!sqlAuthDao.verifyAuthToken(authToken)) throw new Error401Unauthorized();
        // Return the list of games
        return memoryGameDAO.getGameArrayList();
    }
}
