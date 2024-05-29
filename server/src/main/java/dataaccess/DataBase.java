package dataaccess;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.game.MemoryGameDAO;
import dataaccess.user.MemoryUserDAO;

public class DataBase {
    public MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    public MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    public MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
}
