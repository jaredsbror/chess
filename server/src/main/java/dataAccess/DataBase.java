package dataAccess;

import dataAccess.auth.MemoryAuthDAO;
import dataAccess.game.MemoryGameDAO;
import dataAccess.user.MemoryUserDAO;

public class DataBase {
    public MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    public MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    public MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
}
