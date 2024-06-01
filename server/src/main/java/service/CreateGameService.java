package service;

import dataaccess.DataAccessException;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.game.MemoryGameDAO;
import model.custom.CreateRequest;

public class CreateGameService {
    private final SQLAuthDAO sqlAuthDao;
    private final MemoryGameDAO memoryGameDAO;
    private final String authToken;
    private final String gameName;

    public CreateGameService(CreateRequest createRequest) throws DataAccessException {
        sqlAuthDao = new SQLAuthDAO();
        memoryGameDAO = new MemoryGameDAO();
        authToken = createRequest.authToken();
        gameName = createRequest.gameName();
    }

    public int createGame() throws Error400BadRequest, Error401Unauthorized, DataAccessException {
        // Verify that the user exists
        if (!sqlAuthDao.verifyAuthToken(authToken)) {
            throw new Error401Unauthorized();
        }
        // Insert a new game
        return memoryGameDAO.insertGame(gameName);
    }

}
