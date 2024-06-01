package dataaccess.game;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;

import java.sql.Connection;


public class SQLGameDAO implements GameDAO {private Connection connection;
    private String statement = null;

    // Constructor
    public SQLGameDAO() throws DataAccessException {
        // Verify the connection to the SQL database
        DatabaseManager.pingDatabase();
        DatabaseManager.pingTables();
    }
}
