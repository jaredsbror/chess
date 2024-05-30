package dataaccess.game;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLGameDao implements GameDAO {private Connection connection;
    private String statement = null;

    // Constructor
    public SQLGameDao() throws DataAccessException {
        // Verify the connection to the SQL database
        DatabaseManager.pingDatabase();
        DatabaseManager.pingTables();
    }
}
