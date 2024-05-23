package service;

import dataAccess.auth.MemoryAuthDAO;
import dataAccess.game.MemoryGameDAO;
import dataAccess.user.MemoryUserDAO;

public class ClearApplicationService {
    MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    MemoryAuthDAO memoryAuthDao = new MemoryAuthDAO();

    public void clearDatabase() {
        memoryAuthDao.clear();
        memoryGameDAO.clear();
        memoryUserDAO.clear();
    }
}
