package service;


import dataaccess.DataAccessException;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.game.SQLGameDAO;
import dataaccess.user.SQLUserDAO;


// Service to clear the database of all data
public class ClearApplicationService {
    private final SQLUserDAO sqlUserDAO = new SQLUserDAO();
    private final SQLGameDAO sqlGameDAO = new SQLGameDAO();
    private final SQLAuthDAO sqlAuthDao;

    public ClearApplicationService() throws DataAccessException {
        sqlAuthDao = new SQLAuthDAO();
    }

    public void clearDatabase() throws DataAccessException {
        sqlAuthDao.clear();
        sqlGameDAO.clear();
        sqlUserDAO.clear();
    }

    public Boolean isDatabaseEmpty() throws DataAccessException {
        return (sqlAuthDao.isEmpty() && sqlGameDAO.isEmpty() && sqlUserDAO.isEmpty());
    }
}
