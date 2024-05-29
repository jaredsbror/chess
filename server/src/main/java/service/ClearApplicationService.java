package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.game.MemoryGameDAO;
import dataaccess.user.MemoryUserDAO;

// Service to clear the database of all data
public class ClearApplicationService {
    private MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();

    public void clearDatabase() {
        memoryAuthDAO.clear();
        memoryGameDAO.clear();
        memoryUserDAO.clear();
    }

    public Boolean isDatabaseEmpty() {
        return (memoryAuthDAO.isEmpty() && memoryGameDAO.isEmpty() && memoryUserDAO.isEmpty());
    }
}
