package service;


import dataaccess.exceptions.DataAccessException;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.game.SQLGameDAO;
import model.custom.CreateRequest;


public class CreateGameService {
    private final SQLAuthDAO sqlAuthDao;
    private final SQLGameDAO sqlGameDAO;
    private final String authToken;
    private final String gameName;


    public CreateGameService( CreateRequest createRequest ) throws DataAccessException {
        sqlAuthDao = new SQLAuthDAO();
        sqlGameDAO = new SQLGameDAO();
        authToken = createRequest.authToken();
        gameName = createRequest.gameName();
    }


    public int createGame() throws Error400BadRequest, Error401Unauthorized, DataAccessException {
        // Verify that the user exists
        if ( !sqlAuthDao.verifyAuthToken( authToken ) ) {
            throw new Error401Unauthorized();
        }
        // Insert a new game
        return sqlGameDAO.insertGame( gameName );
    }

}
