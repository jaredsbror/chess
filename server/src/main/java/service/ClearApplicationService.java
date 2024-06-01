package service;


import dataaccess.DataAccessException;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.game.MemoryGameDAO;
import dataaccess.user.SQLUserDAO;


// Service to clear the database of all data
public class ClearApplicationService {
    private final SQLUserDAO sqlUserDAO = new SQLUserDAO();
    private final MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private final SQLAuthDAO sqlAuthDao;

    public ClearApplicationService() throws DataAccessException {
        sqlAuthDao = new SQLAuthDAO();
    }

    public void clearDatabase() throws DataAccessException {
        sqlAuthDao.clear();
        memoryGameDAO.clear();
        sqlUserDAO.clear();
    }

    public Boolean isDatabaseEmpty() throws DataAccessException {
        return (sqlAuthDao.isEmpty() && memoryGameDAO.isEmpty() && sqlUserDAO.isEmpty());
    }
}
