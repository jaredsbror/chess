package service;


import dataaccess.exceptions.DataAccessException;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.game.SQLGameDAO;
import model.custom.ListRequest;
import model.original.GameData;

import java.util.ArrayList;


public class ListGamesService {
    private final SQLAuthDAO sqlAuthDao;
    private final SQLGameDAO sqlGameDAO = new SQLGameDAO();
    private final String authToken;


    public ListGamesService( ListRequest listRequest ) throws DataAccessException {
        authToken = listRequest.authToken();
        sqlAuthDao = new SQLAuthDAO();
    }


    public ArrayList<GameData> getGameList() throws Error401Unauthorized, DataAccessException {
        // Verify that the authToken already exists
        if ( !sqlAuthDao.verifyAuthToken( authToken ) ) throw new Error401Unauthorized();
        // Return the list of games
        return sqlGameDAO.getGameArrayList();
    }
}
