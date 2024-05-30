package dataaccess.user;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.UserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLUserDao implements UserDAO {private Connection connection;
    private String statement = null;

    // Constructor
    public SQLUserDao() throws DataAccessException {
        // Verify the connection to the SQL database
        DatabaseManager.pingDatabase();
        DatabaseManager.pingTables();
    }
}
