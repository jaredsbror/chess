package service;

import dataaccess.DataAccessException;
import dataaccess.auth.SQLAuthDao;
import dataaccess.game.MemoryGameDAO;
import dataaccess.user.MemoryUserDAO;

// Service to clear the database of all data
public class ClearApplicationService {
    private MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private SQLAuthDao sqlAuthDao;

    public ClearApplicationService() throws DataAccessException {
        sqlAuthDao = new SQLAuthDao();
    }

    public void clearDatabase() throws DataAccessException {
        sqlAuthDao.clear();
        memoryGameDAO.clear();
        memoryUserDAO.clear();
    }

    public Boolean isDatabaseEmpty() throws DataAccessException {
        return (sqlAuthDao.isEmpty() && memoryGameDAO.isEmpty() && memoryUserDAO.isEmpty());
    }
}
