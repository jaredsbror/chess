package service;

import dataaccess.DataAccessException;
import dataaccess.auth.SQLAuthDao;
import dataaccess.game.MemoryGameDAO;
import dataaccess.user.MemoryUserDAO;

// Service to clear the database of all data
public class ClearApplicationService {
    private final MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private final MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private final SQLAuthDao sqlAuthDao;

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
